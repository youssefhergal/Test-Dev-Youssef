package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.util.*;
import java.util.stream.*;
import java.lang.reflect.Type;
import java.util.Map;

public class Utils {
    public static Map<String, Object> readJson(String filePath) throws Throwable {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Object>>() {}.getType();
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, type);
        }
    }
    public static double findRoutes(Graph graph, String start, String end, int autonomy, int countdown, List<Map<String, Object>> bountyHunters) {
        Queue<RouteState> queue = new LinkedList<>();
        queue.add(new RouteState(start, 0, autonomy, 0));
        Set<String> visited = new HashSet<>();
        Map<String, Set<Integer>> hunterDays = parseHunterDays(bountyHunters);

        int minDaysToReach = Integer.MAX_VALUE;
        int bestAttempts = Integer.MAX_VALUE;

        while (!queue.isEmpty()) {
            RouteState current = queue.poll();
            String planet = current.getPlanet();
            int day = current.getDay();
            double fuel = current.getFuel();
            int attempts = current.getAttempts();

            String stateKey = planet + "-" + day + "-" + fuel;
            if (visited.contains(stateKey)) continue;
            visited.add(stateKey);

            if (planet.equals(end)) {
                if (day <= countdown) {
                    minDaysToReach = Math.min(minDaysToReach, day);
                    bestAttempts = Math.min(bestAttempts, attempts);
                }
                continue;
            }

            for (Route route : graph.getNeighbors(planet)) {
                int travelTime = route.getTravelTime();
                String nextPlanet = route.getDestination();
                int nextDay = day + travelTime;

                if (nextDay > countdown) continue;

                int nextFuel;
                int nextAttempts = attempts;

                if (fuel >= travelTime) {
                    nextFuel = (int) (fuel - travelTime);
                } else {
                    nextFuel = (int) (autonomy - travelTime);
                    nextDay++; // Refuel
                    if (hunterDays.getOrDefault(planet, Set.of()).contains(day)) {
                        nextAttempts++;
                    }
                }

                if (hunterDays.getOrDefault(nextPlanet, Set.of()).contains(nextDay)) {
                    nextAttempts++;
                }

                queue.add(new RouteState(nextPlanet, nextDay, nextFuel, nextAttempts));
            }
        }

        if (minDaysToReach <= countdown) {
            return 1 - calculateProbability(bestAttempts);
        }
        return 0;
    }

    private static Map<String, Set<Integer>> parseHunterDays(List<Map<String, Object>> bountyHunters) {
        return bountyHunters.stream()
                .collect(Collectors.groupingBy(
                        hunter -> (String) hunter.get("planet"),
                        Collectors.mapping(hunter -> {
                            Object dayObj = hunter.get("day");
                            // Vérifier si "day" est un Double ou Integer et effectuer un cast correct
                            if (dayObj instanceof Double) {
                                return ((Double) dayObj).intValue(); // Convertir Double en Integer
                            } else if (dayObj instanceof Integer) {
                                return (Integer) dayObj; // Pas besoin de conversion
                            } else {
                                throw new IllegalArgumentException("Invalid day value: " + dayObj);
                            }
                        }, Collectors.toSet())
                ));
    }


    private static double calculateProbability(int attempts) {
        double prob = 0;
        for (int k = 0; k < attempts; k++) {
            prob += Math.pow(9, k) / Math.pow(10, k + 1);
        }
        return prob;
    }
}

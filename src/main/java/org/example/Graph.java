package org.example;

import java.sql.*;
import java.util.*;

public class Graph {
    private final Map<String, List<Route>> adjacencyList = new HashMap<>();

    public Graph(String dbPath) throws Exception {
        loadRoutes(dbPath);
    }

    private void loadRoutes(String dbPath) throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
        String query = "SELECT ORIGIN, DESTINATION, TRAVEL_TIME FROM ROUTES";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            String origin = rs.getString("ORIGIN");
            String destination = rs.getString("DESTINATION");
            int travelTime = rs.getInt("TRAVEL_TIME");

            adjacencyList.computeIfAbsent(origin, k -> new ArrayList<>()).add(new Route(destination, travelTime));
            adjacencyList.computeIfAbsent(destination, k -> new ArrayList<>()).add(new Route(origin, travelTime));
        }

        conn.close();
    }

    public List<Route> getNeighbors(String planet) {
        return adjacencyList.getOrDefault(planet, new ArrayList<>());
    }
}

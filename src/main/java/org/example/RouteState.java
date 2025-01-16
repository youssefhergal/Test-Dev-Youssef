package org.example;

import java.util.Objects;

public class RouteState implements Comparable<RouteState> {
    private String planet; // Current planet
    private int day;       // Current day
    private int fuel;      // Remaining fuel
    private int attempts;  // Number of bounty hunter encounters

    public RouteState(String planet, int day, int fuel, int attempts) {
        this.planet = planet;
        this.day = day;
        this.fuel = fuel;
        this.attempts = attempts;
    }

    public String getPlanet() {
        return planet;
    }

    public int getDay() {
        return day;
    }

    public int getFuel() {
        return fuel;
    }

    public int getAttempts() {
        return attempts;
    }

    @Override
    public int compareTo(RouteState other) {
        // Sort based on attempts first, then day
        if (this.attempts != other.attempts) {
            return Integer.compare(this.attempts, other.attempts);
        }
        return Integer.compare(this.day, other.day);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        RouteState that = (RouteState) obj;
        return day == that.day &&
                fuel == that.fuel &&
                attempts == that.attempts &&
                planet.equals(that.planet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(planet, day, fuel, attempts);
    }
}

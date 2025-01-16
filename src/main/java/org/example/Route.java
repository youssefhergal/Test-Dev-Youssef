package org.example;

public class Route {
    private final String destination;
    private final int travelTime;

    public Route(String destination, int travelTime) {
        this.destination = destination;
        this.travelTime = travelTime;
    }

    public String getDestination() {
        return destination;
    }

    public int getTravelTime() {
        return travelTime;
    }
}

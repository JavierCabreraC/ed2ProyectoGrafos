package com.example.demo.networkplanner.models.dto;

import com.example.demo.networkplanner.models.NetworkPoint;

import java.util.List;


public class RouteResponse {
    private List<NetworkPoint> path;
    private Double distance;

    // Constructor
    public RouteResponse() {}

    // Getters y Setters
    public List<NetworkPoint> getPath() {
        return path;
    }

    public void setPath(List<NetworkPoint> path) {
        this.path = path;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}

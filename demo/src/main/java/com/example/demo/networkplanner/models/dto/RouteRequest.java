package com.example.demo.networkplanner.models.dto;


public class RouteRequest {
    private Long startId;
    private Long endId;

    // Constructor
    public RouteRequest() {}

    public RouteRequest(Long startId, Long endId) {
        this.startId = startId;
        this.endId = endId;
    }

    // Getters y Setters
    public Long getStartId() {
        return startId;
    }

    public void setStartId(Long startId) {
        this.startId = startId;
    }

    public Long getEndId() {
        return endId;
    }

    public void setEndId(Long endId) {
        this.endId = endId;
    }
}

package com.example.demo.networkplanner.models.dto;

import com.example.demo.networkplanner.models.NetworkPoint;

import java.util.List;


public class RouteResponse {
    private List<NetworkPoint> camino;
    private Double distancia;

    public RouteResponse() {}

    public List<NetworkPoint> getCamino() {
        return camino;
    }

    public void setCamino(List<NetworkPoint> camino) {
        this.camino = camino;
    }

    public Double getDistancia() {
        return distancia;
    }

    public void setDistancia(Double distancia) {
        this.distancia = distancia;
    }
}

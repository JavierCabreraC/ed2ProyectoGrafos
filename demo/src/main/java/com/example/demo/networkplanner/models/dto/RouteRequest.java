package com.example.demo.networkplanner.models.dto;


public class RouteRequest {
    private Long inicioId;
    private Long finId;

    public RouteRequest() {}

    public RouteRequest(Long inicioId, Long finId) {
        this.inicioId = inicioId;
        this.finId = finId;
    }

    public Long getInicioId() {
        return inicioId;
    }

    public void setInicioId(Long inicioId) {
        this.inicioId = inicioId;
    }

    public Long getFinId() {
        return finId;
    }

    public void setFinId(Long finId) {
        this.finId = finId;
    }
}

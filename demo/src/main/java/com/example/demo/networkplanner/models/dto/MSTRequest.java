package com.example.demo.networkplanner.models.dto;


public class MSTRequest {
    private String algoritmo;

    public MSTRequest() {}

    public MSTRequest(String algoritmo) {
        this.algoritmo = algoritmo;
    }

    public String getAlgoritmo() {
        return algoritmo;
    }

    public void setAlgoritmo(String algoritmo) {
        this.algoritmo = algoritmo;
    }
}

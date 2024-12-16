package com.example.demo.networkplanner.models.dto;


public class MSTRequest {
    private String algorithm;

    // Constructor
    public MSTRequest() {}

    public MSTRequest(String algorithm) {
        this.algorithm = algorithm;
    }

    // Getter y Setter
    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }
}

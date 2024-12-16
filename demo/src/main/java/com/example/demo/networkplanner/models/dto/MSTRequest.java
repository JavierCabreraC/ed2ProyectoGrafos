package com.example.demo.networkplanner.models.dto;


public class MSTRequest {
    private String algorithm;

    public MSTRequest() {}

    public MSTRequest(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }
}

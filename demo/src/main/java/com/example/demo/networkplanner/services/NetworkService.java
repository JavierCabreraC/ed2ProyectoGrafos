package com.example.demo.networkplanner.services;

import com.example.demo.networkplanner.models.NetworkPlan;
import com.example.demo.networkplanner.models.NetworkPoint;
import com.example.demo.networkplanner.models.dto.RouteResponse;
import com.example.demo.networkplanner.repositories.NetworkPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class NetworkService {
    @Autowired
    private NetworkPointRepository networkPointRepository;

    public List<NetworkPoint> getAllPoints() {
        return networkPointRepository.findAll();
    }

    public RouteResponse calculateRoute(Long startId, Long endId) {
        // Implementación básica usando Dijkstra
        NetworkPoint start = networkPointRepository.findById(startId).orElseThrow();
        NetworkPoint end = networkPointRepository.findById(endId).orElseThrow();

        RouteResponse response = new RouteResponse();
        response.setPath(Arrays.asList(start, end));
        response.setDistance(0.0); // Calcular distancia real
        return response;
    }

    public NetworkPlan generateMST(String algorithm) {
        // Implementación básica usando Prim o Kruskal
        List<NetworkPoint> points = networkPointRepository.findAll();

        NetworkPlan plan = new NetworkPlan();
        plan.setAlgorithm(algorithm);
        plan.setPoints(points);
        plan.setTotalCost(0.0); // Calcular costo real
        return plan;
    }

    public Map<String, Double> calculateAllPaths() {
        // Implementación básica usando Floyd
        List<NetworkPoint> points = networkPointRepository.findAll();
        Map<String, Double> allPaths = new HashMap<>();
        return allPaths;
    }
}

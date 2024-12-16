package com.example.demo.networkplanner.controllers;

import com.example.demo.networkplanner.models.NetworkPlan;
import com.example.demo.networkplanner.models.dto.MSTRequest;
import com.example.demo.networkplanner.models.dto.RouteRequest;
import com.example.demo.networkplanner.models.dto.RouteResponse;
import com.example.demo.networkplanner.services.NetworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/network")
@CrossOrigin(origins = "http://localhost:5173")
public class NetworkController {

    private final NetworkService networkService;

    @Autowired
    public NetworkController(NetworkService networkService) {
        this.networkService = networkService;
    }

    @GetMapping("/points")
    public ResponseEntity<?> getAllPoints() {
        try {
            return ResponseEntity.ok(networkService.getAllPoints());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error retrieving network points: " + e.getMessage());
        }
    }

    @PostMapping("/route/calculate")
    public ResponseEntity<?> calculateRoute(@RequestBody RouteRequest request) {
        try {
            RouteResponse route = networkService.calculateRoute(
                    request.getStartId(),
                    request.getEndId()
            );
            return ResponseEntity.ok(route);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error calculating route: " + e.getMessage());
        }
    }

    @PostMapping("/mst/generate")
    public ResponseEntity<?> generateMST(@RequestBody MSTRequest request) {
        try {
            NetworkPlan plan = networkService.generateMST(request.getAlgorithm());
            return ResponseEntity.ok(plan);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error generating MST: " + e.getMessage());
        }
    }

    @PostMapping("/floyd/calculate")
    public ResponseEntity<?> calculateAllPaths() {
        try {
            return ResponseEntity.ok(networkService.calculateAllPaths());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error calculating all paths: " + e.getMessage());
        }
    }
}

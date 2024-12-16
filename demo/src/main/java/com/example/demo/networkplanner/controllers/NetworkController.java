package com.example.demo.networkplanner.controllers;

import com.example.demo.networkplanner.models.NetworkPlan;
import com.example.demo.networkplanner.models.dto.MSTRequest;
import com.example.demo.networkplanner.models.dto.RouteRequest;
import com.example.demo.networkplanner.models.dto.RouteResponse;
import com.example.demo.networkplanner.services.ConnectionService;
import com.example.demo.networkplanner.services.NetworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/network")
@CrossOrigin(origins = "${CLIENT_URL}")
public class NetworkController {

    private final NetworkService networkService;
    private final ConnectionService connectionService;

    @Autowired
    public NetworkController(NetworkService networkService, ConnectionService connectionService) {
        this.networkService = networkService;
        this.connectionService = connectionService;
    }

    @GetMapping("/points")
    public ResponseEntity<?> getAllPoints() {
        try {
            return ResponseEntity.ok(networkService.getAllPoints());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error de lectura de los puntos de la red: " + e.getMessage());
        }
    }

    @PostMapping("/route/calculate")
    public ResponseEntity<?> calculateRoute(@RequestBody RouteRequest request) {
        try {
            RouteResponse route = networkService.calculateRoute(
                    request.getInicioId(),
                    request.getFinId()
            );
            return ResponseEntity.ok(route);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error calculando las rutas: " + e.getMessage());
        }
    }

    @PostMapping("/mst/generate")
    public ResponseEntity<?> generateMST(@RequestBody MSTRequest request) {
        try {
            NetworkPlan plan = networkService.generateMST(request.getAlgoritmo());
            return ResponseEntity.ok(plan);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error generado Árbol de expansión: " + e.getMessage());
        }
    }

    @PostMapping("/floyd/calculate")
    public ResponseEntity<?> calculateAllPaths() {
        try {
            return ResponseEntity.ok(networkService.calculateAllPaths());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error calculando los caminos: " + e.getMessage());
        }
    }

    @GetMapping("/connections")
    public ResponseEntity<?> getAllConnections() {
        try {
            return ResponseEntity.ok(connectionService.getAllConnections());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error obteniendo conexiones: " + e.getMessage());
        }
    }
}

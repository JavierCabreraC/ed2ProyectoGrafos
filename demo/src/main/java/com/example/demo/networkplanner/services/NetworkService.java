package com.example.demo.networkplanner.services;

import com.example.demo.networkplanner.core.Algoritmos.Dijkstra;
import com.example.demo.networkplanner.core.Algoritmos.Floyd;
import com.example.demo.networkplanner.core.Algoritmos.Kruskal;
import com.example.demo.networkplanner.core.Algoritmos.Prim;
import com.example.demo.networkplanner.core.Excepciones.ExcepcionAristaNoExiste;
import com.example.demo.networkplanner.core.Excepciones.ExcepcionAristaYaExiste;
import com.example.demo.networkplanner.core.GrafosPesados.GrafoPesado;
import com.example.demo.networkplanner.models.Connection;
import com.example.demo.networkplanner.models.NetworkPlan;
import com.example.demo.networkplanner.models.NetworkPoint;
import com.example.demo.networkplanner.models.dto.RouteResponse;
import com.example.demo.networkplanner.repositories.ConnectionRepository;
import com.example.demo.networkplanner.repositories.NetworkPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class NetworkService {
    @Autowired
    private NetworkPointRepository networkPointRepository;

    @Autowired
    private ConnectionRepository connectionRepository;

    public List<NetworkPoint> getAllPoints() {
        return networkPointRepository.findAll();
    }

    private GrafoPesado<NetworkPoint> construirGrafo() {
        List<NetworkPoint> points = networkPointRepository.findAll();
        List<Connection> connections = connectionRepository.findAll();

        GrafoPesado<NetworkPoint> grafo = new GrafoPesado<>();

        // Agregar todos los vértices
        for (NetworkPoint point : points) {
            try {
                grafo.insertarVertice(point);
            } catch (IllegalArgumentException e) {
                System.out.println("Error al insertar vértice: " + point.getNombre() + e);
                throw new RuntimeException("Error al construir el grafo: " + e.getMessage());
            }
        }

        // Agregar todas las aristas
        for (Connection conn : connections) {
            try {
                NetworkPoint pointA = networkPointRepository.findById(conn.getPointAId())
                        .orElseThrow(() -> new RuntimeException("Punto A no encontrado: " + conn.getPointAId()));
                NetworkPoint pointB = networkPointRepository.findById(conn.getPointBId())
                        .orElseThrow(() -> new RuntimeException("Punto B no encontrado: " + conn.getPointBId()));

                grafo.insertarArista(pointA, pointB, conn.getDistance());

            } catch (ExcepcionAristaYaExiste e) {
                System.out.println("Arista ya existe entre " + conn.getPointAId() + " y " + conn.getPointBId());
                // Continuar con la siguiente arista
            } catch (Exception e) {
                System.out.println("Error al insertar arista entre " + conn.getPointAId() + " y " + conn.getPointBId() + e);
                throw new RuntimeException("Error al construir el grafo: " + e.getMessage());
            }
        }

        return grafo;
    }

    public RouteResponse calculateRoute(Long startId, Long endId) throws ExcepcionAristaNoExiste {
        NetworkPoint start = networkPointRepository.findById(startId).orElseThrow();
        NetworkPoint end = networkPointRepository.findById(endId).orElseThrow();

        GrafoPesado<NetworkPoint> grafo = construirGrafo();
        Dijkstra<NetworkPoint> dijkstra = new Dijkstra<>();
        Dijkstra.Resultado<NetworkPoint> resultado = dijkstra.dijkstra(grafo, start);

        RouteResponse response = new RouteResponse();
        response.setCamino(resultado.caminos.get(end));
        response.setDistancia(resultado.distancia.get(end));
        System.out.println(response);
        return response;
    }

    public NetworkPlan generateMST(String algorithm) throws ExcepcionAristaNoExiste {
        GrafoPesado<NetworkPoint> grafo = construirGrafo();
        NetworkPlan plan = new NetworkPlan();
        plan.setAlgoritmo(algorithm);

        if ("prim".equalsIgnoreCase(algorithm)) {
            NetworkPoint inicio = networkPointRepository.findAll().get(0); // O elegir un punto inicial específico
            Prim<NetworkPoint> prim = new Prim<>(grafo, inicio);
            plan.setCostoTotal(prim.getCostoTotal());

            // Convertir resultado de Prim a lista de puntos
            List<NetworkPoint> puntosEnMST = new ArrayList<>();
            for (Prim<NetworkPoint>.Arista arista : prim.getArbolExpansionMinimo()) {
                puntosEnMST.add(arista.getOrigen());
                puntosEnMST.add(arista.getDestino());
            }
            plan.setPoints(new ArrayList<>(new HashSet<>(puntosEnMST)));

        } else if ("kruskal".equalsIgnoreCase(algorithm)) {
            Kruskal<NetworkPoint> kruskal = new Kruskal<>(grafo);
            plan.setCostoTotal(kruskal.getCostoTotal());

            // Convertir resultado de Kruskal a lista de puntos
            List<NetworkPoint> puntosEnMST = new ArrayList<>();
            for (Kruskal<NetworkPoint>.Arista arista : kruskal.getArbolExpansionMinimo()) {
                puntosEnMST.add(arista.getOrigen());
                puntosEnMST.add(arista.getDestino());
            }
            plan.setPoints(new ArrayList<>(new HashSet<>(puntosEnMST)));
        }

        return plan;
    }

    public Map<String, Double> calculateAllPaths() throws ExcepcionAristaNoExiste {
        GrafoPesado<NetworkPoint> grafo = construirGrafo();
        Floyd<NetworkPoint> floyd = new Floyd<>(grafo);
        Map<String, Double> allPaths = new HashMap<>();

        List<NetworkPoint> points = networkPointRepository.findAll();
        for (NetworkPoint origen : points) {
            for (NetworkPoint destino : points) {
                if (!origen.equals(destino)) {
                    String key = origen.getNombre() + " -> " + destino.getNombre();
                    double distancia = floyd.getDistancia(origen, destino);
                    if (distancia != Double.MAX_VALUE) {
                        allPaths.put(key, distancia);
                    }
                }
            }
        }

        return allPaths;
    }
}

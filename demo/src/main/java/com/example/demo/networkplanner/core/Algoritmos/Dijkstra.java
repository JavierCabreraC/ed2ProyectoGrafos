package com.example.demo.networkplanner.core.Algoritmos;

import com.example.demo.networkplanner.core.Excepciones.ExcepcionAristaNoExiste;
import com.example.demo.networkplanner.core.GrafosPesados.GrafoPesado;
import com.example.demo.networkplanner.core.Utileria.ControlMarcados;

import java.util.*;


public class Dijkstra<G extends Comparable<G>> {
    protected static final double INF = Double.MAX_VALUE;

    public static class Resultado<G> {
        public final Map<G, List<G>> caminos;
        public final Map<G, Double> distancia;

        public Resultado(Map<G, List<G>> caminos, Map<G, Double> distancia) {
            this.caminos = caminos; this.distancia = distancia;
        }
    }

    private List<G> getCamino(Map<G, G> anteriores, G destino) {
        List<G> camino = new LinkedList<>();
        for (G v = destino; v != null; v = anteriores.get(v)) {
            camino.addFirst(v);
        }
        return camino;
    }

    public Resultado<G> dijkstra(GrafoPesado<G> grafo, G origen) throws ExcepcionAristaNoExiste {
        grafo.validarVertice(origen);
        Map<G, G> anteriores = new HashMap<>();
        Map<G, Double> pesos = new HashMap<>();
        Map<G, Integer> vertAIndx = new HashMap<>();
        ControlMarcados marcados = new ControlMarcados(grafo.cantidadDeVertices());
        int indx = 0;
        for (G vertice : grafo.getVertices()) {
            pesos.put(vertice, INF);
            vertAIndx.put(vertice, indx++);
        }
        pesos.put(origen, 0.0);
        PriorityQueue<G> cola = new PriorityQueue<>(Comparator.comparingDouble(pesos::get));
        cola.offer(origen);
        while (!cola.isEmpty()) {
            G u = cola.poll();
            int posU = vertAIndx.get(u);
            if (marcados.estaMarcado(posU)) {
                continue;
            }
            marcados.marcarVertice(posU);
            for (G v: grafo.getAdysDelVertice(u)) {
                int posV = vertAIndx.get(v);
                if (!marcados.estaMarcado(posV)) {
                    double pesoUV = grafo.getPeso(u, v);
                    if (pesos.get(u) + pesoUV < pesos.get(v)) {
                        pesos.put(v, pesos.get(u) + pesoUV);
                        anteriores.put(v, u);
                        cola.offer(v);
                    }
                }
            }
        }
        Map<G, List<G>> caminos = new HashMap<>();
        Map<G, Double> distancia = new HashMap<>();
        for (G vertice : grafo.getVertices()) {
            if (pesos.get(vertice) != INF && !vertice.equals(origen)) {
                List<G> camino = getCamino(anteriores, vertice);
                caminos.put(vertice, camino);
                distancia.put(vertice, pesos.get(vertice));
            }
        }
        return new Resultado<>(caminos, distancia);
    }

    public void impirmirResultado(Resultado<G> res, G origen) {
        System.out.println("El camino más corto de: " + origen + ":");
        for (Map.Entry<G, List<G>> entry: res.caminos.entrySet()) {
            G destino = entry.getKey();
            List<G> camino = entry.getValue();
            Double distancia = res.distancia.get(destino);
            System.out.println("- al vértice: " + distancia);
            System.out.println("- con camino: " + camino);
            System.out.println("- y distancia: " + distancia);
        }
    }
}

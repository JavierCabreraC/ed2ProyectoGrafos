package com.example.demo.networkplanner.core.Algoritmos;

import com.example.demo.networkplanner.core.Excepciones.ExcepcionAristaNoExiste;
import com.example.demo.networkplanner.core.GrafosPesados.GrafoPesado;

import java.util.ArrayList;
import java.util.List;


public class Floyd<G extends Comparable<G>> {
    protected final GrafoPesado<G> grafo;
    protected final int n;
    protected final int[][] intermedios;
    protected final double[][] distancias;
    protected static final double INF = Double.MAX_VALUE;

    public Floyd(GrafoPesado<G> grafo) throws ExcepcionAristaNoExiste {
        this.grafo = grafo;
        this.n = grafo.cantidadDeVertices();
        this.intermedios = new int[n][n];
        this.distancias = new double[n][n];
        this.inicializarMatrices();
        this.ejecutarFloyd();
    }

    private void inicializarMatrices() throws ExcepcionAristaNoExiste {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <n; j++) {
                if (i == j) {
                    distancias[i][j] = 0;
                } else {
                    distancias[i][j] = INF;
                }
                intermedios[i][j] = -1;
            }
        }
        for (G vertice : grafo.getVertices()) {
            int i = grafo.nroVertice(vertice);
            for (G ady : grafo.getAdysDelVertice(vertice)) {
                int j = grafo.nroVertice(ady);
                distancias[i][j] = grafo.getPeso(vertice, ady);
            }
        }
    }

    private void ejecutarFloyd() {
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (distancias[i][k] != INF && distancias[k][j] != INF) {
                        double nuevo = distancias[i][k] + distancias[k][j];
                        if (nuevo < distancias[i][j]) {
                            distancias[i][j] = nuevo;
                            intermedios[i][j] = k;
                        }
                    }
                }
            }
        }
    }

    public double getDistancia(G origen, G destino) {
        int i = grafo.nroVertice(origen);
        int j = grafo.nroVertice(destino);
        return distancias[i][j];
    }

    public List<G> getCamino(G origen, G destino) {
        List<G> camino = new ArrayList<>();
        int posOrigen = grafo.nroVertice(origen);
        int posDestino = grafo.nroVertice(destino);
        if (distancias[posOrigen][posDestino] == INF) {
            return camino;
        }
        camino.add(origen);
        this.reconstruirCamino(posOrigen, posDestino, camino);
        camino.add(destino);
        return camino;
    }

    private void reconstruirCamino(int posOrigen, int posDestino, List<G> camino) {
        int posInterm = intermedios[posOrigen][posDestino];
        if (posInterm != -1) {
            G vertInterm = null;
            for (G vertice : grafo.getVertices()) {
                if (grafo.nroVertice(vertice) == posInterm) {
                    vertInterm = vertice;
                    break;
                }
            }
            reconstruirCamino(posOrigen, posInterm, camino);
            camino.add(vertInterm);
            reconstruirCamino(posInterm, posDestino, camino);
        }
    }
}

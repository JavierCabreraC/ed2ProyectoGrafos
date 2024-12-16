package com.example.demo.networkplanner.core.Utileria;


import com.example.demo.networkplanner.core.GrafosPesados.GrafoPesado;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFS<G extends Comparable<G>> {
    private final GrafoPesado<G> elGrafo;
    private final List<G> recorrido;
    private final ControlMarcados controlMarcados;

    public BFS(GrafoPesado<G> unGrafo, G vertInicial) {
        this.elGrafo = unGrafo;
        this.controlMarcados = new ControlMarcados(this.elGrafo.cantidadDeVertices());
        this.recorrido = new ArrayList<>();
        this.ejecutarBFS(vertInicial);
    }

    public void ejecutarBFS(G vertEnTurno) {
        this.elGrafo.validarVertice(vertEnTurno);
        Queue<G> colaDeVert = new LinkedList<>();
        colaDeVert.offer(vertEnTurno);
        this.controlMarcados.marcarVertice(this.elGrafo.nroVertice(vertEnTurno));
        while (!colaDeVert.isEmpty()) {
            G vertice = colaDeVert.poll();
            this.recorrido.add(vertice);
            Iterable<G> adyVertice = this.elGrafo.getAdysDelVertice(vertice);
            for (G ady: adyVertice) {
                int nroDelAdy = this.elGrafo.nroVertice(ady);
                if (!this.controlMarcados.estaMarcado(nroDelAdy)) {
                    colaDeVert.add(ady);
                    this.controlMarcados.marcarVertice(nroDelAdy);
                }
            }
        }
    }

    public List<G> getRecorrido() {
        return this.recorrido;
    }

    public boolean seVisitoVertice(G vertice) {
        this.elGrafo.validarVertice(vertice);
        int nroVert = this.elGrafo.nroVertice(vertice);
        return this.controlMarcados.estaMarcado(nroVert);
    }

    public boolean seVisitoTodosLosVertices() {
        return this.controlMarcados.estanTodosLosVerticesMarcados();
    }
}


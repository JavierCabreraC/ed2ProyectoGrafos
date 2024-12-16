package com.example.demo.networkplanner.core.Utileria;

import com.example.demo.networkplanner.core.GrafosPesados.GrafoPesado;

import java.util.ArrayList;
import java.util.List;


public class DFS<G extends Comparable<G>> {
    private final ControlMarcados controlMarcados;
    private final GrafoPesado<G> elGrafo;
    private final List<G> recorrido;

    public DFS(GrafoPesado<G> unGrafo, G vertInicial) {
        this.elGrafo = unGrafo;
        this.controlMarcados = new ControlMarcados(this.elGrafo.cantidadDeVertices());
        this.recorrido = new ArrayList<>();
        this.ejecutarDFS(vertInicial);
    }

    public void ejecutarDFS(G vertEnTurno) {
        this.elGrafo.validarVertice(vertEnTurno);
        this.controlMarcados.marcarVertice(this.elGrafo.nroVertice(vertEnTurno));
        this.recorrido.add(vertEnTurno);
        Iterable<G> adyVert = this.elGrafo.getAdysDelVertice(vertEnTurno);
        for (G ady: adyVert) {
            int nroAdy = this.elGrafo.nroVertice(ady);
            if (!this.controlMarcados.estaMarcado(nroAdy)) {
                ejecutarDFS(ady);
            }
        }
    }

    public List<G> getRecorrido() {
        return this.recorrido;
    }

    public boolean seVisitoVertice(G vertDestino) {
        this.elGrafo.validarVertice(vertDestino);
        int pos = this.elGrafo.nroVertice(vertDestino);
        return this.controlMarcados.estaMarcado(pos);
    }
}


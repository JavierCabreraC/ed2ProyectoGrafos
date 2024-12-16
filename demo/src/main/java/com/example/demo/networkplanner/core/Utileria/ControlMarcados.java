package com.example.demo.networkplanner.core.Utileria;

import java.util.ArrayList;
import java.util.List;


public class ControlMarcados {
    private final List<Boolean> listaDeMarcados;

    public ControlMarcados(int nroVertices) {
        this.listaDeMarcados = new ArrayList<Boolean>();
        for (int i = 0; i < nroVertices; i++) {
            listaDeMarcados.add(Boolean.FALSE);
        }
    }

    public void desmarcarTodos() {
        for (int i = 0; i < this.listaDeMarcados.size(); i++) {
            this.listaDeMarcados.set(i, Boolean.FALSE);
        }
    }

    public boolean estaMarcado(int nroVertice) {
        return this.listaDeMarcados.get(nroVertice);
    }

    public void marcarVertice(int vertice) {
        this.listaDeMarcados.set(vertice, Boolean.TRUE);
    }

    public void desmarcarVertice(int vertice) {
        this.listaDeMarcados.set(vertice, Boolean.FALSE);
    }

    public boolean estanTodosLosVerticesMarcados() {
        return !this.listaDeMarcados.contains(Boolean.FALSE);
    }
}

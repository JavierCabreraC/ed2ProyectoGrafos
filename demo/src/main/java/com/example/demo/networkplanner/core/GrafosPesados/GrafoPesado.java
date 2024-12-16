package com.example.demo.networkplanner.core.GrafosPesados;

import com.example.demo.networkplanner.core.Excepciones.ExcepcionAristaNoExiste;
import com.example.demo.networkplanner.core.Excepciones.ExcepcionAristaYaExiste;
import com.example.demo.networkplanner.core.Utileria.ControlMarcados;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GrafoPesado<G extends Comparable<G>> {
    protected List<G> listaDeVertices;
    protected List<List<AdyacenteConPeso>> listasDeAdyacencias;
    protected static final byte NRO_VERTICE_INVALIDO = -1;

    public GrafoPesado() {
        listaDeVertices = new ArrayList<>();
        listasDeAdyacencias = new ArrayList<>();
    }

    public GrafoPesado(Iterable<G> vertices) {
        this();
        for (G vertice: vertices) {
            this.insertarVertice(vertice);
        }
    }

    public int nroVertice(G vertice) {
        for (int i = 0; i < listaDeVertices.size(); i++) {
            G verticeEnTurno = listaDeVertices.get(i);
            if (vertice.compareTo(verticeEnTurno) == 0) {
                return i;
            }
        }
        return GrafoPesado.NRO_VERTICE_INVALIDO;
    }

    public void validarVertice(G vertice) {
        int nroDelVertice = this.nroVertice(vertice);
        if (nroDelVertice == GrafoPesado.NRO_VERTICE_INVALIDO) {
            throw new IllegalArgumentException("El vértice no pertenece al grafo");
        }
    }

    public void insertarVertice(G vertice) {
        int nroDelVertice = this.nroVertice(vertice);
        if (nroDelVertice != GrafoPesado.NRO_VERTICE_INVALIDO) {
            throw new IllegalArgumentException("El vértice ya existe en el grafo");
        }
        this.listaDeVertices.add(vertice);
        this.listasDeAdyacencias.add(new ArrayList<>());
    }

    public int cantidadDeVertices() {
        return this.listaDeVertices.size();
    }

    public Iterable<G> getVertices() {
        return this.listaDeVertices;
    }

    public Iterable<G> getAdysDelVertice(G vertice) {
        this.validarVertice(vertice);
        int nroDelVertice = this.nroVertice(vertice);
        List<AdyacenteConPeso> adysDelVerticeXNro = this.listasDeAdyacencias.get(nroDelVertice);
        List<G> listaDeAdysDelVertice = new ArrayList<>();
        for (AdyacenteConPeso adyConPeso: adysDelVerticeXNro) {
            listaDeAdysDelVertice.add(this.listaDeVertices.get(adyConPeso.getNroVertice()));
        }
        return listaDeAdysDelVertice;
    }

    public boolean existeAdyacencia(G origen, G destino) {
        this.validarVertice(origen); this.validarVertice(destino);
        int posVertOrigen = this.nroVertice(origen);
        int posVertDestino = this.nroVertice(destino);
        List<AdyacenteConPeso> adysDelVertOrigen = this.listasDeAdyacencias.get(posVertOrigen);
        AdyacenteConPeso adySinPeso = new AdyacenteConPeso(posVertDestino);
        return adysDelVertOrigen.contains(adySinPeso);
    }

    public double getPeso(G origen, G destino) throws ExcepcionAristaNoExiste {
        if (!this.existeAdyacencia(origen, destino)) {
            throw new ExcepcionAristaNoExiste();
        }
        int nroVertOrigen = this.nroVertice(origen);
        int nroVertDestino = this.nroVertice(destino);
        List<AdyacenteConPeso> adysDelOrigen = this.listasDeAdyacencias.get(nroVertOrigen);
        AdyacenteConPeso adysSinPeso = new AdyacenteConPeso(nroVertDestino);
        int posDeAdyacencia = adysDelOrigen.indexOf(adysSinPeso);
        AdyacenteConPeso adyacencia = adysDelOrigen.get(posDeAdyacencia);
        return adyacencia.getPeso();
    }

    public void insertarArista(G origen, G destino, double peso) throws ExcepcionAristaYaExiste {
        if (this.existeAdyacencia(origen, destino)) {
            throw new ExcepcionAristaYaExiste();
        }
        int posVertOrigen = this.nroVertice(origen);
        int posVertDestino = this.nroVertice(destino);
        List<AdyacenteConPeso> adysDelOrigen = this.listasDeAdyacencias.get(posVertOrigen);
        AdyacenteConPeso adyDelOrigen = new AdyacenteConPeso(posVertDestino, peso);
        adysDelOrigen.add(adyDelOrigen);
        Collections.sort(adysDelOrigen);
        if (posVertOrigen != posVertDestino) {
            List<AdyacenteConPeso> adysDelDestino = this.listasDeAdyacencias.get(posVertDestino);
            AdyacenteConPeso adyDelDestino = new AdyacenteConPeso(posVertOrigen, peso);
            adysDelDestino.add(adyDelDestino);
            Collections.sort(adysDelDestino);
        }
    }

    public int cantidadDeAristas() {
        int ctd = 0;
        for (int i = 0; i < this.listasDeAdyacencias.size(); i++) {
            List<AdyacenteConPeso> adysDelVertice = this.listasDeAdyacencias.get(i);
            for (AdyacenteConPeso ady : adysDelVertice) {
                if (i == ady.getNroVertice()) {
                    ctd++;
                } else if (i < ady.getNroVertice()) {
                    ctd++;
                }
            }
        }
        return ctd;
    }

    public void eliminarArista(G origen, G destino) throws ExcepcionAristaNoExiste {
        if (!this.existeAdyacencia(origen, destino)) {
            throw new ExcepcionAristaNoExiste();
        }
        int posOrigen = this.nroVertice(origen);
        int posDestino = this.nroVertice(destino);
        List<AdyacenteConPeso> adysDelOrigen = this.listasDeAdyacencias.get(posOrigen);
        AdyacenteConPeso adyAEliminar = new AdyacenteConPeso(posDestino);
        adysDelOrigen.remove(adyAEliminar);
        if (posOrigen != posDestino) {
            List<AdyacenteConPeso> adysDelDestino = this.listasDeAdyacencias.get(posDestino);
            AdyacenteConPeso adyAEliminarDelDestino = new AdyacenteConPeso(posOrigen);
            adysDelDestino.remove(adyAEliminarDelDestino);
        }
    }

    public void eliminarVertice(G vertice) {
        this.validarVertice(vertice);
        int posVertice = this.nroVertice(vertice);
        this.listaDeVertices.remove(posVertice);
        this.listasDeAdyacencias.remove(posVertice);
        for (List<AdyacenteConPeso> adys : this.listasDeAdyacencias) {
            adys.removeIf(ady -> ady.getNroVertice() == posVertice);
            for (AdyacenteConPeso ady : adys) {
                if (ady.getNroVertice() > posVertice) {
                    ady.setNroVertice(ady.getNroVertice() - 1);
                }
            }
            Collections.sort(adys);
        }
    }

    public int gradoDelVertice(G vertice) {
        this.validarVertice(vertice);
        int posVertice = this.nroVertice(vertice);
        List<AdyacenteConPeso> adys = this.listasDeAdyacencias.get(posVertice);
        int ctd = adys.size();
        for (AdyacenteConPeso ady : adys) {
            if (ady.getNroVertice() == posVertice) {
                ctd++;
            }
        }
        return ctd;
    }

    public boolean tieneCiclo() {
        if (this.cantidadDeVertices() == 0) {
            return false;
        }
        ControlMarcados marcados = new ControlMarcados(this.cantidadDeVertices());
        for (G vertice : this.getVertices()) {
            int nroVertice = this.nroVertice(vertice);
            if (!marcados.estaMarcado(nroVertice)) {
                if (tieneCicloDFS(vertice, null, marcados)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean tieneCicloDFS(G actual, G anterior, ControlMarcados marcados) {
        int nroVerticeActual = this.nroVertice(actual);
        marcados.marcarVertice(nroVerticeActual);
        for (G adyacente : this.getAdysDelVertice(actual)) {
            int nroAdyacente = this.nroVertice(adyacente);
            if (anterior == null || adyacente.compareTo(anterior) != 0) {
                if (marcados.estaMarcado(nroAdyacente)) {
                    return true;
                }
                if (tieneCicloDFS(adyacente, actual, marcados)) {
                    return true;
                }
            }
        }
        return false;
    }
}

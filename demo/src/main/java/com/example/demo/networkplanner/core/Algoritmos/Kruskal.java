package com.example.demo.networkplanner.core.Algoritmos;

import com.example.demo.networkplanner.core.Excepciones.ExcepcionAristaNoExiste;
import com.example.demo.networkplanner.core.GrafosPesados.GrafoPesado;

import java.util.*;


public class Kruskal<G extends Comparable<G>> {
    private class Arista implements Comparable<Arista> {
        private final G origen, destino;
        private final double peso;

        public Arista(G origen, G destino, double peso) {
            this.origen = origen;
            this.destino = destino;
            this.peso = peso;
        }

        @Override
        public int compareTo(Arista otra) {
            return Double.compare(this.peso, otra.peso);
        }

        public G getOrigen() {
            return this.origen;
        }

        public G getDestino() {
            return this.destino;
        }

        public double getPeso() {
            return this.peso;
        }
    }

    protected final GrafoPesado<G> grafo;
    protected final List<Arista> mst;
    protected double costoTotal;
    protected final int[] conjuntos;

    public Kruskal(GrafoPesado<G> grafo) throws ExcepcionAristaNoExiste {
        this.grafo = grafo;
        this.mst = new ArrayList<>();
        costoTotal = 0.0;
        this.conjuntos = new int[grafo.cantidadDeVertices()];
        this.ejecutarKruskal();
    }

    private void ejecutarKruskal() throws ExcepcionAristaNoExiste {
        this.incializarConjuntos();
        List<Arista> aristasOrdenadas = this.getAristasOrdenadas();
        for (Arista arista : aristasOrdenadas) {
            int posOrigen = grafo.nroVertice(arista.getOrigen());
            int posDestino = grafo.nroVertice(arista.getDestino());
            if (this.buscarConjunto(posOrigen) != this.buscarConjunto(posDestino)) {
                mst.add(arista);
                costoTotal += arista.getPeso();
                this.unirConjuntos(posOrigen, posDestino);
            }
        }
    }

    private void incializarConjuntos() {
        for (int i = 0; i < conjuntos.length; i++) {
            conjuntos[i] = i;
        }
    }

    private int buscarConjunto(int pos) {
        if (conjuntos[pos] != pos) {
            conjuntos[pos] = buscarConjunto(conjuntos[pos]);
        }
        return conjuntos[pos];
    }

    private void unirConjuntos(int posUno, int posDos) {
        conjuntos[this.buscarConjunto(posUno)] = this.buscarConjunto(posDos);
    }

    private List<Arista> getAristasOrdenadas() throws ExcepcionAristaNoExiste {
        Set<String> agregadas = new HashSet<>();
        List<Arista> aristas = new ArrayList<>();
        for (G vertice : grafo.getVertices()) {
            for (G ady : grafo.getAdysDelVertice(vertice)) {
                String idUno = vertice.toString() + "-" + ady.toString();
                String idDos = ady.toString() + "-" + vertice.toString();
                if (!agregadas.contains(idUno) && !agregadas.contains(idDos)) {
                    double peso = grafo.getPeso(vertice, ady);
                    aristas.add(new Arista(vertice, ady, peso));
                    agregadas.add(idUno);
                }
            }
        }
        Collections.sort(aristas);
        return aristas;
    }

    public List<Arista> getArbolExpansionMinimo() {
        return new ArrayList<>(mst);
    }

    public double getCostoTotal() {
        return costoTotal;
    }
}

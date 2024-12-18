package com.example.demo.networkplanner.core.Algoritmos;

import com.example.demo.networkplanner.core.Excepciones.ExcepcionAristaNoExiste;
import com.example.demo.networkplanner.core.GrafosPesados.GrafoPesado;
import com.example.demo.networkplanner.core.Utileria.ControlMarcados;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;


public class Prim<G extends Comparable<G>> {
    public class Arista implements Comparable<Arista> {
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
    protected final ControlMarcados marcados;

    public Prim(GrafoPesado<G> grafo, G origen) throws ExcepcionAristaNoExiste {
        this.grafo = grafo;
        this.mst = new ArrayList<>();
        this.costoTotal = 0.0;
        this.marcados = new ControlMarcados(grafo.cantidadDeVertices());
        this.ejecutarPrim(origen);
    }

    private void ejecutarPrim(G origen) throws ExcepcionAristaNoExiste {
        PriorityQueue<Arista> cola = new PriorityQueue<>();
        int posOrigen = grafo.nroVertice(origen);
        marcados.marcarVertice(posOrigen);
        this.getAristasAdyacentes(origen, cola);
        while (!cola.isEmpty() && mst.size() < grafo.cantidadDeVertices() - 1) {
            Arista arista = cola.poll();
            int posDestino = grafo.nroVertice(arista.getDestino());
            if (marcados.estaMarcado(posDestino)) {
                continue;
            }
            marcados.marcarVertice(posDestino);
            mst.add(arista);
            costoTotal += arista.getPeso();
            this.getAristasAdyacentes(arista.getDestino(), cola);
        }
    }

    private void getAristasAdyacentes(G vertice, PriorityQueue<Arista> cola) throws ExcepcionAristaNoExiste {
        for (G ady : grafo.getAdysDelVertice(vertice)) {
            int posAdy = grafo.nroVertice(ady);
            if (!marcados.estaMarcado(posAdy)) {
                double peso = grafo.getPeso(vertice, ady);
                cola.offer(new Arista(vertice, ady, peso));
            }
        }
    }

    public List<Arista> getArbolExpansionMinimo() {
        return new ArrayList<>(mst);
    }

    public double getCostoTotal() {
        return costoTotal;
    }
}

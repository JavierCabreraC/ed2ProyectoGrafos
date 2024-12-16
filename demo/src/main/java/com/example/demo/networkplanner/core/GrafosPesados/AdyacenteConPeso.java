package com.example.demo.networkplanner.core.GrafosPesados;


public class AdyacenteConPeso implements Comparable<AdyacenteConPeso> {
    private int nroVertice;
    private double peso;

    public AdyacenteConPeso(int nroVertice) {
        this.nroVertice = nroVertice;
    }

    public AdyacenteConPeso(int nroVertice, double peso) {
        this.nroVertice = nroVertice;
        this.peso = peso;
    }

    public int getNroVertice() {
        return this.nroVertice;
    }

    public void setNroVertice(int nroVertice) {
        this.nroVertice = nroVertice;
    }

    public double getPeso() {
        return this.peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    @Override
    public int compareTo(AdyacenteConPeso ady) {
        return Integer.compare(this.nroVertice, ady.nroVertice);
    }

    @Override
    public boolean equals(Object obj) {
        AdyacenteConPeso o = (AdyacenteConPeso) obj;
        return this.compareTo(o) == 0;
    }
}

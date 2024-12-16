package com.example.demo.networkplanner.core.Excepciones;


public class ExcepcionAristaYaExiste extends Exception {
    public ExcepcionAristaYaExiste() { super("Arista ya existe"); }

    public ExcepcionAristaYaExiste(String message) {
        super(message);
    }
}
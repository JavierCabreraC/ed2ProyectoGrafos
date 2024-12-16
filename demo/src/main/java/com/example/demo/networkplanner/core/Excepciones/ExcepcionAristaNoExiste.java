package com.example.demo.networkplanner.core.Excepciones;


public class ExcepcionAristaNoExiste extends Exception {
    public ExcepcionAristaNoExiste() { super("No existe la arista."); }

    public ExcepcionAristaNoExiste(String message) {
        super(message);
    }
}

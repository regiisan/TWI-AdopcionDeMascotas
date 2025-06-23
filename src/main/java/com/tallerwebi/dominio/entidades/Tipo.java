package com.tallerwebi.dominio.entidades;

public enum Tipo {
    PERRO("Perro"),
    GATO("Gato");

    private final String displayValue;

    private Tipo(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}



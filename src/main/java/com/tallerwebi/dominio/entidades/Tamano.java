package com.tallerwebi.dominio.entidades;

public enum Tamano {
    CHICO("Chico"),
    GRANDE("Grande"),
    MEDIANO("Mediano");

    private final String displayValue;

    private Tamano(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}

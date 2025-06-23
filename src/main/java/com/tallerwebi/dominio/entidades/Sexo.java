package com.tallerwebi.dominio.entidades;

public enum Sexo {
    MACHO("Macho"),
    HEMBRA("Hembra");

    private final String displayValue;

    private Sexo(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}

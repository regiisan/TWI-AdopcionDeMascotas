package com.tallerwebi.dominio.entidades;

public enum NivelEnergia {
    BAJO("Bajo"),
    MEDIO("Medio"),
    ALTO("Alto");

    private final String displayValue;

    private NivelEnergia(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}

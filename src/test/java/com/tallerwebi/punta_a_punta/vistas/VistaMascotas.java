package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;

public class VistaMascotas extends VistaWeb {

    public VistaMascotas(Page page) {
        super(page);
        page.navigate("localhost:8080/tallerwebi-base-1.0-SNAPSHOT/mascotas");
    }

    public void darClickEnMascotasEnAdopcion(){
        this.darClickEnElElemento("#ir-a-mascotas");
    }

    public void darClickEnAdoptar(){
        this.darClickEnElElemento("#modalAdoptarBtn");
    }

    public void darClickEnPrimerBotonDetalle(){
        this.darClickEnElPrimerElemento(".ver-detalle");
    }

    public void darClickEnviarSolicitudAdopcion(){
        this.darClickEnElElemento("#btn-solicitud-adopcion");
    }

    public void escribirNombre(String nombre){
        this.escribirEnElElemento("#nombre", nombre);
    }

    public void escribirEmail(String email){
        this.escribirEnElElemento("#email", email);
    }

    public void escribirExperiencia(String experiencia){
        this.escribirEnElElemento("#experiencia", experiencia);
    }

    public void escribirEspacioDisponible(String espacio){
        this.escribirEnElElemento("#espacio", espacio);
    }

    public void seleccionarTipoVivienda(String tipoVivienda){
        page.selectOption("#tipoVivienda", tipoVivienda);
    }

    public void seleccionarSiTieneAnimales(String tieneAnimales){
        page.selectOption("#tieneAnimales", tieneAnimales);
    }

    public String obtenerMensajeDeExito(){
        return this.obtenerTextoDelElemento("div.alerta-flotante-success span");
    }





}

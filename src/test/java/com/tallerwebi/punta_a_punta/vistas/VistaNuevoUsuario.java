package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;

public class VistaNuevoUsuario extends VistaWeb {

    public VistaNuevoUsuario(Page page) {
        super(page);
    }

    public void escribirNOMBRE(String nombre){
        this.escribirEnElElemento("#nombre", nombre);
    }

    public void escribirEMAIL(String email){
        this.escribirEnElElemento("#email", email);
    }

    public void escribirClave(String clave){
        this.escribirEnElElemento("#password", clave);
    }

    public void darClickEnRegistrarme(){
        this.darClickEnElElemento("#btn-registrarme");
    }
}

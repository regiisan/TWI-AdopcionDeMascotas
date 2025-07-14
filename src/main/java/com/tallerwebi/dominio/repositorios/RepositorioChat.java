package com.tallerwebi.dominio.repositorios;

import com.tallerwebi.dominio.entidades.Mensaje;

import java.util.List;

public interface RepositorioChat {
    void guardarMensaje(Mensaje mensaje);
    List<Mensaje> obtenerMensajes();
}

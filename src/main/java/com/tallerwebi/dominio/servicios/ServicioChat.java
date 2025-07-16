package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.entidades.Mensaje;
import com.tallerwebi.dominio.entidades.MensajeDto;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ServicioChat {
    void guardarMensaje(Mensaje mensaje);
    List<MensajeDto> obtenerMensajes();
}

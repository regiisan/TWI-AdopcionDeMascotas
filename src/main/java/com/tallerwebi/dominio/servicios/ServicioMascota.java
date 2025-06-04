package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.entidades.Mascota;

import java.util.List;

public interface ServicioMascota {
    void guardar(Mascota mascota);
    List<Mascota> obtenerMascotas();
    List<Mascota> obtenerMascotasDestacadas();
    Mascota obtenerMascotaPorId(Long id);

}

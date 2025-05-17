package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioMascota {
    void guardar(Mascota mascota);
    List<Mascota> obtenerMascotas();
    List<Mascota> obtenerMascotasDestacadas();
}

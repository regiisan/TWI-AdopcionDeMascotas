package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioMascota {
    void guardar(Mascota mascota);
    List<Mascota> listarMascotas();
}

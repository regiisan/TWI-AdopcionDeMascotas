package com.tallerwebi.dominio.repositorios;

import com.tallerwebi.dominio.entidades.Mascota;

import java.util.List;

public interface RepositorioMascota {
    void guardar(Mascota mascota);
    List<Mascota> listarMascotas();
    List<Mascota> listarMascotasDestacadas();

    Mascota buscarPorId(Long id);
}

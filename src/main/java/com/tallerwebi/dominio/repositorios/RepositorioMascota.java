package com.tallerwebi.dominio.repositorios;

import com.tallerwebi.dominio.entidades.*;

import java.util.List;

public interface RepositorioMascota {
    void guardar(Mascota mascota);
    List<Mascota> listarMascotas();
    List<Mascota> listarMascotasDestacadas();
    Mascota buscarPorId(Long id);
    List<Mascota> buscarPorFiltros(Tipo tipo, Sexo sexo, Tamano tamano, NivelEnergia energia);
    List<Mascota> listarMascotasFiltradas(String tipo, String sexo, String tamano, String energia);
}

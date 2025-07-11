package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.entidades.*;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ServicioMascota {
    void guardar(Mascota mascota);
    List<MascotaDto> obtenerMascotas();
    List<MascotaDto> obtenerMascotasDestacadas();
    Mascota obtenerMascotaPorId(Long id);
    List<MascotaDto> obtenerMascotasFiltradas(Tipo tipo, Sexo sexo, Tamano tamano, NivelEnergia energia);
    void aprobarMascota(Long id);
    void rechazarMascota(Long id);
    List<MascotaDto> obtenerMascotasPorEstado(String estado);
    List<Mascota> obtenerMascotasPorEstadoEntidad(String estado);
    int contarMascotasPendientes();
}

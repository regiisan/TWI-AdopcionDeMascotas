package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.*;
import com.tallerwebi.dominio.repositorios.RepositorioMascota;
import com.tallerwebi.dominio.servicios.ServicioMascota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service("servicioMacota")
@Transactional
public class ServicioMascotaImpl implements ServicioMascota {

    private RepositorioMascota repositorioMascota;

    @Autowired
    public ServicioMascotaImpl(final RepositorioMascota repositorioMascota) {
        this.repositorioMascota = repositorioMascota;
    }

    @Override
    public void guardar(Mascota mascota) {
        // Si el usuario es admin, la mascota se aprueba automáticamente
        if (mascota.getUsuario() != null && 
            mascota.getUsuario().getRol() != null && 
            mascota.getUsuario().getRol().equals("ADMIN")) {
            mascota.setEstado("Aprobada");
        } else {
            // Si no es admin o no tiene rol, la mascota queda pendiente
            mascota.setEstado("Pendiente");
        }
        repositorioMascota.guardar(mascota);
    }

    @Override
    public List<MascotaDto> obtenerMascotas() {
        return repositorioMascota.listarMascotas().stream()
                .filter(m -> "Aprobada".equals(m.getEstado()))
                .map(MascotaDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<MascotaDto> obtenerMascotasDestacadas() {
        return repositorioMascota.listarMascotasDestacadas().stream()
                .filter(m -> "Aprobada".equals(m.getEstado()))
                .map(MascotaDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Mascota obtenerMascotaPorId(Long id) {
        return repositorioMascota.buscarPorId(id);
    }

    @Override
    public List<MascotaDto> obtenerMascotasFiltradas(Tipo tipo, Sexo sexo, Tamano tamano, NivelEnergia energia) {
        return repositorioMascota.buscarPorFiltros(tipo, sexo, tamano, energia)
                .stream()
                .filter(m -> "Aprobada".equals(m.getEstado()))
                .map(MascotaDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public void aprobarMascota(Long id) {
        Mascota mascota = repositorioMascota.buscarPorId(id);
        if (mascota != null) {
            mascota.setEstado("Aprobada");
            repositorioMascota.modificar(mascota);
        }
    }

    @Override
    public void rechazarMascota(Long id) {
        Mascota mascota = repositorioMascota.buscarPorId(id);
        if (mascota != null) {
            mascota.setEstado("Rechazada");
            repositorioMascota.modificar(mascota);
        }
    }

    @Override
    public List<MascotaDto> obtenerMascotasPorEstado(String estado) {
        return repositorioMascota.listarMascotas().stream()
                .filter(m -> estado.equals(m.getEstado()))
                .map(MascotaDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<Mascota> obtenerMascotasPorEstadoEntidad(String estado) {
        return repositorioMascota.listarMascotas().stream()
                .filter(m -> estado.equals(m.getEstado()))
                .collect(Collectors.toList());
    }
}

package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Mascota;
import com.tallerwebi.dominio.repositorios.RepositorioMascota;
import com.tallerwebi.dominio.servicios.ServicioMascota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
        repositorioMascota.guardar(mascota);
    }

    @Override
    public List<Mascota> obtenerMascotas() {
        return repositorioMascota.listarMascotas();
    }

    @Override
    public List<Mascota> obtenerMascotasDestacadas() {
        return repositorioMascota.listarMascotasDestacadas();
    }

    @Override
    public Mascota obtenerMascotaPorId(Long id) {
        return repositorioMascota.buscarPorId(id);
    }
}

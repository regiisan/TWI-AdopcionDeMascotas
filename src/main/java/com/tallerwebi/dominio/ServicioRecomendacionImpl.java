package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Mascota;
import com.tallerwebi.dominio.entidades.MascotaDto;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.repositorios.RepositorioMascota;
import com.tallerwebi.dominio.servicios.ServicioRecomendacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service("servicioRecomendacion")
public class ServicioRecomendacionImpl implements ServicioRecomendacion {

    private RepositorioMascota repositorioMascota;

    @Autowired
    public ServicioRecomendacionImpl(final RepositorioMascota repositorioMascota) {
        this.repositorioMascota = repositorioMascota;
    }

    @Override
    public List<MascotaDto> obtenerMascotasRecomendadas(Usuario usuario) {
        List<Mascota> mascotas = repositorioMascota.listarMascotas().stream()
                .filter(m -> "Aprobada".equals(m.getEstado()) && Boolean.FALSE.equals(m.getAdoptado()))
                .collect(Collectors.toList());
        List<MascotaDto> mascotasDtos = new ArrayList<>();

        for (Mascota mascota : mascotas) {
            int coincidencias = contadorCoincidencias(mascota, usuario);
            MascotaDto dto = new MascotaDto(mascota, coincidencias);
            mascotasDtos.add(dto);
        }

        mascotasDtos.sort((mascota1, mascota2) ->
            Integer.compare(mascota2.getCoincidencias(), mascota1.getCoincidencias())
        );

        return mascotasDtos;
    }

    private int contadorCoincidencias(Mascota mascota, Usuario usuario) {

        int coincidencias = 0;

        if(usuario.getTipoPreferido() != null && usuario.getTipoPreferido().equals(mascota.getTipo())){
            coincidencias+=3;
        }

        if(usuario.getEdadPreferida() != null && usuario.getEdadPreferida().equals(mascota.getEdad())){
            coincidencias++;
        }

        if(usuario.getSexoPreferido() != null && usuario.getSexoPreferido().equals(mascota.getSexo())){
            coincidencias++;
        }

        if(usuario.getTamanoPreferido() != null && usuario.getTamanoPreferido().equals(mascota.getTamano())){
            coincidencias++;
        }

        if(usuario.getNivelEnergiaPreferido() != null && usuario.getNivelEnergiaPreferido().equals(mascota.getNivelEnergia())){
            coincidencias++;
        }

        return coincidencias;
    }
}

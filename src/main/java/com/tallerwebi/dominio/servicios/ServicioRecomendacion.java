package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.entidades.Mascota;
import com.tallerwebi.dominio.entidades.MascotaDto;
import com.tallerwebi.dominio.entidades.Usuario;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ServicioRecomendacion {
    List<MascotaDto> obtenerMascotasRecomendadas(Usuario usuario);
}

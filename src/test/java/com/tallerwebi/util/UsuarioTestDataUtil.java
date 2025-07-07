package com.tallerwebi.util;

import com.tallerwebi.dominio.entidades.*;
import com.tallerwebi.presentacion.DatosRegistro;

public class UsuarioTestDataUtil {

    public static UsuarioDto crearUsuarioDtoTest(final Long id) {
        final UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setId(id);
        usuarioDto.setEmail("testEmail" + id);
        usuarioDto.setRol("testRol" + id);
        usuarioDto.setNombre("testNombre" + id);
        usuarioDto.setEdadPreferida(15);
        usuarioDto.setTipoPreferido(Tipo.GATO);
        usuarioDto.setTamanoPreferido(Tamano.CHICO);
        usuarioDto.setNivelEnergiaPreferido(NivelEnergia.ALTO);
        usuarioDto.setSexoPreferido(Sexo.HEMBRA);
        return usuarioDto;
    }

    public static DatosRegistro crearRegistroDtoTest(Long id) {
        DatosRegistro datosRegistro = new DatosRegistro();
        datosRegistro.setNombre("testNombre" + id);
        datosRegistro.setEmail("testEmail" + id);
        datosRegistro.setPassword("password123");
        return datosRegistro;
    }
}

package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.entidades.Usuario;

import javax.transaction.Transactional;

@Transactional
public interface ServicioUsuario {
    Usuario buscarPorId(Long id);
    Usuario buscar(String email);
    void guardar(Usuario usuario);
    void modificar(Usuario usuario);
    boolean tienePreferenciasCargadas(Usuario usuario);
    int contarUsuariosActivos();
}

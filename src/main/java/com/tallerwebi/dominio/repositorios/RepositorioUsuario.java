package com.tallerwebi.dominio.repositorios;

import com.tallerwebi.dominio.entidades.Usuario;

public interface RepositorioUsuario {
    Usuario buscarUsuario(String email);
    void guardar(Usuario usuario);
    Usuario buscarPorId(Long id);
    void modificar(Usuario usuario);
    int contarUsuariosActivos();
}


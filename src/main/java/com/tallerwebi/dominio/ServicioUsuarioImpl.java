package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.repositorios.RepositorioUsuario;
import com.tallerwebi.dominio.servicios.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioUsuario")
@Transactional
public class ServicioUsuarioImpl implements ServicioUsuario {

    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioUsuarioImpl(final RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public Usuario buscarPorId(Long id) {
        return repositorioUsuario.buscarPorId(id);
    }

    @Override
    public Usuario buscar(String email) {
        return repositorioUsuario.buscarUsuario(email);
    }

    @Override
    public void guardar(Usuario usuario) {
        repositorioUsuario.guardar(usuario);
    }

    @Override
    public void modificar(Usuario usuario) {
        repositorioUsuario.modificar(usuario);
    }

    @Override
    public boolean tienePreferenciasCargadas(Usuario usuario) {
        return usuario.getTipoPreferido() != null ||
                usuario.getSexoPreferido() != null ||
                usuario.getTamanoPreferido() != null ||
                usuario.getEdadPreferida() != null ||
                usuario.getNivelEnergiaPreferido() != null;
    }

    @Override
    public int contarUsuariosActivos() {
        return repositorioUsuario.contarUsuariosActivos();
    }


}

package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.repositorios.RepositorioUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ServicioUsuarioImplTest {

    private ServicioUsuarioImpl servicioUsuario;
    private RepositorioUsuario repositorioUsuarioMock;

    @BeforeEach
    public void init() {
        repositorioUsuarioMock = mock(RepositorioUsuario.class);
        servicioUsuario = new ServicioUsuarioImpl(repositorioUsuarioMock);
    }

    @Test
    public void debeBuscarPorId() {
        Usuario usuario = new Usuario();
        when(repositorioUsuarioMock.buscarPorId(1L)).thenReturn(usuario);

        Usuario resultado = servicioUsuario.buscarPorId(1L);

        assertThat(resultado, is(usuario));
    }

    @Test
    public void debeGuardarUsuario() {
        Usuario usuario = new Usuario();

        servicioUsuario.guardar(usuario);

        verify(repositorioUsuarioMock).guardar(usuario);
    }

    @Test
    public void debeModificarUsuario() {
        Usuario usuario = new Usuario();

        servicioUsuario.modificar(usuario);

        verify(repositorioUsuarioMock).modificar(usuario);
    }

    @Test
    public void debeRetornarTrueSiTienePreferenciasCargadas() {
        Usuario usuario = new Usuario();
        usuario.setEdadPreferida(5);

        boolean resultado = servicioUsuario.tienePreferenciasCargadas(usuario);

        assertThat(resultado, is(true));
    }

    @Test
    public void debeRetornarFalseSiNoTienePreferencias() {
        Usuario usuario = new Usuario();

        boolean resultado = servicioUsuario.tienePreferenciasCargadas(usuario);

        assertThat(resultado, is(false));
    }

    @Test
    public void dadoQueExistanUsuariosActivosDebeContarlos() {
        when(repositorioUsuarioMock.contarUsuariosActivos()).thenReturn(5);

        int usuariosActivos = servicioUsuario.contarUsuariosActivos();

        assertThat(usuariosActivos, is(5));
        verify(repositorioUsuarioMock, times(1)).contarUsuariosActivos();
    }

    @Test
    public void dadoQueNoExistanUsuariosActivosDebeRetornarCero() {
        when(repositorioUsuarioMock.contarUsuariosActivos()).thenReturn(0);

        int usuariosActivos = servicioUsuario.contarUsuariosActivos();

        assertThat(usuariosActivos, is(0));
        verify(repositorioUsuarioMock, times(1)).contarUsuariosActivos();
    }

}

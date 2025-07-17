package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.repositorios.RepositorioUsuario;
import com.tallerwebi.dominio.servicios.ServicioLogin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class ServicioLoginImplTest {

    private RepositorioUsuario repositorioUsuarioMock;
    private PasswordEncoder passwordEncoder;
    private ServicioLogin servicioLogin;

    @BeforeEach
    public void init(){
        repositorioUsuarioMock = mock(RepositorioUsuario.class);
        passwordEncoder = mock(PasswordEncoder.class);
        servicioLogin = new ServicioLoginImpl(repositorioUsuarioMock, passwordEncoder);
    }

    @Test
    public void dadoUsuarioNoEncontradoConsultarUsuarioDevuelveNull() {
        when(repositorioUsuarioMock.buscarUsuario("juan@gmail.com")).thenReturn(null);

        Usuario resultado = servicioLogin.consultarUsuario("juan@gmail.com", "1234");

        assertNull(resultado);
    }

    @Test
    public void dadoQueElUsuarioNoExistaDebeRegistrarlo() throws UsuarioExistente {
        Usuario usuario = new Usuario();
        usuario.setEmail("juan@gmail.com");
        usuario.setPassword("123");
        when(repositorioUsuarioMock.buscarUsuario("juan@gmail.com")).thenReturn(null);
        when(passwordEncoder.encode("123")).thenReturn("encrypted123");

        servicioLogin.registrar(usuario);

        assertThat(usuario.getPassword(), is("encrypted123"));
        verify(repositorioUsuarioMock).guardar(usuario);
    }

    @Test
    public void dadoQueElUsuarioExistadebeLanzarUnaExcepcion() {
        Usuario usuario = new Usuario();
        usuario.setEmail("juan@gmail.com");

        when(repositorioUsuarioMock.buscarUsuario("juan@gmail.com")).thenReturn(new Usuario());

        assertThrows(UsuarioExistente.class, () -> {servicioLogin.registrar(usuario);});
        verify(repositorioUsuarioMock, never()).guardar(any());
    }



}





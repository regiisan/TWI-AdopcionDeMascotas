package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.repositorios.RepositorioMascota;
import com.tallerwebi.dominio.repositorios.RepositorioUsuario;
import com.tallerwebi.dominio.servicios.ServicioLogin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioLoginImplTest {

    private RepositorioUsuario repositorioUsuarioMock;
    private PasswordEncoder passwordEncoder;
    private ServicioLogin servicioLogin;

    @BeforeEach
    public void init(){
        repositorioUsuarioMock = mock(RepositorioUsuario.class);
        passwordEncoder = new BCryptPasswordEncoder();
        servicioLogin = new ServicioLoginImpl(repositorioUsuarioMock, passwordEncoder);
    }

    @Test
    public void dadoUsuarioNoEncontradoConsultarUsuarioDevuelveNull() {
        when(repositorioUsuarioMock.buscarUsuario("juan@gmail.com")).thenReturn(null);

        Usuario resultado = servicioLogin.consultarUsuario("juan@gmail.com", "1234");

        assertNull(resultado);
    }



}





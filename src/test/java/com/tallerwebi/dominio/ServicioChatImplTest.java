package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Mensaje;
import com.tallerwebi.dominio.entidades.MensajeDto;
import com.tallerwebi.dominio.repositorios.RepositorioChat;
import com.tallerwebi.dominio.servicios.ServicioChat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioChatImplTest {

    private ServicioChat servicioChat;
    private RepositorioChat repositorioChatMock;

    @BeforeEach
    public void init(){
        repositorioChatMock = mock(RepositorioChat.class);
        servicioChat = new ServicioChatImpl(repositorioChatMock);
    }

    @Test
    public void dadoQueNoHayaMensajesDebeDevolverUnaListaVacia(){
        when(repositorioChatMock.obtenerMensajes()).thenReturn(new ArrayList<>());

        List<MensajeDto> mensajes = servicioChat.obtenerMensajes();

        assertThat(mensajes, empty());
    }

    @Test
    public void dadoQueSeCreenDosMensajesDebeDevolverUnaListaConDosMensajes(){
        Mensaje mensaje1 = mock(Mensaje.class);
        Mensaje mensaje2 = mock(Mensaje.class);
        when(mensaje1.getMessage()).thenReturn("Hola");
        when(mensaje1.getEmisorId()).thenReturn(1L);
        when(mensaje1.getNombreUsuario()).thenReturn("Roberto");
        when(mensaje1.getFecha()).thenReturn(LocalDateTime.now());
        when(mensaje2.getMessage()).thenReturn("Chau");
        when(mensaje2.getEmisorId()).thenReturn(2L);
        when(mensaje2.getNombreUsuario()).thenReturn("Alfonso");
        when(mensaje2.getFecha()).thenReturn(LocalDateTime.now());

        List<Mensaje> mensajesMock = Arrays.asList(mensaje1, mensaje2);
        when(repositorioChatMock.obtenerMensajes()).thenReturn(mensajesMock);

        List<MensajeDto> mensajes = servicioChat.obtenerMensajes();

        assertThat(mensajes.size(), is(2));
        assertThat(mensajes.get(0), instanceOf(MensajeDto.class));
    }
}

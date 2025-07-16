package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.MensajeDto;
import com.tallerwebi.dominio.entidades.Mensaje;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.servicios.ServicioChat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import javax.servlet.http.HttpSession;

import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;

public class ControladorChatSocketTest {

    private ControladorChatSocket controladorChatSocket;
    private ServicioChat servicioChat;
    private HttpSession sessionMock;
    private Usuario usuarioMock;

    @BeforeEach
    public void init(){
        servicioChat = mock(ServicioChat.class);
        sessionMock = mock(HttpSession.class);
        usuarioMock = mock(Usuario.class);
        controladorChatSocket = new ControladorChatSocket(servicioChat);
    }

    @Test
    public void debeConvertirMensajeRecibidoEnMensajeDtoCorrectamente() throws Exception {
        String mensaje = "Hola";
        Long emisorId = 1L;
        String nombreUsuario = "Juan";
        Mensaje mensajeRecibido = new Mensaje();
        mensajeRecibido.setMessage(mensaje);
        mensajeRecibido.setEmisorId(emisorId);
        mensajeRecibido.setNombreUsuario(nombreUsuario);
        mensajeRecibido.setFecha(LocalDateTime.now());

        MensajeDto mensajeDto = controladorChatSocket.getMessages(mensajeRecibido);

        assertThat(mensajeDto.getTexto(), is(mensaje));
        assertThat(mensajeDto.getEmisorId(), is(emisorId));
        assertThat(mensajeDto.getNombreUsuario(), is(nombreUsuario));
    }


}

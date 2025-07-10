package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.MensajeDto;
import com.tallerwebi.dominio.entidades.MensajeRecibido;
import com.tallerwebi.dominio.entidades.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.mock;

public class ControladorChatSocketTest {

    private ControladorChatSocket controladorChatSocket;
    private HttpSession sessionMock;
    private Usuario usuarioMock;

    @BeforeEach
    public void init(){
        controladorChatSocket = new ControladorChatSocket();
        sessionMock = mock(HttpSession.class);
        usuarioMock = mock(Usuario.class);
    }

    @Test
    public void debeConvertirMensajeRecibidoEnMensajeDtoCorrectamente() throws Exception {
        String mensaje = "Hola";
        Long emisorId = 1L;
        String nombreUsuario = "Juan";
        MensajeRecibido mensajeRecibido = new MensajeRecibido();
        mensajeRecibido.setMessage(mensaje);
        mensajeRecibido.setEmisorId(emisorId);
        mensajeRecibido.setNombreUsuario(nombreUsuario);

        MensajeDto mensajeDto = controladorChatSocket.getMessages(mensajeRecibido);

        assertThat(mensajeDto.getTexto(), is(mensaje));
        assertThat(mensajeDto.getEmisorId(), is(emisorId));
        assertThat(mensajeDto.getNombreUsuario(), is(nombreUsuario));
    }


}

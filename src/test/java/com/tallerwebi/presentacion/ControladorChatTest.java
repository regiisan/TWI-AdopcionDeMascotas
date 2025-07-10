package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorChatTest {

    private ControladorChat controladorChat;
    private HttpSession sessionMock;
    private Usuario usuarioMock;

    @BeforeEach
    public void init(){
        controladorChat = new ControladorChat();
        sessionMock = mock(HttpSession.class);
        usuarioMock = mock(Usuario.class);
    }

    @Test
    public void debeRedirigirAlLoginCuandoSeEjecutaElMetodoMostrarChatYElUsuarioNoEstaLogeado() {
        when(sessionMock.getAttribute("idUsuario")).thenReturn(null);

        ModelAndView modelAndView = controladorChat.mostrarChat(sessionMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
    }

    @Test
    public void debeMostrarVistaSalaChatConDatosDeUsuarioSiElUsuarioEstaLogeado() {
        Long idUsuario = 1L;
        String nombreUsuario = "Juan";
        when(sessionMock.getAttribute("idUsuario")).thenReturn(idUsuario);
        when(sessionMock.getAttribute("nombreUsuario")).thenReturn(nombreUsuario);

        ModelAndView modelAndView = controladorChat.mostrarChat(sessionMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("sala-chat"));
        assertThat(modelAndView.getModel().get("idUsuario"), is(idUsuario));
        assertThat(modelAndView.getModel().get("nombreUsuario"), is(nombreUsuario));
    }


}

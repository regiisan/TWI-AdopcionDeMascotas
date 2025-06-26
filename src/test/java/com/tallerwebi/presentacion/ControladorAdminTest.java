package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.SolicitudAdopcion;
import com.tallerwebi.dominio.servicios.ServicioSolicitudAdoptar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorAdminTest {

    private ControladorAdmin controladorAdmin;
    private ServicioSolicitudAdoptar servicioSolicitudAdoptar;
    private HttpSession sessionMock;

    @BeforeEach
    public void init(){
        servicioSolicitudAdoptar = mock(ServicioSolicitudAdoptar.class);
        sessionMock = mock(HttpSession.class);
        controladorAdmin = new ControladorAdmin(servicioSolicitudAdoptar);
    }

//    @Test
//    public void debeRetornarLaVistaSolicitudesDeAdopcionCuandoSeEjecutaElMetodoMostrarSolicitudesYElUsuarioEsADMIN(){
//        List<SolicitudAdopcion> solicitudesMock = Arrays.asList(mock(SolicitudAdopcion.class), mock(SolicitudAdopcion.class));
//        when(sessionMock.getAttribute("ROL")).thenReturn("ADMIN");
//        when(servicioSolicitudAdoptar.obtenerSolicitudes()).thenReturn(solicitudesMock);
//
//        ModelAndView modelAndView = controladorAdmin.mostrarSolicitudes("Aprobada",sessionMock);
//
//        assertThat(modelAndView.getViewName(), equalToIgnoringCase("solicitudesDeAdopcion"));
//        assertThat(modelAndView.getModel().get("solicitudes"), is(solicitudesMock));
//    }

    @Test
    public void debeRedirigirAlHomeCuandoSeEjecutaElMetodoMostrarSolicitudesYElUsuarioNoEsADMIN() {
        when(sessionMock.getAttribute("ROL")).thenReturn("USUARIO");

        ModelAndView modelAndView = controladorAdmin.mostrarSolicitudes("Aprobada",sessionMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/home"));
    }

    @Test
    public void debeRedirigirAlHomeCuandoSeEjecutaElMetodoMostrarSolicitudesYElUsuarioEsNull() {
        when(sessionMock.getAttribute("ROL")).thenReturn(null);

        ModelAndView modelAndView = controladorAdmin.mostrarSolicitudes("Aprobada",sessionMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/home"));
    }

}

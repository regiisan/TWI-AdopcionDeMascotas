package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.SolicitudAdopcion;
import com.tallerwebi.dominio.servicios.ServicioSolicitudAdoptar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ControladorAdminTest {

    private ControladorAdmin controladorAdmin;
    private ServicioSolicitudAdoptar servicioSolicitudAdoptarMock;
    private HttpSession sessionMock;

    @BeforeEach
    public void init() {
        servicioSolicitudAdoptarMock = mock(ServicioSolicitudAdoptar.class);
        sessionMock = mock(HttpSession.class);
        controladorAdmin = new ControladorAdmin(servicioSolicitudAdoptarMock);
    }

    @Test
    public void siElUsuarioNoEsAdminDebeRedirigirAlHome() {
        // Preparación
        when(sessionMock.getAttribute("ROL")).thenReturn("USUARIO");

        // Ejecución
        ModelAndView modelAndView = controladorAdmin.mostrarSolicitudes(null, sessionMock);

        // Verificación
        assertThat(modelAndView.getViewName(), is("redirect:/home"));
    }

    @Test
    public void siElUsuarioEsAdminDebeMostrarTodasLasSolicitudes() {
        // Preparación
        when(sessionMock.getAttribute("ROL")).thenReturn("ADMIN");
        List<SolicitudAdopcion> solicitudesMock = Arrays.asList(mock(SolicitudAdopcion.class), mock(SolicitudAdopcion.class));
        when(servicioSolicitudAdoptarMock.obtenerSolicitudes()).thenReturn(solicitudesMock);

        // Ejecución
        ModelAndView modelAndView = controladorAdmin.mostrarSolicitudes(null, sessionMock);

        // Verificación
        assertThat(modelAndView.getViewName(), is("solicitudesDeAdopcion"));
        assertThat(modelAndView.getModel().get("solicitudes"), is(solicitudesMock));
    }

    @Test
    public void siElUsuarioEsAdminYFiltrarPorEstadoDebeMostrarSoloLasSolicitudesConEseEstado() {
        // Preparación
        when(sessionMock.getAttribute("ROL")).thenReturn("ADMIN");
        SolicitudAdopcion solicitudAprobada = mock(SolicitudAdopcion.class);
        when(solicitudAprobada.getEstado()).thenReturn("APROBADA");
        SolicitudAdopcion solicitudPendiente = mock(SolicitudAdopcion.class);
        when(solicitudPendiente.getEstado()).thenReturn("PENDIENTE");
        List<SolicitudAdopcion> todasLasSolicitudes = Arrays.asList(solicitudAprobada, solicitudPendiente);
        when(servicioSolicitudAdoptarMock.obtenerSolicitudes()).thenReturn(todasLasSolicitudes);

        // Ejecución
        ModelAndView modelAndView = controladorAdmin.mostrarSolicitudes("APROBADA", sessionMock);

        // Verificación
        assertThat(modelAndView.getViewName(), is("solicitudesDeAdopcion"));
        List<SolicitudAdopcion> solicitudesFiltradas = (List<SolicitudAdopcion>) modelAndView.getModel().get("solicitudes");
        assertThat(solicitudesFiltradas, hasSize(1));
        assertThat(solicitudesFiltradas.get(0).getEstado(), is("APROBADA"));
    }

    @Test
    public void alAprobarSolicitudSiNoEsAdminNoDebeHacerNada() {
        // Preparación
        when(sessionMock.getAttribute("ROL")).thenReturn("USUARIO");
        Long idSolicitud = 1L;

        // Ejecución
        ModelAndView modelAndView = controladorAdmin.aprobarSolicitud(idSolicitud, sessionMock);

        // Verificación
        verify(servicioSolicitudAdoptarMock, never()).aprobarSolicitud(idSolicitud);
        assertThat(modelAndView.getViewName(), is("redirect:/admin/solicitudes"));
    }

    @Test
    public void alAprobarSolicitudSiEsAdminDebeAprobarla() {
        // Preparación
        when(sessionMock.getAttribute("ROL")).thenReturn("ADMIN");
        Long idSolicitud = 1L;

        // Ejecución
        ModelAndView modelAndView = controladorAdmin.aprobarSolicitud(idSolicitud, sessionMock);

        // Verificación
        verify(servicioSolicitudAdoptarMock).aprobarSolicitud(idSolicitud);
        assertThat(modelAndView.getViewName(), is("redirect:/admin/solicitudes"));
    }

    @Test
    public void alRechazarSolicitudSiNoEsAdminNoDebeHacerNada() {
        // Preparación
        when(sessionMock.getAttribute("ROL")).thenReturn("USUARIO");
        Long idSolicitud = 1L;

        // Ejecución
        ModelAndView modelAndView = controladorAdmin.rechazarSolicitud(idSolicitud, sessionMock);

        // Verificación
        verify(servicioSolicitudAdoptarMock, never()).rechazarSolicitud(idSolicitud);
        assertThat(modelAndView.getViewName(), is("redirect:/admin/solicitudes"));
    }

    @Test
    public void alRechazarSolicitudSiEsAdminDebeRechazarla() {
        // Preparación
        when(sessionMock.getAttribute("ROL")).thenReturn("ADMIN");
        Long idSolicitud = 1L;

        // Ejecución
        ModelAndView modelAndView = controladorAdmin.rechazarSolicitud(idSolicitud, sessionMock);

        // Verificación
        verify(servicioSolicitudAdoptarMock).rechazarSolicitud(idSolicitud);
        assertThat(modelAndView.getViewName(), is("redirect:/admin/solicitudes"));
    }

    @Test
    public void siElUsuarioEsAdminYNoHaySolicitudesDebeMostrarListaVacia() {
        // Preparación
        when(sessionMock.getAttribute("ROL")).thenReturn("ADMIN");
        when(servicioSolicitudAdoptarMock.obtenerSolicitudes()).thenReturn(Arrays.asList());

        // Ejecución
        ModelAndView modelAndView = controladorAdmin.mostrarSolicitudes(null, sessionMock);

        // Verificación
        assertThat(modelAndView.getViewName(), is("solicitudesDeAdopcion"));
        List<SolicitudAdopcion> solicitudes = (List<SolicitudAdopcion>) modelAndView.getModel().get("solicitudes");
        assertThat(solicitudes, empty());
    }
}

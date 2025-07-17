package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.SolicitudAdopcion;
import com.tallerwebi.dominio.servicios.ServicioMascota;
import com.tallerwebi.dominio.servicios.ServicioSolicitudAdoptar;
import com.tallerwebi.dominio.servicios.ServicioUsuario;
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
    private ServicioSolicitudAdoptar servicioSolicitudAdoptar;
    private ServicioMascota servicioMascota;
    private ServicioUsuario servicioUsuario;
    private HttpSession sessionMock;

    @BeforeEach
    public void init(){
        servicioSolicitudAdoptar = mock(ServicioSolicitudAdoptar.class);
        servicioMascota = mock(ServicioMascota.class);
        servicioUsuario = mock(ServicioUsuario.class);
        sessionMock = mock(HttpSession.class);
        controladorAdmin = new ControladorAdmin(servicioSolicitudAdoptar,servicioMascota, servicioUsuario);
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
    public void siElUsuarioEsNullDebeRedirigirAlHome() {
        // Preparación
        when(sessionMock.getAttribute("ROL")).thenReturn(null);

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
        when(servicioSolicitudAdoptar.obtenerSolicitudes()).thenReturn(solicitudesMock);

        // Ejecución
        ModelAndView modelAndView = controladorAdmin.mostrarSolicitudes(null, sessionMock);

        // Verificación
        assertThat(modelAndView.getViewName(), is("solicitudesDeAdopcion"));
        assertThat(modelAndView.getModel().get("solicitudes"), is(solicitudesMock));
    }

    @Test
    public void siElUsuarioEsAdminYFiltrarPorEstadoDebeMostrarLasSolicitudesConEseEstado() {
        // Preparación
        SolicitudAdopcion solicitudAprobada = mock(SolicitudAdopcion.class);
        when(sessionMock.getAttribute("ROL")).thenReturn("ADMIN");
        when(solicitudAprobada.getEstado()).thenReturn("APROBADA");
        when(servicioSolicitudAdoptar.obtenerSolicitudesPorEstado("APROBADA")).thenReturn(List.of(solicitudAprobada));

        // Ejecución
        ModelAndView modelAndView = controladorAdmin.mostrarSolicitudes("APROBADA", sessionMock);

        // Verificación
        assertThat(modelAndView.getViewName(), is("solicitudesDeAdopcion"));
        List<SolicitudAdopcion> solicitudesFiltradas = (List<SolicitudAdopcion>) modelAndView.getModel().get("solicitudes");
        assertThat(solicitudesFiltradas, hasSize(1));
        assertThat(solicitudesFiltradas.get(0).getEstado(), is("APROBADA"));
    }

    @Test
    public void debeRetornarLaVistaSolicitudesDeAdopcionCuandoSeEjecutaElMetodoMostrarSolicitudesYElUsuarioEsADMIN(){
        List<SolicitudAdopcion> solicitudesMock = Arrays.asList(mock(SolicitudAdopcion.class), mock(SolicitudAdopcion.class));
        when(sessionMock.getAttribute("ROL")).thenReturn("ADMIN");
        when(servicioSolicitudAdoptar.obtenerSolicitudes()).thenReturn(solicitudesMock);

        ModelAndView modelAndView = controladorAdmin.mostrarSolicitudes(null,sessionMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("solicitudesDeAdopcion"));
        assertThat(modelAndView.getModel().get("solicitudes"), is(solicitudesMock));
    }

    @Test
    public void alAprobarSolicitudSiNoEsAdminNoDebeHacerNada() {
        // Preparación
        when(sessionMock.getAttribute("ROL")).thenReturn("USUARIO");
        Long idSolicitud = 1L;

        // Ejecución
        ModelAndView modelAndView = controladorAdmin.aprobarSolicitud(idSolicitud, sessionMock);

        // Verificación
        verify(servicioSolicitudAdoptar, never()).aprobarSolicitud(idSolicitud);
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
        verify(servicioSolicitudAdoptar).aprobarSolicitud(idSolicitud);
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
        verify(servicioSolicitudAdoptar, never()).rechazarSolicitud(idSolicitud);
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
        verify(servicioSolicitudAdoptar).rechazarSolicitud(idSolicitud);
        assertThat(modelAndView.getViewName(), is("redirect:/admin/solicitudes"));
    }

    @Test
    public void siElUsuarioEsAdminYNoHaySolicitudesDebeMostrarListaVacia() {
        // Preparación
        when(sessionMock.getAttribute("ROL")).thenReturn("ADMIN");
        when(servicioSolicitudAdoptar.obtenerSolicitudes()).thenReturn(Arrays.asList());

        // Ejecución
        ModelAndView modelAndView = controladorAdmin.mostrarSolicitudes(null, sessionMock);

        // Verificación
        assertThat(modelAndView.getViewName(), is("solicitudesDeAdopcion"));
        List<SolicitudAdopcion> solicitudes = (List<SolicitudAdopcion>) modelAndView.getModel().get("solicitudes");
        assertThat(solicitudes, empty());
    }

    @Test
    public void debeFiltrarSolicitudesPorEstado() {
        List<SolicitudAdopcion> solicitudesFiltradas = Arrays.asList(mock(SolicitudAdopcion.class));
        when(sessionMock.getAttribute("ROL")).thenReturn("ADMIN");
        when(servicioSolicitudAdoptar.obtenerSolicitudesPorEstado("Pendiente")).thenReturn(solicitudesFiltradas);

        ModelAndView modelAndView = controladorAdmin.mostrarSolicitudes("Pendiente", sessionMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("solicitudesDeAdopcion"));
        assertThat(modelAndView.getModel().get("solicitudes"), is(solicitudesFiltradas));
        assertThat(modelAndView.getModel().get("estadoSeleccionado"), is("Pendiente"));
    }

    @Test
    public void debeRedirigirAlHomeCuandoElUsuarioNoEsAdminYQuiereAccederAlHomeAdmin() {
        when(sessionMock.getAttribute("ROL")).thenReturn("USUARIO");

        ModelAndView modelAndView = controladorAdmin.mostrarEstadisticas(sessionMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/home"));
    }

    @Test
    public void debeRedirigirAlHomeCuandoElUsuarioEsNullYQuiereAccederAlHomeAdmin() {
        when(sessionMock.getAttribute("ROL")).thenReturn(null);

        ModelAndView modelAndView = controladorAdmin.mostrarEstadisticas(sessionMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/home"));
    }

    @Test
    public void debeMostrarVistaHomeAdminSiElUsuarioEsAdmin() {
        when(sessionMock.getAttribute("ROL")).thenReturn("ADMIN");

        ModelAndView modelAndView = controladorAdmin.mostrarEstadisticas(sessionMock);

        assertThat(modelAndView.getViewName(), is("homeAdmin"));
        verify(sessionMock, times(1)).getAttribute("ROL");
    }

    @Test
    public void debeMostrarEstadisticasSiElUsuarioEsAdmin() {
        when(sessionMock.getAttribute("ROL")).thenReturn("ADMIN");
        when(servicioSolicitudAdoptar.contarSolicitudesPendientes()).thenReturn(3);
        when(servicioMascota.contarMascotasPendientes()).thenReturn(2);
        when(servicioSolicitudAdoptar.contarSolicitudesAprobadas()).thenReturn(5);
        when(servicioUsuario.contarUsuariosActivos()).thenReturn(10);

        ModelAndView modelAndView = controladorAdmin.mostrarEstadisticas(sessionMock);

        assertThat(modelAndView.getModel().get("solicitudesPendientes"), is(3));
        assertThat(modelAndView.getModel().get("mascotasPendientes"), is(2));
        assertThat(modelAndView.getModel().get("adopcionesExitosas"), is(5));
        assertThat(modelAndView.getModel().get("usuariosActivos"), is(10));
        verify(servicioSolicitudAdoptar, times(1)).contarSolicitudesPendientes();
        verify(servicioMascota, times(1)).contarMascotasPendientes();
        verify(servicioSolicitudAdoptar, times(1)).contarSolicitudesAprobadas();
        verify(servicioUsuario, times(1)).contarUsuariosActivos();
    }

    @Test
    public void debeRetornarCerosEnLasEstadisticasSiNoHaySolicitudesNiUsuarios() {
        when(sessionMock.getAttribute("ROL")).thenReturn("ADMIN");
        when(servicioSolicitudAdoptar.contarSolicitudesPendientes()).thenReturn(0);
        when(servicioMascota.contarMascotasPendientes()).thenReturn(0);
        when(servicioSolicitudAdoptar.contarSolicitudesAprobadas()).thenReturn(0);
        when(servicioUsuario.contarUsuariosActivos()).thenReturn(0);

        ModelAndView modelAndView = controladorAdmin.mostrarEstadisticas(sessionMock);

        assertThat(modelAndView.getModel().get("solicitudesPendientes"), is(0));
        assertThat(modelAndView.getModel().get("mascotasPendientes"), is(0));
        assertThat(modelAndView.getModel().get("adopcionesExitosas"), is(0));
        assertThat(modelAndView.getModel().get("usuariosActivos"), is(0));
    }

}

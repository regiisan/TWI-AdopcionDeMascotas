package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.Mascota;
import com.tallerwebi.dominio.entidades.SolicitudAdopcion;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.servicios.ServicioMascota;
import com.tallerwebi.dominio.servicios.ServicioSolicitudAdoptar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.servlet.http.HttpSession;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class ControladorSolicitudAdoptarTest {

    private ControladorSolicitudAdoptar controladorSolicitudAdoptar;
    private ServicioMascota servicioMascotaMock;
    private ServicioSolicitudAdoptar servicioSolicitudAdoptarMock;
    private HttpSession sessionMock;
    private Usuario usuarioMock;

    @BeforeEach
    public void init(){
        servicioMascotaMock = mock(ServicioMascota.class);
        servicioSolicitudAdoptarMock = mock(ServicioSolicitudAdoptar.class);
        sessionMock = mock(HttpSession.class);
        usuarioMock = mock(Usuario.class);
        controladorSolicitudAdoptar = new ControladorSolicitudAdoptar(servicioMascotaMock, servicioSolicitudAdoptarMock);
    }

    @Test
    public void debeRedirigirAlLoginCuandoSeEjecutaElMetodoMostrarFormularioAdopcionYElUsuarioNoEstaLogueado() {
        when(sessionMock.getAttribute("idUsuario")).thenReturn(null);

        ModelAndView modelAndView = controladorSolicitudAdoptar.mostrarFormularioAdopcion(1L, sessionMock);

        assertEquals("redirect:/login", modelAndView.getViewName());
    }

    @Test
    public void debeRetornarLaVistaFormularioAdopcionCuandoSeEjecutaElMetodoMostrarFormularioAdopcionYElUsuarioEstaLogeado(){
        when(sessionMock.getAttribute("idUsuario")).thenReturn(1L);

        ModelAndView modelAndView = controladorSolicitudAdoptar.mostrarFormularioAdopcion(1L, sessionMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("formulario-adopcion"));
    }

    @Test
    public void debeCargarMascotaYSolicitudEnElModeloAlMostrarElFormularioDeAdopcion() {
        Long idUsuario = 1L;
        Long idMascota = 10L;
        Mascota mascotaMock = mock(Mascota.class);
        when(sessionMock.getAttribute("idUsuario")).thenReturn(idUsuario);
        when(servicioMascotaMock.obtenerMascotaPorId(idMascota)).thenReturn(mascotaMock);

        ModelAndView modelAndView = controladorSolicitudAdoptar.mostrarFormularioAdopcion(idMascota, sessionMock);

        assertEquals(mascotaMock, modelAndView.getModel().get("mascota"));
        assertThat(((SolicitudAdopcion) modelAndView.getModel().get("solicitud")).getMascota(), is(mascotaMock));
    }

    @Test
    public void debeGuardarLaSolicitudConMascotaYEstadoPendienteYRedirigir() {
        Long idMascota = 10L;
        Mascota mascotaMock = mock(Mascota.class);
        SolicitudAdopcion solicitudMock = new SolicitudAdopcion();
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

        when(servicioMascotaMock.obtenerMascotaPorId(idMascota)).thenReturn(mascotaMock);

        ModelAndView vista = controladorSolicitudAdoptar.guardarSolicitudAdopcion(solicitudMock, idMascota, redirectAttributes);

        assertThat(vista.getViewName(), equalToIgnoringCase("redirect:/mascotas"));
        assertThat(solicitudMock.getMascota(), is(mascotaMock));
        assertThat(solicitudMock.getEstado(), is("Pendiente"));
        verify(servicioSolicitudAdoptarMock).guardar(solicitudMock);
    }

    @Test
    public void noDebeGuardarLaSolicitudCuandoMascotaEsNull() {
        Long idMascota = 10L;
        SolicitudAdopcion solicitudMock = new SolicitudAdopcion();
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        when(servicioMascotaMock.obtenerMascotaPorId(idMascota)).thenReturn(null);

        ModelAndView vista = controladorSolicitudAdoptar.guardarSolicitudAdopcion(solicitudMock, idMascota, redirectAttributes);

        assertThat(vista.getViewName(), equalToIgnoringCase("redirect:/mascotas"));
        assertNull(solicitudMock.getMascota());
        assertNull(solicitudMock.getEstado());
        verify(servicioSolicitudAdoptarMock, never()).guardar(any());
    }

}

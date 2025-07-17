package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.*;
import com.tallerwebi.dominio.servicios.ServicioMascota;
import com.tallerwebi.dominio.servicios.ServicioRecomendacion;
import com.tallerwebi.dominio.servicios.ServicioUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.isA;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class ControladorMascotaTest {
    private ControladorMascota controladorMascota;
    private ServicioMascota servicioMascotaMock;
    private ServicioRecomendacion servicioRecomendacionMock;
    private ServicioUsuario servicioUsuarioMock;
    private HttpSession sessionMock;
    private Usuario usuarioMock;

    @BeforeEach
    public void init() {
        servicioMascotaMock = mock(ServicioMascota.class);
        servicioRecomendacionMock = mock(ServicioRecomendacion.class);
        servicioUsuarioMock = mock(ServicioUsuario.class);
        controladorMascota = new ControladorMascota(servicioMascotaMock, servicioRecomendacionMock, servicioUsuarioMock);
        sessionMock = mock(HttpSession.class);
        usuarioMock = mock(Usuario.class);
    }

    @Test
    public void debeRetornarLaVistaMascotasCuandoSeEjecutaElMetodoMostrarMascotas() {
        List<MascotaDto> mascotasMock = Arrays.asList(mock(MascotaDto.class), mock(MascotaDto.class));
        when(servicioMascotaMock.obtenerMascotas()).thenReturn(mascotasMock);

        ModelAndView modelAndView = controladorMascota.mostrarMascotas(sessionMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("mascotas"));
    }

    @Test
    public void debeRetornarLaVistaHomeCuandoSeEjecutaElMetodoMostrarMascotasDestacadas() {
        List<MascotaDto> mascotasDestacadasMock = Arrays.asList(mock(MascotaDto.class), mock(MascotaDto.class));
        when(servicioMascotaMock.obtenerMascotasDestacadas()).thenReturn(mascotasDestacadasMock);

        ModelAndView modelAndView = controladorMascota.mostrarMascotasDestacadas();

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("home"));
    }

    @Test
    public void debeMostrarMascotasRecomendadasSiElUsuarioEstaLogeadoYTienePreferencias() {
        Long idUsuario = 1L;
        List<MascotaDto> mascotasMock = Arrays.asList(mock(MascotaDto.class), mock(MascotaDto.class));
        when(sessionMock.getAttribute("idUsuario")).thenReturn(idUsuario);
        when(servicioUsuarioMock.buscarPorId(idUsuario)).thenReturn(usuarioMock);
        when(servicioRecomendacionMock.obtenerMascotasRecomendadas(usuarioMock)).thenReturn(mascotasMock);

        ModelAndView modelAndView = controladorMascota.mostrarMascotas(sessionMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("mascotas"));
        assertThat(modelAndView.getModel().get("mascotas"), is(mascotasMock));
        assertThat(modelAndView.getModel().get("usuario"), is(usuarioMock));
    }

    @Test
    public void debeMostrarTodasLasMascotasSiElUsuarioNoEstaLogeado() {
        List<MascotaDto> mascotasMock = Arrays.asList(mock(MascotaDto.class), mock(MascotaDto.class));

        when(sessionMock.getAttribute("idUsuario")).thenReturn(null);
        when(servicioMascotaMock.obtenerMascotas()).thenReturn(mascotasMock);

        ModelAndView modelAndView = controladorMascota.mostrarMascotas(sessionMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("mascotas"));
        assertNull(modelAndView.getModel().get("usuario"));
    }

    @Test
    public void debeOcultarLaSugerenciaDePreferenciasSiElUsuarioYaLasCargo() {
        Long idUsuario = 1L;
        when(sessionMock.getAttribute("idUsuario")).thenReturn(idUsuario);
        when(servicioUsuarioMock.buscarPorId(idUsuario)).thenReturn(usuarioMock);
        when(servicioUsuarioMock.tienePreferenciasCargadas(usuarioMock)).thenReturn(true);

        ModelAndView modelAndView = controladorMascota.mostrarMascotas(sessionMock);

        assertThat(modelAndView.getModel().get("mostrarSugerenciaDePreferencias"), is(false));
    }

    @Test
    public void debeMostrarLaSugerenciaDePreferenciasSiElUsuarioNoLasCargo() {
        Long idUsuario = 1L;
        when(sessionMock.getAttribute("idUsuario")).thenReturn(idUsuario);
        when(servicioUsuarioMock.buscarPorId(idUsuario)).thenReturn(usuarioMock);
        when(servicioUsuarioMock.tienePreferenciasCargadas(usuarioMock)).thenReturn(false);

        ModelAndView modelAndView = controladorMascota.mostrarMascotas(sessionMock);

        assertThat(modelAndView.getModel().get("mostrarSugerenciaDePreferencias"), is(true));
    }

    @Test
    public void debeFiltrarMascotasSegunParametrosYRetornarVista() {
        List<MascotaDto> mascotasFiltradas = Arrays.asList(mock(MascotaDto.class), mock(MascotaDto.class));
        when(servicioMascotaMock.obtenerMascotasFiltradas(Tipo.PERRO, Sexo.MACHO, Tamano.MEDIANO, NivelEnergia.ALTO))
                .thenReturn(mascotasFiltradas);

        ModelAndView modelAndView = controladorMascota.filtrarMascotas(Tipo.PERRO, Sexo.MACHO, Tamano.MEDIANO, NivelEnergia.ALTO, sessionMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("mascotas"));
        assertThat(modelAndView.getModel().get("mascotas"), is(mascotasFiltradas));
    }

    @Test
    public void debeAprobarMascotaCuandoElUsuarioEsAdmin() {
        Long idMascota = 10L;
        when(sessionMock.getAttribute("ROL")).thenReturn("ADMIN");

        ModelAndView modelAndView = controladorMascota.aprobarMascota(idMascota, sessionMock);

        verify(servicioMascotaMock).aprobarMascota(idMascota);
        assertEquals("redirect:/mascota/nueva", modelAndView.getViewName());
    }

    @Test
    public void noDebeAprobarMascotaCuandoElUsuarioNoEsAdmin() {
        Long idMascota = 10L;
        when(sessionMock.getAttribute("ROL")).thenReturn("USUARIO");

        ModelAndView modelAndView = controladorMascota.aprobarMascota(idMascota, sessionMock);

        verify(servicioMascotaMock, never()).aprobarMascota(anyLong());
        assertEquals("redirect:/mascota/nueva", modelAndView.getViewName());
    }

    @Test
    public void noDebeAprobarMascotaCuandoElUsuarioNoTieneRol() {
        Long idMascota = 10L;
        when(sessionMock.getAttribute("ROL")).thenReturn(null);

        ModelAndView modelAndView = controladorMascota.aprobarMascota(idMascota, sessionMock);

        verify(servicioMascotaMock, never()).aprobarMascota(anyLong());
        assertEquals("redirect:/mascota/nueva", modelAndView.getViewName());
    }

    @Test
    public void debeRechazarMascotaCuandoElUsuarioEsAdmin() {
        Long idMascota = 10L;
        when(sessionMock.getAttribute("ROL")).thenReturn("ADMIN");

        ModelAndView modelAndView = controladorMascota.rechazarMascota(idMascota, sessionMock);

        verify(servicioMascotaMock).rechazarMascota(idMascota);
        assertEquals("redirect:/mascota/nueva", modelAndView.getViewName());
    }

    @Test
    public void noDebeRechazarMascotaCuandoElUsuarioNoEsAdmin() {
        Long idMascota = 10L;
        when(sessionMock.getAttribute("ROL")).thenReturn("USUARIO");

        ModelAndView modelAndView = controladorMascota.rechazarMascota(idMascota, sessionMock);

        verify(servicioMascotaMock, never()).rechazarMascota(anyLong());
        assertEquals("redirect:/mascota/nueva", modelAndView.getViewName());
    }

    @Test
    public void noDebeRechazarMascotaCuandoElUsuarioNoTieneRol() {
        Long idMascota = 10L;
        when(sessionMock.getAttribute("ROL")).thenReturn(null);

        ModelAndView modelAndView = controladorMascota.rechazarMascota(idMascota, sessionMock);

        verify(servicioMascotaMock, never()).rechazarMascota(anyLong());
        assertEquals("redirect:/mascota/nueva", modelAndView.getViewName());
    }

    @Test
    public void debeRedirigirAlLoginSiElUsuarioQuiereAdoptarPeroNoInicioSesion() {
        when(sessionMock.getAttribute("idUsuario")).thenReturn(null);

        ModelAndView modelAndView = controladorMascota.mostrarFormularioAdopcion(sessionMock);

        assertEquals("redirect:/login", modelAndView.getViewName());
    }

    @Test
    public void debeMostrarFormularioAdopcionSiElUsuarioInicioSesionYNoEsAdmin() {
        Long idUsuario = 1L;
        when(sessionMock.getAttribute("idUsuario")).thenReturn(idUsuario);
        when(sessionMock.getAttribute("ROL")).thenReturn("USUARIO");

        ModelAndView modelAndView = controladorMascota.mostrarFormularioAdopcion(sessionMock);

        assertEquals("formulario-dar-en-adopcion", modelAndView.getViewName());
        assertThat(modelAndView.getModel().get("mascota"), isA(Mascota.class));
        assertNull(modelAndView.getModel().get("mascotasPendientes"));
    }

    @Test
    public void debeMostrarFormularioAdopcionSiElUsuarioInicioSesionYSuRolEsNull() {
        Long idUsuario = 1L;
        when(sessionMock.getAttribute("idUsuario")).thenReturn(idUsuario);
        when(sessionMock.getAttribute("ROL")).thenReturn(null);

        ModelAndView modelAndView = controladorMascota.mostrarFormularioAdopcion(sessionMock);

        assertEquals("formulario-dar-en-adopcion", modelAndView.getViewName());
        assertThat(modelAndView.getModel().get("mascota"), isA(Mascota.class));
        assertNull(modelAndView.getModel().get("mascotasPendientes"));
    }

    @Test
    public void debeMostrarFormularioAdopcionYListaDeSolicitudesSiElUsuarioEsAdmin() {
        Long idUsuario = 1L;
        List<Mascota> pendientesMock = Arrays.asList(mock(Mascota.class), mock(Mascota.class));
        when(sessionMock.getAttribute("idUsuario")).thenReturn(idUsuario);
        when(sessionMock.getAttribute("ROL")).thenReturn("ADMIN");
        when(servicioMascotaMock.obtenerMascotasPorEstadoEntidad("Pendiente")).thenReturn(pendientesMock);

        ModelAndView modelAndView = controladorMascota.mostrarFormularioAdopcion(sessionMock);

        assertEquals("formulario-dar-en-adopcion", modelAndView.getViewName());
        assertThat(modelAndView.getModel().get("mascota"), isA(Mascota.class));
        assertThat(modelAndView.getModel().get("mascotasPendientes"), is(pendientesMock));
    }

    @Test
    public void debeRedirigirAlLoginSiElUsuarioEsNullYQuiereSubirUnaMascota() {
        RedirectAttributes redirectAttributesMock = mock(RedirectAttributes.class);
        when(sessionMock.getAttribute("idUsuario")).thenReturn(null);

        ModelAndView mv = controladorMascota.guardarMascota(new Mascota(), mock(MultipartFile.class), sessionMock, redirectAttributesMock);

        assertEquals("redirect:/login", mv.getViewName());
    }

    /*
    @Test
    public void debeGuardarYPublicarMascotaSiEsAdmin() throws Exception {
        Mascota mascota = new Mascota();
        MultipartFile imagenMock = mock(MultipartFile.class);
        RedirectAttributes redirectAttributesMock = mock(RedirectAttributes.class);
        when(sessionMock.getAttribute("idUsuario")).thenReturn(1L);
        when(sessionMock.getAttribute("ROL")).thenReturn("ADMIN");
        when(imagenMock.getOriginalFilename()).thenReturn("foto.jpg");
        when(imagenMock.getBytes()).thenReturn(new byte[]{1,2,3});
        when(servicioUsuarioMock.buscarPorId(1L)).thenReturn(usuarioMock);

        ModelAndView model = controladorMascota.guardarMascota(mascota, imagenMock, sessionMock, redirectAttributesMock);

        verify(servicioMascotaMock).guardar(any(Mascota.class));
        verify(redirectAttributesMock).addFlashAttribute("mensaje", "¡Mascota agregada correctamente!");
        assertEquals("redirect:/mascotas", model.getViewName());
    }

    @Test
    public void debeGuardarMascotaYRedirigirAHomeSiNoEsAdmin() throws Exception {
        Mascota mascota = new Mascota();
        MultipartFile imagenMock = mock(MultipartFile.class);
        RedirectAttributes redirectAttributesMock = mock(RedirectAttributes.class);
        when(sessionMock.getAttribute("idUsuario")).thenReturn(1L);
        when(sessionMock.getAttribute("ROL")).thenReturn("USUARIO");
        when(imagenMock.getOriginalFilename()).thenReturn("foto.jpg");
        when(imagenMock.getBytes()).thenReturn(new byte[]{1,2,3});
        when(servicioUsuarioMock.buscarPorId(1L)).thenReturn(usuarioMock);

        ModelAndView model = controladorMascota.guardarMascota(mascota, imagenMock, sessionMock, redirectAttributesMock);

        verify(servicioMascotaMock).guardar(any(Mascota.class));
        verify(redirectAttributesMock).addFlashAttribute("mensaje", "¡Publicación exitosa! Te enviaremos un correo electrónico cuando tu mascota esté visible para los adoptantes.");
        assertEquals("redirect:/home", model.getViewName());
    }

    @Test
    public void debeMostrarFormularioConErrorCuandoFallaLaCargaDeImagen() throws Exception {
        Mascota mascota = new Mascota();
        MultipartFile imagenMock = mock(MultipartFile.class);
        RedirectAttributes redirectAttributesMock = mock(RedirectAttributes.class);
        when(sessionMock.getAttribute("idUsuario")).thenReturn(1L);
        when(imagenMock.getOriginalFilename()).thenReturn("foto.jpg");
        when(imagenMock.getBytes()).thenThrow(new IOException());

        ModelAndView mv = controladorMascota.guardarMascota(mascota, imagenMock, sessionMock, redirectAttributesMock);

        assertEquals("formulario-dar-en-adopcion", mv.getViewName());
        assertEquals("Error al subir la imagen. Por favor, intente nuevamente.", mv.getModel().get("error"));
    }*/


}

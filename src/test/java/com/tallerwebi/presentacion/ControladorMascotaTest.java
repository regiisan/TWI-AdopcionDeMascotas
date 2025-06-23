package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.*;
import com.tallerwebi.dominio.servicios.ServicioMascota;
import com.tallerwebi.dominio.servicios.ServicioRecomendacion;
import com.tallerwebi.dominio.servicios.ServicioUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class ControladorMascotaTest
{
    private ControladorMascota controladorMascota;
    private ServicioMascota servicioMascotaMock;
    private ServicioRecomendacion servicioRecomendacionMock;
    private ServicioUsuario servicioUsuarioMock;
    private HttpSession sessionMock;
    private Usuario usuarioMock;

    @BeforeEach
    public void init(){
        servicioMascotaMock = mock(ServicioMascota.class);
        servicioRecomendacionMock = mock(ServicioRecomendacion.class);
        servicioUsuarioMock = mock(ServicioUsuario.class);
        controladorMascota = new ControladorMascota(servicioMascotaMock, servicioRecomendacionMock, servicioUsuarioMock);
        sessionMock = mock(HttpSession.class);
        usuarioMock = mock(Usuario.class);
    }


    @Test
    public void debeRetornarLaVistaMascotasCuandoSeEjecutaElMetodoMostrarMascotas(){
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
    public void debeMostrarMascotasRecomendadasSiElUsuarioEstaLogeadoYTienePreferencias(){
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
    public void debeOcultarLaSugerenciaDePreferenciasSiElUsuarioYaLasCargo(){
        Long idUsuario = 1L;
        when(sessionMock.getAttribute("idUsuario")).thenReturn(idUsuario);
        when(servicioUsuarioMock.buscarPorId(idUsuario)).thenReturn(usuarioMock);
        when(servicioUsuarioMock.tienePreferenciasCargadas(usuarioMock)).thenReturn(true);

        ModelAndView modelAndView = controladorMascota.mostrarMascotas(sessionMock);

        assertThat(modelAndView.getModel().get("mostrarSugerenciaDePreferencias"), is(false));
    }

    @Test
    public void debeMostrarLaSugerenciaDePreferenciasSiElUsuarioNoLasCargo(){
        Long idUsuario = 1L;
        when(sessionMock.getAttribute("idUsuario")).thenReturn(idUsuario);
        when(servicioUsuarioMock.buscarPorId(idUsuario)).thenReturn(usuarioMock);
        when(servicioUsuarioMock.tienePreferenciasCargadas(usuarioMock)).thenReturn(false);

        ModelAndView modelAndView = controladorMascota.mostrarMascotas(sessionMock);

        assertThat(modelAndView.getModel().get("mostrarSugerenciaDePreferencias"), is(true));
    }

    @Test
    public void debeFiltrarMascotasSegunParametrosYRetornarVista() {
        List<MascotaDto> mascotasFiltradas = Arrays.asList(mock(MascotaDto.class));
        when(servicioMascotaMock.obtenerMascotasFiltradas(Tipo.PERRO, Sexo.MACHO, Tamano.MEDIANO, NivelEnergia.ALTO))
                .thenReturn(mascotasFiltradas);

        ModelAndView modelAndView = controladorMascota.filtrarMascotas(Tipo.PERRO, Sexo.MACHO, Tamano.MEDIANO, NivelEnergia.ALTO, sessionMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("mascotas"));
        assertThat(modelAndView.getModel().get("mascotas"), is(mascotasFiltradas));
    }

}

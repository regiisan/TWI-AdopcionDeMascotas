package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.SolicitudAdopcion;
import com.tallerwebi.dominio.repositorios.RepositorioSolicitudAdoptar;
import com.tallerwebi.dominio.servicios.ServicioSolicitudAdoptar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class ServicioSolicitudAdoptarImplTest {

    private ServicioSolicitudAdoptar servicioSolicitudAdoptar;
    private RepositorioSolicitudAdoptar repositorioSolicitudAdoptarMock;

    @BeforeEach
    public void init(){
        repositorioSolicitudAdoptarMock = mock(RepositorioSolicitudAdoptar.class);
        servicioSolicitudAdoptar = new ServicioSolicitudAdoptarImpl(repositorioSolicitudAdoptarMock);
    }

    @Test
    public void alGuardarUnaSolicitudDebeGuardarlaEnElRepositorio() {
        // Preparación
        SolicitudAdopcion solicitud = new SolicitudAdopcion();

        // Ejecución
        servicioSolicitudAdoptar.guardar(solicitud);

        // Verificación
        verify(repositorioSolicitudAdoptarMock).guardar(solicitud);
    }

    @Test
    public void alBuscarPorIdDebeRetornarLaSolicitudCorrespondiente() {
        // Preparación
        Long id = 1L;
        SolicitudAdopcion solicitudEsperada = new SolicitudAdopcion();
        when(repositorioSolicitudAdoptarMock.buscarSolicitudPorId(id)).thenReturn(solicitudEsperada);

        // Ejecución
        SolicitudAdopcion resultado = servicioSolicitudAdoptar.buscarPorId(id);

        // Verificación
        assertThat(resultado, is(solicitudEsperada));
    }

    @Test
    public void alObtenerSolicitudesDebeRetornarTodasLasSolicitudes() {
        // Preparación
        List<SolicitudAdopcion> solicitudesEsperadas = Arrays.asList(new SolicitudAdopcion(), new SolicitudAdopcion());
        when(repositorioSolicitudAdoptarMock.listarSolicitudes()).thenReturn(solicitudesEsperadas);

        // Ejecución
        List<SolicitudAdopcion> resultado = servicioSolicitudAdoptar.obtenerSolicitudes();

        // Verificación
        assertThat(resultado, is(solicitudesEsperadas));
    }

    @Test
    public void alAprobarSolicitudDebeActualizarElEstadoAAprobada() {
        // Preparación
        Long id = 1L;
        SolicitudAdopcion solicitud = new SolicitudAdopcion();
        when(repositorioSolicitudAdoptarMock.buscarSolicitudPorId(id)).thenReturn(solicitud);

        // Ejecución
        servicioSolicitudAdoptar.aprobarSolicitud(id);

        // Verificación
        assertThat(solicitud.getEstado(), is("Aprobada"));
        verify(repositorioSolicitudAdoptarMock).modificar(solicitud);
    }

    @Test
    public void alRechazarSolicitudDebeActualizarElEstadoARechazada() {
        // Preparación
        Long id = 1L;
        SolicitudAdopcion solicitud = new SolicitudAdopcion();
        when(repositorioSolicitudAdoptarMock.buscarSolicitudPorId(id)).thenReturn(solicitud);

        // Ejecución
        servicioSolicitudAdoptar.rechazarSolicitud(id);

        // Verificación
        assertThat(solicitud.getEstado(), is("Rechazada"));
        verify(repositorioSolicitudAdoptarMock).modificar(solicitud);
    }

    @Test

    public void siLaSolicitudNoExisteAlAprobarNoDebeHacerNada() {
        // Preparación
        Long id = 1L;
        when(repositorioSolicitudAdoptarMock.buscarSolicitudPorId(id)).thenReturn(null);

        // Ejecución
        servicioSolicitudAdoptar.aprobarSolicitud(id);

        // Verificación
        verify(repositorioSolicitudAdoptarMock, never()).modificar(any());
    }

    @Test
    public void siLaSolicitudNoExisteAlRechazarNoDebeHacerNada() {
        // Preparación
        Long id = 1L;
        when(repositorioSolicitudAdoptarMock.buscarSolicitudPorId(id)).thenReturn(null);

        // Ejecución
        servicioSolicitudAdoptar.rechazarSolicitud(id);

        // Verificación
        verify(repositorioSolicitudAdoptarMock, never()).modificar(any());
    }

    @Test
    public void siNoHaySolicitudesDebeRetornarUnaListaVacia() {
        // Preparación
        when(repositorioSolicitudAdoptarMock.listarSolicitudes()).thenReturn(new ArrayList<>());

        // Ejecución
        List<SolicitudAdopcion> resultado = servicioSolicitudAdoptar.obtenerSolicitudes();

        // Verificación
        assertThat(resultado, empty());
    }

    public void dadoQueExistenSolicitudesConEstadoPendienteDebeDevolverSoloLasPendientes() {
        List<SolicitudAdopcion> solicitudesMock = Arrays.asList(mock(SolicitudAdopcion.class));
        when(repositorioSolicitudAdoptarMock.listarSolicitudesPorEstado("Pendiente")).thenReturn(solicitudesMock);

        List<SolicitudAdopcion> solicitudes = servicioSolicitudAdoptar.obtenerSolicitudesPorEstado("Pendiente");

        assertThat(solicitudes.size(), is(1));
        assertThat(solicitudes.get(0), instanceOf(SolicitudAdopcion.class));
    }

    @Test
    public void dadoQueNoExistenSolicitudesConEstadoPendienteDebeDevolverUnaListaVacia() {
        when(repositorioSolicitudAdoptarMock.listarSolicitudesPorEstado("Pendiente")).thenReturn(null);

        List<SolicitudAdopcion> solicitudes = servicioSolicitudAdoptar.obtenerSolicitudesPorEstado("Rechazada");

        assertThat(solicitudes, empty());
    }

}

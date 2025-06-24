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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioSolicitudAdoptarImplTest {

    private ServicioSolicitudAdoptar servicioSolicitudAdoptar;
    private RepositorioSolicitudAdoptar repositorioSolicitudAdoptarMock;

    @BeforeEach
    public void init(){
        repositorioSolicitudAdoptarMock = mock(RepositorioSolicitudAdoptar.class);
        servicioSolicitudAdoptar = new ServicioSolicitudAdoptarImpl(repositorioSolicitudAdoptarMock);
    }

    @Test
    public void dadoQueNoExistanSolicitudesDebeDevolverUnaListaVacia(){
        when(repositorioSolicitudAdoptarMock.listarSolicitudes()).thenReturn(new ArrayList<>());

        List<SolicitudAdopcion> solicitudes = servicioSolicitudAdoptar.obtenerSolicitudes();

        assertThat(solicitudes, empty());
    }

    @Test
    public void dadoQueSeCreenDosSolicitudesDebeDevolverUnaListaConDosSolicitudes(){
        List<SolicitudAdopcion> solicitudesMock = Arrays.asList(mock(SolicitudAdopcion.class), mock(SolicitudAdopcion.class));
        when(repositorioSolicitudAdoptarMock.listarSolicitudes()).thenReturn(solicitudesMock);

        List<SolicitudAdopcion> solicitudes = servicioSolicitudAdoptar.obtenerSolicitudes();

        assertThat(solicitudes.size(), is(2));
        assertThat(solicitudes.get(0), instanceOf(SolicitudAdopcion.class));
    }

}

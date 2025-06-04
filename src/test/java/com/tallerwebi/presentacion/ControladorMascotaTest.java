package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.Mascota;
import com.tallerwebi.dominio.servicios.ServicioMascota;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;


import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class ControladorMascotaTest
{
    private ControladorMascota controladorMascota;
    private ServicioMascota servicioMascotaMock;

    @BeforeEach
    public void init(){
        servicioMascotaMock = mock(ServicioMascota.class);
        controladorMascota = new ControladorMascota(servicioMascotaMock);
    }

    @Test
    public void debeRetornarLaVistaMascotasCuandoSeEjecutaElMetodoMostrarMascotas(){
        List<Mascota> mascotasMock = Arrays.asList(mock(Mascota.class), mock(Mascota.class));
        when(servicioMascotaMock.obtenerMascotas()).thenReturn(mascotasMock);

        ModelAndView modelAndView = controladorMascota.mostrarMascotas();

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("mascotas"));
    }

    @Test
    public void debeRetornarLaVistaHomeCuandoSeEjecutaElMetodoMostrarMascotasDestacadas() {
        List<Mascota> mascotasDestacadasMock = Arrays.asList(mock(Mascota.class), mock(Mascota.class));
        when(servicioMascotaMock.obtenerMascotasDestacadas()).thenReturn(mascotasDestacadasMock);

        ModelAndView modelAndView = controladorMascota.mostrarMascotasDestacadas();

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("home"));
    }
}

package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.ControladorMascota;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class ServicioMascotaImplTest {

    private ServicioMascota servicioMascota;
    private RepositorioMascota repositorioMascotaMock;

    @BeforeEach
    public void init(){
        repositorioMascotaMock = mock(RepositorioMascota.class);
        servicioMascota = new ServicioMascotaImpl(repositorioMascotaMock);
    }

    @Test
    public void dadoQueNoExistanMascotasDebeDevolverUnaListaVacia(){
        when(repositorioMascotaMock.listarMascotas()).thenReturn(new ArrayList<>());
        List<Mascota> mascotas = servicioMascota.obtenerMascotas();
        assertThat(mascotas, empty());
    }

    @Test
    public void dadoQueSeCreenDosMascotasDebeDevolverUnaListaConDosMascotas(){
        List<Mascota> mascotasMock = Arrays.asList(mock(Mascota.class), mock(Mascota.class));
        when(repositorioMascotaMock.listarMascotas()).thenReturn(mascotasMock);

        List<Mascota> mascotas = servicioMascota.obtenerMascotas();

        assertThat(mascotas.size(), is(2));
        assertThat(mascotas, is(mascotasMock));
    }

    @Test
    public void debeObtenerMascotasDestacadas() {
        List<Mascota> mascotasDestacadas = Arrays.asList(mock(Mascota.class), mock(Mascota.class));
        when(repositorioMascotaMock.listarMascotasDestacadas()).thenReturn(mascotasDestacadas);

        List<Mascota> resultado = servicioMascota.obtenerMascotasDestacadas();

        assertThat(mascotasDestacadas.size(), is(2));
        assertThat(resultado, is(mascotasDestacadas));
    }

}

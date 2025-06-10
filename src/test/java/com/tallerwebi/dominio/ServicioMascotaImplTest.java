package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Mascota;
import com.tallerwebi.dominio.entidades.MascotaDto;
import com.tallerwebi.dominio.repositorios.RepositorioMascota;
import com.tallerwebi.dominio.servicios.ServicioMascota;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
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

        List<MascotaDto> mascotas = servicioMascota.obtenerMascotas();

        assertThat(mascotas, empty());
    }

    @Test
    public void dadoQueSeCreenDosMascotasDebeDevolverUnaListaConDosMascotas(){
        List<Mascota> mascotasMock = Arrays.asList(mock(Mascota.class), mock(Mascota.class));
        when(repositorioMascotaMock.listarMascotas()).thenReturn(mascotasMock);

        List<MascotaDto> mascotas = servicioMascota.obtenerMascotas();

        assertThat(mascotas.size(), is(2));
        assertThat(mascotas.get(0), instanceOf(MascotaDto.class));
    }

    @Test
    public void debeObtenerMascotasDestacadas() {
        List<Mascota> mascotasDestacadasMock = Arrays.asList(mock(Mascota.class), mock(Mascota.class));
        when(repositorioMascotaMock.listarMascotasDestacadas()).thenReturn(mascotasDestacadasMock);

        List<MascotaDto> mascotas = servicioMascota.obtenerMascotasDestacadas();

        assertThat(mascotas.size(), is(2));
        assertThat(mascotas.get(0), instanceOf(MascotaDto.class));
        assertThat(mascotas.get(1), instanceOf(MascotaDto.class));
    }

}

package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.*;
import com.tallerwebi.dominio.repositorios.RepositorioMascota;
import com.tallerwebi.dominio.servicios.ServicioMascota;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        Mascota mascota1 = mock(Mascota.class);
        when(mascota1.getEstado()).thenReturn("Aprobada");

        Mascota mascota2 = mock(Mascota.class);
        when(mascota2.getEstado()).thenReturn("Aprobada");

        List<Mascota> mascotasMock = Arrays.asList(mascota1, mascota2);
        when(repositorioMascotaMock.listarMascotas()).thenReturn(mascotasMock);

        List<MascotaDto> mascotas = servicioMascota.obtenerMascotas();

        assertThat(mascotas.size(), is(2));
        assertThat(mascotas.get(0), instanceOf(MascotaDto.class));
    }

    @Test
    public void debeObtenerMascotasDestacadas() {
        Mascota mascota1 = mock(Mascota.class);
        when(mascota1.getEstado()).thenReturn("Aprobada");

        Mascota mascota2 = mock(Mascota.class);
        when(mascota2.getEstado()).thenReturn("Aprobada");

        List<Mascota> mascotasDestacadasMock = Arrays.asList(mascota1, mascota2);
        when(repositorioMascotaMock.listarMascotasDestacadas()).thenReturn(mascotasDestacadasMock);

        List<MascotaDto> mascotas = servicioMascota.obtenerMascotasDestacadas();

        assertThat(mascotas.size(), is(2));
        assertThat(mascotas.get(0), instanceOf(MascotaDto.class));
        assertThat(mascotas.get(1), instanceOf(MascotaDto.class));
    }

    @Test
    public void debeObtenerMascotasFiltradasComoDto() {
        Mascota mascota = new Mascota();
        mascota.setNombre("Luna");
        mascota.setTipo(Tipo.GATO);
        mascota.setSexo(Sexo.HEMBRA);
        mascota.setTamano(Tamano.CHICO);
        mascota.setNivelEnergia(NivelEnergia.BAJO);
        mascota.setEstado("Aprobada");

        when(repositorioMascotaMock.buscarPorFiltros(Tipo.GATO, Sexo.HEMBRA, Tamano.CHICO, NivelEnergia.BAJO))
                .thenReturn(List.of(mascota));

        List<MascotaDto> resultado = servicioMascota.obtenerMascotasFiltradas(Tipo.GATO, Sexo.HEMBRA, Tamano.CHICO, NivelEnergia.BAJO);

        assertEquals(1, resultado.size());
        assertEquals("Luna", resultado.get(0).getNombre());
    }

    @Test
    public void alGuardarMascotaComoAdminDebeQuedarAprobada() {
        // Preparación
        Mascota mascota = new Mascota();
        Usuario admin = new Usuario();
        admin.setRol("ADMIN");
        mascota.setUsuario(admin);

        // Ejecución
        servicioMascota.guardar(mascota);

        // Verificación
        assertThat(mascota.getEstado(), is("Aprobada"));
        verify(repositorioMascotaMock).guardar(mascota);
    }

    @Test
    public void alGuardarMascotaComoUsuarioNormalDebeQuedarPendiente() {
        // Preparación
        Mascota mascota = new Mascota();
        Usuario usuario = new Usuario();
        usuario.setRol("USUARIO");
        mascota.setUsuario(usuario);

        // Ejecución
        servicioMascota.guardar(mascota);

        // Verificación
        assertThat(mascota.getEstado(), is("Pendiente"));
        verify(repositorioMascotaMock).guardar(mascota);
    }

    @Test
    public void alObtenerMascotasDebeRetornarSoloLasAprobadas() {
        // Preparación
        Mascota mascotaAprobada = new Mascota();
        mascotaAprobada.setEstado("Aprobada");
        Mascota mascotaPendiente = new Mascota();
        mascotaPendiente.setEstado("Pendiente");
        List<Mascota> todasLasMascotas = Arrays.asList(mascotaAprobada, mascotaPendiente);
        when(repositorioMascotaMock.listarMascotas()).thenReturn(todasLasMascotas);

        // Ejecución
        List<MascotaDto> resultado = servicioMascota.obtenerMascotas();

        // Verificación
        assertThat(resultado, hasSize(1));
    }

    @Test
    public void alObtenerMascotasPorEstadoDebeRetornarSoloLasDelEstadoIndicado() {
        // Preparación
        Mascota mascotaAprobada = new Mascota();
        mascotaAprobada.setEstado("Aprobada");
        Mascota mascotaPendiente = new Mascota();
        mascotaPendiente.setEstado("Pendiente");
        List<Mascota> todasLasMascotas = Arrays.asList(mascotaAprobada, mascotaPendiente);
        when(repositorioMascotaMock.listarMascotas()).thenReturn(todasLasMascotas);

        // Ejecución
        List<MascotaDto> resultado = servicioMascota.obtenerMascotasPorEstado("Pendiente");

        // Verificación
        assertThat(resultado, hasSize(1));
    }

    @Test
    public void alAprobarMascotaDebeActualizarSuEstado() {
        // Preparación
        Long id = 1L;
        Mascota mascota = new Mascota();
        when(repositorioMascotaMock.buscarPorId(id)).thenReturn(mascota);

        // Ejecución
        servicioMascota.aprobarMascota(id);

        // Verificación
        assertThat(mascota.getEstado(), is("Aprobada"));
        verify(repositorioMascotaMock).modificar(mascota);
    }

    @Test
    public void alRechazarMascotaDebeActualizarSuEstado() {
        // Preparación
        Long id = 1L;
        Mascota mascota = new Mascota();
        when(repositorioMascotaMock.buscarPorId(id)).thenReturn(mascota);

        // Ejecución
        servicioMascota.rechazarMascota(id);

        // Verificación
        assertThat(mascota.getEstado(), is("Rechazada"));
        verify(repositorioMascotaMock).modificar(mascota);
    }

    @Test
    public void alBuscarMascotaPorIdDebeRetornarLaMascotaCorrecta() {
        // Preparación
        Long id = 1L;
        Mascota mascotaEsperada = new Mascota();
        when(repositorioMascotaMock.buscarPorId(id)).thenReturn(mascotaEsperada);

        // Ejecución
        Mascota resultado = servicioMascota.obtenerMascotaPorId(id);

        // Verificación
        assertThat(resultado, is(mascotaEsperada));
    }

    @Test
    public void alObtenerMascotasFiltradasDebeRetornarSoloLasAprobadas() {
        // Preparación
        Mascota mascotaAprobada = new Mascota();
        mascotaAprobada.setEstado("Aprobada");
        Mascota mascotaPendiente = new Mascota();
        mascotaPendiente.setEstado("Pendiente");
        List<Mascota> mascotas = Arrays.asList(mascotaAprobada, mascotaPendiente);
        when(repositorioMascotaMock.buscarPorFiltros(any(), any(), any(), any())).thenReturn(mascotas);

        // Ejecución
        List<MascotaDto> resultado = servicioMascota.obtenerMascotasFiltradas(null, null, null, null);

        // Verificación
        assertThat(resultado, hasSize(1));
    }

    @Test
    public void alObtenerMascotasDestacadasDebeRetornarSoloLasAprobadas() {
        // Preparación
        Mascota mascotaAprobada = new Mascota();
        mascotaAprobada.setEstado("Aprobada");
        Mascota mascotaPendiente = new Mascota();
        mascotaPendiente.setEstado("Pendiente");
        List<Mascota> mascotas = Arrays.asList(mascotaAprobada, mascotaPendiente);
        when(repositorioMascotaMock.listarMascotasDestacadas()).thenReturn(mascotas);

        // Ejecución
        List<MascotaDto> resultado = servicioMascota.obtenerMascotasDestacadas();

        // Verificación
        assertThat(resultado, hasSize(1));
    }
}

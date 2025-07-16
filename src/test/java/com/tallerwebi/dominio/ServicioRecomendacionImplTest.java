package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.*;
import com.tallerwebi.dominio.repositorios.RepositorioMascota;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioRecomendacionImplTest {

    private ServicioRecomendacionImpl servicioRecomendacion;
    private RepositorioMascota repositorioMascotaMock;
    private HttpSession sessionMock;
    private Usuario usuarioMock;

    @BeforeEach
    public void init(){
        repositorioMascotaMock = mock(RepositorioMascota.class);
        servicioRecomendacion = new ServicioRecomendacionImpl(repositorioMascotaMock);
        sessionMock = mock(HttpSession.class);
        usuarioMock = mock(Usuario.class);
    }

    private Mascota crearMascota() {
        Mascota mascota = new Mascota();
        mascota.setEstado("Aprobada");
        return mascota;
    }

    @Test
    public void dadoQueNoExistanMascotasDebeDevolverUnaListaVacia(){
        when(repositorioMascotaMock.listarMascotas()).thenReturn(new ArrayList<>());

        List<MascotaDto> mascotas = servicioRecomendacion.obtenerMascotasRecomendadas(usuarioMock);

        assertThat(mascotas, empty());
    }

    @Test
    public void dadoQueExistanMascotasYElUsuarioHayaCargadoPreferenciasDebeDevolverUnaListaConMascotasOrdenadasSegunDichasPreferencias(){
        Usuario usuario = new Usuario();
        usuario.setTipoPreferido(Tipo.GATO);
        usuario.setSexoPreferido(Sexo.HEMBRA);
        usuario.setEdadPreferida(5);
        usuario.setTamanoPreferido(Tamano.GRANDE);
        usuario.setNivelEnergiaPreferido(NivelEnergia.ALTO);

        Mascota mascota1 = crearMascota();
        mascota1.setTipo(Tipo.GATO);
        mascota1.setSexo(Sexo.HEMBRA);
        mascota1.setEdad(5);
        mascota1.setTamano(Tamano.GRANDE);
        mascota1.setNivelEnergia(NivelEnergia.MEDIO);
        mascota1.setAdoptado(false);

        Mascota mascota2 = crearMascota();
        mascota2.setTipo(Tipo.GATO);
        mascota2.setSexo(Sexo.MACHO);
        mascota2.setEdad(2);
        mascota2.setTamano(Tamano.MEDIANO);
        mascota2.setNivelEnergia(NivelEnergia.ALTO);
        mascota2.setAdoptado(false);

        List<Mascota> mascotas = Arrays.asList(mascota1, mascota2);
        when(repositorioMascotaMock.listarMascotas()).thenReturn(mascotas);

        List<MascotaDto> mascotasRecomendadas = servicioRecomendacion.obtenerMascotasRecomendadas(usuario);

        assertEquals(2, mascotasRecomendadas.size());
        assertEquals(6, mascotasRecomendadas.get(0).getCoincidencias()); // mascota1 debería estar primero
        assertEquals(4, mascotasRecomendadas.get(1).getCoincidencias());
    }

    @Test
    public void dadoQueNoHayaCoincidenciasDebeDevolverUnaListaConMascotasConCeroCoincidencias(){
        Usuario usuario = new Usuario();
        usuario.setTipoPreferido(Tipo.GATO);
        usuario.setSexoPreferido(Sexo.HEMBRA);
        usuario.setEdadPreferida(5);
        usuario.setTamanoPreferido(Tamano.GRANDE);
        usuario.setNivelEnergiaPreferido(NivelEnergia.ALTO);

        Mascota mascota = crearMascota();
        mascota.setTipo(Tipo.PERRO);
        mascota.setSexo(Sexo.MACHO);
        mascota.setEdad(1);
        mascota.setTamano(Tamano.CHICO);
        mascota.setNivelEnergia(NivelEnergia.MEDIO);
        mascota.setAdoptado(false);

        List<Mascota> mascotas = Arrays.asList(mascota);
        when(repositorioMascotaMock.listarMascotas()).thenReturn(mascotas);

        List<MascotaDto> mascotasRecomendadas = servicioRecomendacion.obtenerMascotasRecomendadas(usuario);

        assertEquals(1, mascotasRecomendadas.size());
        assertEquals(0, mascotasRecomendadas.get(0).getCoincidencias());
    }
}

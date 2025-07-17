package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.*;
import com.tallerwebi.dominio.repositorios.RepositorioMascota;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfraestructuraTestConfig.class})
@Transactional
@Rollback
public class RepositorioMascotaTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioMascota repositorioMascota;

    @BeforeEach
    public void init() {
        this.repositorioMascota = new RepositorioMascotaImpl(this.sessionFactory);
    }

    private Mascota crearMascota() {
        Mascota mascota = new Mascota();
        mascota.setEstado("Aprobada");
        return mascota;
    }

    @Test
    public void dadoQueExistaUnaMascotaEnLaBDCuandoLaObtengoPorIdDevuelveLaMascotaCorrespondiente(){
        Mascota mascota = crearMascota();
        mascota.setNombre("Luna");
        this.sessionFactory.getCurrentSession().save(mascota);

        String hql = "FROM Mascota m WHERE m.nombre = :nombre";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("nombre", "Luna");
        Mascota mascotaGuardada = (Mascota)query.getSingleResult();

        Mascota mascotaObtenida = this.repositorioMascota.buscarPorId(mascotaGuardada.getId());

        assertThat(mascotaObtenida, equalTo(mascotaGuardada));
    }

    @Test
    public void dadoQueNoExisteUnaMascotaEnLaBDConEseIdCuandoLaBuscoDevuelveNull() {
        Mascota mascotaObtenida = this.repositorioMascota.buscarPorId(9999L);

        assertNull(mascotaObtenida);
    }

    @Test
    public void dadoQueHayaMascotasEnLaBDListarMascotasDevuelveTodas() {
        Mascota mascota1 = crearMascota();
        Mascota mascota2 = crearMascota();
        this.sessionFactory.getCurrentSession().save(mascota1);
        this.sessionFactory.getCurrentSession().save(mascota2);

        List<Mascota> mascotas = this.repositorioMascota.listarMascotas();

        assertEquals(2, mascotas.size());
    }

    @Test
    public void dadoQueNoHayaMascotasEnLaBDListarMascotasDevuelveListaVacia() {
        List<Mascota> mascotas = this.repositorioMascota.listarMascotas();

        assertTrue(mascotas.isEmpty());
    }

    @Test
    public void dadoQueHayaMasDe4MascotasEnLaBDListarMascotasDestacadasDevuelveSolo4() {
        for (int i = 1; i <= 6; i++) {
            Mascota m = crearMascota();
            this.sessionFactory.getCurrentSession().save(m);
        }

        List<Mascota> mascotasDestacadas = this.repositorioMascota.listarMascotasDestacadas();

        assertEquals(4, mascotasDestacadas.size());
    }

    @Test
    public void dadoQueHayaMenosDe4MascotasEnLaBDListarMascotasDestacadasDevuelveTodas() {
        for (int i = 1; i <= 3; i++) {
            Mascota m = crearMascota();
            this.sessionFactory.getCurrentSession().save(m);
        }

        List<Mascota> mascotasDestacadas = this.repositorioMascota.listarMascotasDestacadas();

        assertEquals(3, mascotasDestacadas.size());
    }

    @Test
    public void debeFiltrarMascotasPorTipoSexoTamanoEnergia() {
        Mascota mascota = crearMascota();
        mascota.setNombre("Toby");
        mascota.setTipo(Tipo.PERRO);
        mascota.setSexo(Sexo.MACHO);
        mascota.setTamano(Tamano.MEDIANO);
        mascota.setNivelEnergia(NivelEnergia.MEDIO);
        repositorioMascota.guardar(mascota);

        List<Mascota> resultado = repositorioMascota
                .listarMascotasFiltradas(Tipo.PERRO.name(), Sexo.MACHO.name(), Tamano.MEDIANO.name(), NivelEnergia.MEDIO.name());

        assertThat(resultado.size(), is(1));
        assertThat(resultado.get(0).getNombre(), is("Toby"));
    }

    @Test
    public void dadoQueHayaMascotasPendientesEnLaBDContarMascotasPendientesDevuelveCuantasHay(){
        Mascota mascota1 = new Mascota();
        mascota1.setNombre("Luna");
        mascota1.setEstado("Pendiente");

        Mascota mascota2 = new Mascota();
        mascota2.setNombre("Simba");
        mascota2.setEstado("Pendiente");

        this.sessionFactory.getCurrentSession().save(mascota1);
        this.sessionFactory.getCurrentSession().save(mascota2);

        int mascotasPendientes = repositorioMascota.contarMascotasPendientes();

        assertEquals(2, mascotasPendientes);
    }

    @Test
    public void dadoQueHayaSolicitudesConMascotasConDiferentesEstadosEnLaBDContarMascotasPendientesDevuelveSoloCuantasPendientesHay(){
        Mascota mascota1 = new Mascota();
        mascota1.setNombre("Luna");
        mascota1.setEstado("Pendiente");

        Mascota mascota2 = new Mascota();
        mascota2.setNombre("Coco");
        mascota2.setEstado("Aprobada");

        this.sessionFactory.getCurrentSession().save(mascota1);
        this.sessionFactory.getCurrentSession().save(mascota2);

        int mascotasPendientes = repositorioMascota.contarMascotasPendientes();

        assertEquals(1, mascotasPendientes);
    }

    @Test
    public void dadoQueNoHayaMascotasPendientesDebeRetornarCero() {
        Mascota mascota = new Mascota();
        mascota.setNombre("Luna");
        mascota.setEstado("Aprobada");

        this.sessionFactory.getCurrentSession().save(mascota);

        int mascotasPendientes = repositorioMascota.contarMascotasPendientes();

        assertEquals(0, mascotasPendientes);
    }

}

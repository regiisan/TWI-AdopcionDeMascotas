package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Mascota;
import com.tallerwebi.dominio.repositorios.RepositorioMascota;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfraestructuraTestConfig.class})
@Transactional
public class RepositorioMascotaTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioMascota repositorioMascota;

    @BeforeEach
    public void init() {
        this.repositorioMascota = new RepositorioMascotaImpl(this.sessionFactory);
    }

    @Test
    @Rollback
    public void dadoQueExistaUnaMascotaEnLaBDCuandoLaObtengoPorIdDevuelveLaMascotaCorrespondiente(){
        Mascota mascota = new Mascota();
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
    @Rollback
    public void dadoQueNoExisteUnaMascotaEnLaBDConEseIdCuandoLaBuscoDevuelveNull() {
        Mascota mascotaObtenida = this.repositorioMascota.buscarPorId(9999L);

        assertNull(mascotaObtenida);
    }

    @Test
    @Rollback
    public void dadoQueHayaMascotasEnLaBDListarMascotasDevuelveTodas() {
        Mascota mascota1 = new Mascota();
        Mascota mascota2 = new Mascota();
        this.sessionFactory.getCurrentSession().save(mascota1);
        this.sessionFactory.getCurrentSession().save(mascota2);

        List<Mascota> mascotas = this.repositorioMascota.listarMascotas();

        assertEquals(2, mascotas.size());
    }

    @Test
    @Rollback
    public void dadoQueNoHayaMascotasEnLaBDListarMascotasDevuelveListaVacia() {
        List<Mascota> mascotas = this.repositorioMascota.listarMascotas();

        assertTrue(mascotas.isEmpty());
    }

    @Test
    @Rollback
    public void dadoQueHayaMasDe4MascotasEnLaBDListarMascotasDestacadasDevuelveSolo4() {
        for (int i = 1; i <= 6; i++) {
            Mascota m = new Mascota();
            this.sessionFactory.getCurrentSession().save(m);
        }

        List<Mascota> mascotasDestacadas = this.repositorioMascota.listarMascotasDestacadas();

        assertEquals(4, mascotasDestacadas.size());
    }

    @Test
    @Rollback
    public void dadoQueHayaMenosDe4MascotasEnLaBDListarMascotasDestacadasDevuelveTodas() {
        // Dado: creo 3 mascotas
        for (int i = 1; i <= 3; i++) {
            Mascota m = new Mascota();
            this.sessionFactory.getCurrentSession().save(m);
        }

        List<Mascota> mascotasDestacadas = this.repositorioMascota.listarMascotasDestacadas();

        assertEquals(3, mascotasDestacadas.size());
    }

}

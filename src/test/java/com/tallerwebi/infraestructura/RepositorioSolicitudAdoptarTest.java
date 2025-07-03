package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Mascota;
import com.tallerwebi.dominio.entidades.SolicitudAdopcion;
import com.tallerwebi.dominio.repositorios.RepositorioSolicitudAdoptar;
import com.tallerwebi.infraestructura.config.HibernateInfraestructuraTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfraestructuraTestConfig.class})
@Transactional
@Rollback
public class RepositorioSolicitudAdoptarTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioSolicitudAdoptar repositorioSolicitudAdoptar;

    @BeforeEach
    public void init() {
        this.repositorioSolicitudAdoptar = new RepositorioSolicitudAdoptarImpl(this.sessionFactory);
    }

    @Test
    public void dadoQueHayaSolicitudesEnLaBDListarSolicitudesDevuelveTodas(){
        Mascota mascota = new Mascota();
        mascota.setNombre("Luna");

        SolicitudAdopcion solicitud = new SolicitudAdopcion();
        solicitud.setMascota(mascota);

        this.sessionFactory.getCurrentSession().save(mascota);
        this.sessionFactory.getCurrentSession().save(solicitud);

        List<SolicitudAdopcion> solicitudes = repositorioSolicitudAdoptar.listarSolicitudes();

        assertEquals(1, solicitudes.size());
        assertNotNull(solicitudes.get(0).getMascota());
        assertFalse(solicitudes.isEmpty());
    }

    @Test
    public void dadoQueNoHayaSolicitudesEnLaBDDevuelveListaVacia() {
        List<SolicitudAdopcion> solicitudes = repositorioSolicitudAdoptar.listarSolicitudes();

        assertTrue(solicitudes.isEmpty());
    }

    @Test
    public void dadoQueLaSolicitudTieneMascotaListarSolicitudesDevuelveLaMascotaAsociada() {
        Mascota mascota = new Mascota();
        mascota.setNombre("Luna");

        SolicitudAdopcion solicitud = new SolicitudAdopcion();
        solicitud.setMascota(mascota);
        solicitud.setEstado("Pendiente");

        this.sessionFactory.getCurrentSession().save(mascota);
        this.sessionFactory.getCurrentSession().save(solicitud);

        List<SolicitudAdopcion> solicitudes = repositorioSolicitudAdoptar.listarSolicitudes();

        assertNotNull(solicitudes.get(0).getMascota());
        assertEquals("Luna", solicitudes.get(0).getMascota().getNombre());
    }

    @Test
    public void dadoQueHayaSolicitudesEnLaBDConEstadoPendienteListarSolicitudesPorEstadoDevuelveSoloLasPendientes(){
        Mascota mascota = new Mascota();
        mascota.setNombre("Luna");

        SolicitudAdopcion solicitudPendiente = new SolicitudAdopcion();
        solicitudPendiente.setEstado("Pendiente");
        solicitudPendiente.setMascota(mascota);

        SolicitudAdopcion solicitudAprobada = new SolicitudAdopcion();
        solicitudAprobada.setEstado("Aprobada");
        solicitudAprobada.setMascota(mascota);

        this.sessionFactory.getCurrentSession().save(mascota);
        this.sessionFactory.getCurrentSession().save(solicitudPendiente);
        this.sessionFactory.getCurrentSession().save(solicitudAprobada);

        List<SolicitudAdopcion> solicitudesPendientes = repositorioSolicitudAdoptar.listarSolicitudesPorEstado("Pendiente");

        assertEquals(1, solicitudesPendientes.size());
        assertEquals("Pendiente", solicitudesPendientes.get(0).getEstado());
    }

    @Test
    public void dadoQueNoHayaSolicitudesEnLaBDConEstadoPendienteListarSolicitudesPorEstadoDevuelveUnaListaVacia() {
        Mascota mascota = new Mascota();
        mascota.setNombre("Luna");

        SolicitudAdopcion solicitud = new SolicitudAdopcion();
        solicitud.setEstado("Aprobada");
        solicitud.setMascota(mascota);

        this.sessionFactory.getCurrentSession().save(mascota);
        this.sessionFactory.getCurrentSession().save(solicitud);

        List<SolicitudAdopcion> solicitudes = repositorioSolicitudAdoptar.listarSolicitudesPorEstado("Pendiente");

        assertTrue(solicitudes.isEmpty());
    }

    @Test
    public void dadoQueHayaSolicitudesConEstadoNullListarSolicitudesPorEstadoDevuelveListaVacia() {
        List<SolicitudAdopcion> resultado = repositorioSolicitudAdoptar.listarSolicitudesPorEstado(null);
        assertTrue(resultado.isEmpty());
    }

    @Test
    public void dadoQueHayaSolicitudesPendientesEnLaBDContarSolicitudesPendientesDevuelveCuantasHay(){
        Mascota mascota = new Mascota();
        mascota.setNombre("Luna");

        SolicitudAdopcion solicitudPendiente1 = new SolicitudAdopcion();
        solicitudPendiente1.setEstado("Pendiente");
        solicitudPendiente1.setMascota(mascota);

        SolicitudAdopcion solicitudPendiente2 = new SolicitudAdopcion();
        solicitudPendiente2.setEstado("Pendiente");
        solicitudPendiente2.setMascota(mascota);

        this.sessionFactory.getCurrentSession().save(mascota);
        this.sessionFactory.getCurrentSession().save(solicitudPendiente1);
        this.sessionFactory.getCurrentSession().save(solicitudPendiente2);

        int solicitudesPendientes = repositorioSolicitudAdoptar.contarSolicitudesPendientes();

        assertEquals(2, solicitudesPendientes);
    }

    @Test
    public void dadoQueHayaSolicitudesConDiferentesEstadosEnLaBDContarSolicitudesPendientesDevuelveSoloCuantasPendientesHay(){
        Mascota mascota = new Mascota();
        mascota.setNombre("Luna");

        SolicitudAdopcion solicitudPendiente = new SolicitudAdopcion();
        solicitudPendiente.setEstado("Pendiente");
        solicitudPendiente.setMascota(mascota);

        SolicitudAdopcion solicitudAprobada = new SolicitudAdopcion();
        solicitudAprobada.setEstado("Aprobada");
        solicitudAprobada.setMascota(mascota);

        this.sessionFactory.getCurrentSession().save(mascota);
        this.sessionFactory.getCurrentSession().save(solicitudPendiente);
        this.sessionFactory.getCurrentSession().save(solicitudAprobada);

        int solicitudesPendientes = repositorioSolicitudAdoptar.contarSolicitudesPendientes();

        assertEquals(1, solicitudesPendientes);
    }

    @Test
    public void dadoQueNoHayaSolicitudesPendientesDebeRetornarCero() {
        Mascota mascota = new Mascota();
        mascota.setNombre("Luna");

        SolicitudAdopcion solicitud = new SolicitudAdopcion();
        solicitud.setEstado("Aprobada");
        solicitud.setMascota(mascota);

        this.sessionFactory.getCurrentSession().save(mascota);
        this.sessionFactory.getCurrentSession().save(solicitud);

        int solicitudesPendientes = repositorioSolicitudAdoptar.contarSolicitudesPendientes();

        assertEquals(0, solicitudesPendientes);
    }

    @Test
    public void dadoQueHayaSolicitudesAprobadasEnLaBDContarSolicitudesAprobadasDevuelveCuantasHay(){
        Mascota mascota = new Mascota();
        mascota.setNombre("Luna");

        SolicitudAdopcion solicitudAprobada1 = new SolicitudAdopcion();
        solicitudAprobada1.setEstado("Aprobada");
        solicitudAprobada1.setMascota(mascota);

        SolicitudAdopcion solicitudAprobada2 = new SolicitudAdopcion();
        solicitudAprobada2.setEstado("Aprobada");
        solicitudAprobada2.setMascota(mascota);

        this.sessionFactory.getCurrentSession().save(mascota);
        this.sessionFactory.getCurrentSession().save(solicitudAprobada1);
        this.sessionFactory.getCurrentSession().save(solicitudAprobada2);

        int solicitudesAprobadas = repositorioSolicitudAdoptar.contarSolicitudesAprobadas();

        assertEquals(2, solicitudesAprobadas);
    }

    @Test
    public void dadoQueHayaSolicitudesConDiferentesEstadosEnLaBDContarSolicitudesAprobadasDevuelveSoloCuantasAprobadasHay(){
        Mascota mascota = new Mascota();
        mascota.setNombre("Luna");

        SolicitudAdopcion solicitudPendiente = new SolicitudAdopcion();
        solicitudPendiente.setEstado("Pendiente");
        solicitudPendiente.setMascota(mascota);

        SolicitudAdopcion solicitudAprobada = new SolicitudAdopcion();
        solicitudAprobada.setEstado("Aprobada");
        solicitudAprobada.setMascota(mascota);

        this.sessionFactory.getCurrentSession().save(mascota);
        this.sessionFactory.getCurrentSession().save(solicitudPendiente);
        this.sessionFactory.getCurrentSession().save(solicitudAprobada);

        int solicitudesAprobadas = repositorioSolicitudAdoptar.contarSolicitudesAprobadas();

        assertEquals(1, solicitudesAprobadas);
    }

    @Test
    public void dadoQueNoHayaSolicitudesAprobadasDebeRetornarCero() {
        Mascota mascota = new Mascota();
        mascota.setNombre("Luna");

        SolicitudAdopcion solicitud = new SolicitudAdopcion();
        solicitud.setEstado("Pendiente");
        solicitud.setMascota(mascota);

        this.sessionFactory.getCurrentSession().save(mascota);
        this.sessionFactory.getCurrentSession().save(solicitud);

        int solicitudesAprobadas = repositorioSolicitudAdoptar.contarSolicitudesAprobadas();

        assertEquals(0, solicitudesAprobadas);
    }

}

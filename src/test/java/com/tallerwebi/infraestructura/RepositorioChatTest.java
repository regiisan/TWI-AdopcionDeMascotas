package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Mensaje;
import com.tallerwebi.dominio.repositorios.RepositorioChat;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfraestructuraTestConfig.class})
@Transactional
@Rollback
public class RepositorioChatTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioChat repositorioChat;

    @BeforeEach
    public void init() {
        this.repositorioChat = new RepositorioChatImpl(this.sessionFactory);
    }


    @Test
    public void dadoQueSeGuardeUnMensajeDeberiaPersistirLaEntidad() {
        Mensaje mensaje = new Mensaje();
        mensaje.setMessage("Hola");

        repositorioChat.guardarMensaje(mensaje);

        assertNotNull(mensaje.getId());
        Mensaje buscado = sessionFactory.getCurrentSession().get(Mensaje.class, mensaje.getId());
        assertNotNull(buscado);
        assertEquals("Hola", buscado.getMessage());
    }

    @Test
    public void dadoQueExistanMensajesCuandoLosObtengoDevuelveListaOrdenadaPorFecha() {
        Mensaje mensaje1 = new Mensaje();
        mensaje1.setMessage("Hola");
        mensaje1.setFecha(LocalDateTime.of(2025,7,12,10,0));
        repositorioChat.guardarMensaje(mensaje1);

        Mensaje mensaje2 = new Mensaje();
        mensaje2.setMessage("Como estás?");
        mensaje2.setFecha(LocalDateTime.of(2025,7,12,12,0));
        repositorioChat.guardarMensaje(mensaje2);

        List<Mensaje> mensajes = repositorioChat.obtenerMensajes();

        assertThat(mensajes.size(), is(2));
        assertThat(mensajes.get(0).getMessage(), is("Hola"));
        assertThat(mensajes.get(1).getMessage(), is("Como estás?"));
    }
}

package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Usuario;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateInfraestructuraTestConfig.class})
@Transactional
@Rollback
public class RepositorioUsuarioTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioUsuarioImpl repositorioUsuario;

    @BeforeEach
    public void setUp() {
        repositorioUsuario = new RepositorioUsuarioImpl(sessionFactory);
    }

    @Test
    public void deberiaGuardarYBuscarUsuarioPorEmail() {
        Usuario usuario = new Usuario();
        usuario.setEmail("prueba@correo.com");
        usuario.setPassword("1234");

        repositorioUsuario.guardar(usuario);

        Usuario resultado = repositorioUsuario.buscarUsuario("prueba@correo.com");

        assertThat(resultado, is(notNullValue()));
        assertThat(resultado.getEmail(), is("prueba@correo.com"));
    }

    @Test
    public void deberiaModificarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setEmail("modificar@correo.com");
        usuario.setPassword("password");

        repositorioUsuario.guardar(usuario);

        usuario.setPassword("nuevaPassword");
        repositorioUsuario.modificar(usuario);

        Usuario actualizado = repositorioUsuario.buscarUsuario("modificar@correo.com");

        assertThat(actualizado.getPassword(), is("nuevaPassword"));
    }

    @Test
    public void dadoQueHayaUsuariosActivosEnLaBDContarUsuariosActivosDevuelveCuantosHay(){
        Usuario usuario = new Usuario();
        usuario.setActivo(true);
        this.sessionFactory.getCurrentSession().save(usuario);

        int cantidad = repositorioUsuario.contarUsuariosActivos();

        assertEquals(1, cantidad);
    }

    @Test
    public void dadoQueHayaUsuariosInactivosEnLaBDContarUsuariosActivosDevuelveSoloCuantosActivosHay(){
        Usuario usuario1 = new Usuario();
        usuario1.setActivo(true);
        Usuario usuario2 = new Usuario();
        usuario2.setActivo(false);
        this.sessionFactory.getCurrentSession().save(usuario1);
        this.sessionFactory.getCurrentSession().save(usuario2);

        int cantidad = repositorioUsuario.contarUsuariosActivos();

        assertEquals(1, cantidad);
    }

    @Test
    public void dadoQueNoHayaUsuariosActivosDebeRetornarCero() {
        Usuario usuario = new Usuario();
        usuario.setActivo(false);
        this.sessionFactory.getCurrentSession().save(usuario);

        int cantidad = repositorioUsuario.contarUsuariosActivos();

        assertEquals(0, cantidad);
    }

    @Test
    public void dadoQueExistaUnaUsuarioEnLaBDCuandoLoObtengoPorIdDevuelveElUsuarioCorrespondiente(){
        Usuario usuario = new Usuario();
        usuario.setNombre("Germán");
        this.sessionFactory.getCurrentSession().save(usuario);

        String hql = "FROM Usuario u WHERE u.nombre = :nombre";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("nombre", "Germán");
        Usuario usuarioGuardado = (Usuario)query.getSingleResult();

        Usuario usuarioObtenido = this.repositorioUsuario.buscarPorId(usuarioGuardado.getId());

        assertThat(usuarioObtenido, equalTo(usuarioGuardado));
    }

    @Test
    public void dadoQueNoExisteUnUsuarioEnLaBDConEseIdCuandoLoBuscoDevuelveNull() {
        Usuario usuarioObtenido = this.repositorioUsuario.buscarPorId(9999L);

        assertNull(usuarioObtenido);
    }










}

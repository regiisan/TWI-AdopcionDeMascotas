package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.*;
import com.tallerwebi.dominio.servicios.ServicioUsuario;
import com.tallerwebi.util.UsuarioTestDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class ControladorUsuarioTest {

    private ControladorUsuario controladorUsuario;
    private ServicioUsuario servicioUsuarioMock;
    private UsuarioDto usuario;
    private HttpSession sessionMock;

    @BeforeEach
    public void init(){
        servicioUsuarioMock = mock(ServicioUsuario.class);
        usuario = UsuarioTestDataUtil.crearUsuarioDtoTest(1L);
        sessionMock = mock(HttpSession.class);
        controladorUsuario = new ControladorUsuario(servicioUsuarioMock);
    }



    @Test
    public void debeRetornarFormularioPreferenciasSiElUsuarioEstaLogeado(){
        Long idUsuario = 1L;
        when(sessionMock.getAttribute("idUsuario")).thenReturn(idUsuario);
        when(servicioUsuarioMock.buscarPorId(idUsuario)).thenReturn(usuario.obtenerEntidad());

        ModelAndView modelAndView = controladorUsuario.mostrarFormularioPreferencias(sessionMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("formulario-PreferenciasDelUsuario"));
        assertThat(modelAndView.getModel().get("usuario"), is(usuario));
        assertThat(modelAndView.getModel().get("tipos"), is(Tipo.values()));
        assertThat(modelAndView.getModel().get("sexos"), is(Sexo.values()));
        assertThat(modelAndView.getModel().get("tamanos"), is(Tamano.values()));
        assertThat(modelAndView.getModel().get("energias"), is(NivelEnergia.values()));
    }

    @Test
    public void debeRedirigirAlLoginSiElUsuarioNoEstaLogeadoYQuiereAccederAlFormularioDePreferencias(){
        when(sessionMock.getAttribute("idUsuario")).thenReturn(null);

        ModelAndView modelAndView = controladorUsuario.mostrarFormularioPreferencias(sessionMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
    }

    @Test
    public void debeGuardarPreferenciasYRetornarLaVistaMascotasSiElUsuarioEstaLogeado(){
        Long idUsuario = 1L;
        UsuarioDto usuarioConPreferencias = new UsuarioDto();
        usuarioConPreferencias.setTipoPreferido(Tipo.PERRO);
        usuarioConPreferencias.setSexoPreferido(Sexo.HEMBRA);
        usuarioConPreferencias.setEdadPreferida(3);
        usuarioConPreferencias.setTamanoPreferido(Tamano.CHICO);
        usuarioConPreferencias.setNivelEnergiaPreferido(NivelEnergia.ALTO);

        Usuario usuario = new Usuario();
        when(sessionMock.getAttribute("idUsuario")).thenReturn(idUsuario);
        when(servicioUsuarioMock.buscarPorId(idUsuario)).thenReturn(usuario);

        ModelAndView modelAndView = controladorUsuario.guardarPreferencias(usuarioConPreferencias, sessionMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/mascotas"));
        assertThat(usuario.getTipoPreferido(), is(Tipo.PERRO));
        assertThat(usuario.getSexoPreferido(), is(Sexo.HEMBRA));
        assertThat(usuario.getEdadPreferida(), is(3));
        assertThat(usuario.getTamanoPreferido(), is(Tamano.CHICO));
        assertThat(usuario.getNivelEnergiaPreferido(), is(NivelEnergia.ALTO));
        verify(servicioUsuarioMock).modificar(usuario);
    }

    @Test
    public void debeRedirigirAlLoginSiElUsuarioNoEstaLogeadoAlGuardarLasPreferencias(){
        when(sessionMock.getAttribute("idUsuario")).thenReturn(null);

        ModelAndView modelAndView = controladorUsuario.guardarPreferencias(usuario, sessionMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
    }

    @Test
    public void debeMostrarPerfilSiElUsuarioEstaLogeado() {
        long idUsuario = 2L;
        when(sessionMock.getAttribute("idUsuario")).thenReturn(idUsuario);
        when(servicioUsuarioMock.buscarPorId(idUsuario)).thenReturn(usuario.obtenerEntidad());

        ModelAndView modelAndView = controladorUsuario.mostrarPerfil(sessionMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("perfil"));
        assertThat(modelAndView.getModel().get("usuario"), is(usuario));
    }

    @Test
    public void debeRedirigirAlLoginSiElUsuarioNoEstaLogeadoAlVerPerfil() {
        when(sessionMock.getAttribute("idUsuario")).thenReturn(null);

        ModelAndView modelAndView = controladorUsuario.mostrarPerfil(sessionMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
    }

    @Test
    public void debeEditarPerfilSiElUsuarioEstaLogeado() {
        Long idUsuario = 3L;
        UsuarioDto formUsuario = new UsuarioDto();
        formUsuario.setEdadPreferida(5);
        formUsuario.setTipoPreferido(Tipo.GATO);
        formUsuario.setTamanoPreferido(Tamano.MEDIANO);

        Usuario usuarioExistente = new Usuario();
        when(sessionMock.getAttribute("idUsuario")).thenReturn(idUsuario);
        when(servicioUsuarioMock.buscarPorId(idUsuario)).thenReturn(usuarioExistente);

        ModelAndView modelAndView = controladorUsuario.editarPerfil(formUsuario, sessionMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/perfil"));
        assertThat(usuarioExistente.getEdadPreferida(), is(5));
        assertThat(usuarioExistente.getTipoPreferido(), is(Tipo.GATO));
        assertThat(usuarioExistente.getTamanoPreferido(), is(Tamano.MEDIANO));
        verify(servicioUsuarioMock).modificar(usuarioExistente);
    }

    @Test
    public void debeRedirigirAlLoginSiElUsuarioNoEstaLogeadoAlEditarPerfil() {
        when(sessionMock.getAttribute("idUsuario")).thenReturn(null);

        ModelAndView modelAndView = controladorUsuario.editarPerfil(new UsuarioDto(), sessionMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
    }

}

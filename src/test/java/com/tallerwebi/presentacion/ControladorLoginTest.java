package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.servicios.ServicioLogin;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.util.UsuarioTestDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;

public class ControladorLoginTest {

	private ControladorLogin controladorLogin;
	private DatosRegistro usuario;
	private DatosLogin datosLoginMock;
	private HttpServletRequest requestMock;
	private HttpSession sessionMock;
	private ServicioLogin servicioLoginMock;


	@BeforeEach
	public void init(){
		datosLoginMock = new DatosLogin("dami@unlam.com", "123");
		usuario = UsuarioTestDataUtil.crearRegistroDtoTest(1L);
		requestMock = mock(HttpServletRequest.class);
		sessionMock = mock(HttpSession.class);
		servicioLoginMock = mock(ServicioLogin.class);
		controladorLogin = new ControladorLogin(servicioLoginMock);
	}

	@Test
	public void loginConUsuarioYPasswordInorrectosDeberiaLlevarALoginNuevamente(){
		when(servicioLoginMock.consultarUsuario(anyString(), anyString())).thenReturn(null);

		ModelAndView modelAndView = controladorLogin.validarLogin(datosLoginMock, requestMock);

		assertThat(modelAndView.getViewName(), equalToIgnoringCase("login"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Usuario o clave incorrecta"));
		verify(sessionMock, times(0)).setAttribute("ROL", "ADMIN");
	}

	@Test
	public void loginConUsuarioYPasswordCorrectosDeberiaLLevarAHomeAdminSiEsAdmin(){
		Usuario usuarioEncontradoMock = mock(Usuario.class);
		when(usuarioEncontradoMock.getRol()).thenReturn("ADMIN");

		when(requestMock.getSession()).thenReturn(sessionMock);
		when(servicioLoginMock.consultarUsuario(anyString(), anyString())).thenReturn(usuarioEncontradoMock);

		ModelAndView modelAndView = controladorLogin.validarLogin(datosLoginMock, requestMock);

		assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/admin/home"));
		verify(sessionMock, times(1)).setAttribute("ROL", usuarioEncontradoMock.getRol());
	}

	@Test
	public void loginConUsuarioYPasswordCorrectosDeberiaLLevarAHomeSiNoEsAdmin(){
		Usuario usuarioEncontradoMock = mock(Usuario.class);
		when(usuarioEncontradoMock.getRol()).thenReturn("USUARIO");
		when(requestMock.getSession()).thenReturn(sessionMock);
		when(servicioLoginMock.consultarUsuario(anyString(), anyString())).thenReturn(usuarioEncontradoMock);

		ModelAndView modelAndView = controladorLogin.validarLogin(datosLoginMock, requestMock);

		assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/home"));
		verify(sessionMock, times(1)).setAttribute("ROL", usuarioEncontradoMock.getRol());
	}


	@Test
	public void registrameSiUsuarioNoExisteDeberiaCrearUsuarioYVolverAlLogin() throws UsuarioExistente {
		ModelAndView modelAndView = controladorLogin.registrarme(usuario);

		assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
		verify(servicioLoginMock, times(1)).registrar(any(Usuario.class));
	}

	@Test
	public void registrarmeSiUsuarioExisteDeberiaVolverAFormularioYMostrarError() throws UsuarioExistente {
		doThrow(UsuarioExistente.class).when(servicioLoginMock).registrar(any(Usuario.class));

		ModelAndView modelAndView = controladorLogin.registrarme(usuario);

		assertThat(modelAndView.getViewName(), equalToIgnoringCase("nuevo-usuario"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("El usuario ya existe"));
	}

	@Test
	public void errorEnRegistrarmeDeberiaVolverAFormularioYMostrarError() throws UsuarioExistente {
		doThrow(RuntimeException.class).when(servicioLoginMock).registrar(any(Usuario.class));

		ModelAndView modelAndView = controladorLogin.registrarme(usuario);

		assertThat(modelAndView.getViewName(), equalToIgnoringCase("nuevo-usuario"));
		assertThat(modelAndView.getModel().get("error").toString(), equalToIgnoringCase("Error al registrar el nuevo usuario"));
	}

	@Test
	public void debeMostrarVistaNuevoUsuarioConFormularioDeRegistroVacioCuandoSeEjecutaElMetodoNuevoUsuario() {
		ModelAndView modelAndView = controladorLogin.nuevoUsuario();

		assertThat(modelAndView.getViewName(), equalToIgnoringCase("nuevo-usuario"));
		assertThat(modelAndView.getModel().get("registro"), is(instanceOf(DatosRegistro.class)));
	}

	@Test
	public void debeCerrarSesion() {
		HttpServletRequest requestMock = mock(HttpServletRequest.class);
		HttpSession sessionMock = mock(HttpSession.class);
		when(requestMock.getSession()).thenReturn(sessionMock);

		ModelAndView modelAndView = controladorLogin.cerrarSesion(requestMock);

		verify(sessionMock, times(1)).invalidate();
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
	}
}

package com.tallerwebi.punta_a_punta;

import com.microsoft.playwright.*;
import com.tallerwebi.punta_a_punta.vistas.VistaLogin;
import com.tallerwebi.punta_a_punta.vistas.VistaNuevoUsuario;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;

public class VistaLoginE2E {

    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    VistaLogin vistaLogin;

    @BeforeAll
    static void abrirNavegador() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
        //browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(50));
    }

    @AfterAll
    static void cerrarNavegador() {
        playwright.close();
    }

    @BeforeEach
    void crearContextoYPagina() {
        ReiniciarDB.limpiarBaseDeDatos();

        context = browser.newContext();
        Page page = context.newPage();
        vistaLogin = new VistaLogin(page);
    }

    @AfterEach
    void cerrarContexto() {
        context.close();
    }

    @Test
    void deberiaDarUnErrorAlIntentarIniciarSesionConUnUsuarioQueNoExiste() {
        dadoQueElUsuarioCargaSusDatosDeLoginCon("damian@unlam.edu.ar", "unlam");
        cuandoElUsuarioTocaElBotonDeLogin();
        entoncesDeberiaVerUnMensajeDeError();
    }

    @Test
    void deberiaNavegarAlHomeSiElUsuarioExiste() throws MalformedURLException {
        dadoQueElUsuarioCargaSusDatosDeLoginCon("regi@gmail.com", "test");
        cuandoElUsuarioTocaElBotonDeLogin();
        entoncesDeberiaSerRedirigidoALaVistaDeHome();
    }

    @Test
    void deberiaRegistrarUnUsuarioEIniciarSesionExistosamente() throws MalformedURLException {
        dadoQueElUsuarioNavegaALaVistaDeRegistro();
        dadoQueElUsuarioSeRegistraCon("Juan","juan@unlam.edu.ar", "123456");
        dadoQueElUsuarioEstaEnLaVistaDeLogin();
        dadoQueElUsuarioCargaSusDatosDeLoginCon("juan@unlam.edu.ar", "123456");
        cuandoElUsuarioTocaElBotonDeLogin();
        entoncesDeberiaSerRedirigidoALaVistaDeHome();
    }

    private void entoncesDeberiaVerADOPETSEnElNavbar() {
        String texto = vistaLogin.obtenerTextoDeLaBarraDeNavegacion();
        assertThat("ADOPETS", equalToIgnoringCase(texto));
    }

    private void dadoQueElUsuarioEstaEnLaVistaDeLogin() throws MalformedURLException {
        URL urlLogin = vistaLogin.obtenerURLActual();
        assertThat(urlLogin.getPath(), matchesPattern("^/tallerwebi-base-1.0-SNAPSHOT/login(?:;jsessionid=[^/\\s]+)?$"));
    }

    private void cuandoElUsuarioTocaElBotonDeLogin() {
        vistaLogin.darClickEnIniciarSesion();
    }

    private void entoncesDeberiaSerRedirigidoALaVistaDeHome() throws MalformedURLException {
        URL url = vistaLogin.obtenerURLActual();
        assertThat(url.getPath(), matchesPattern("^/tallerwebi-base-1.0-SNAPSHOT/home(?:;jsessionid=[^/\\s]+)?$"));
    }

    private void entoncesDeberiaVerUnMensajeDeError() {
        String texto = vistaLogin.obtenerMensajeDeError();
        assertThat("Error Usuario o clave incorrecta", equalToIgnoringCase(texto));
    }

    private void dadoQueElUsuarioCargaSusDatosDeLoginCon(String email, String clave) {
        vistaLogin.escribirEMAIL(email);
        vistaLogin.escribirClave(clave);
    }

    private void dadoQueElUsuarioNavegaALaVistaDeRegistro() {
        vistaLogin.darClickEnRegistrarse();
    }

    private void dadoQueElUsuarioSeRegistraCon(String nombre, String email, String clave) {
        VistaNuevoUsuario vistaNuevoUsuario = new VistaNuevoUsuario(context.pages().get(0));
        vistaNuevoUsuario.escribirNOMBRE(nombre);
        vistaNuevoUsuario.escribirEMAIL(email);
        vistaNuevoUsuario.escribirClave(clave);
        vistaNuevoUsuario.darClickEnRegistrarme();
    }
}
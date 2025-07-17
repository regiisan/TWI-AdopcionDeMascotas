package com.tallerwebi.punta_a_punta;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.tallerwebi.punta_a_punta.vistas.VistaLogin;
import com.tallerwebi.punta_a_punta.vistas.VistaMascotas;
import org.junit.jupiter.api.*;

import java.net.MalformedURLException;
import java.net.URL;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;

public class VistaMascotasE2E {

    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    VistaLogin vistaLogin;
    VistaMascotas vistaMascotas;

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
    void deberiaNavegarALaVistaMascotas() throws MalformedURLException{
        dadoQueElUsuarioIniciaSesion("regi@gmail.com", "test");
        vistaMascotas = new VistaMascotas(vistaLogin.getPage());
        dadoQueElUsuarioNavegaALaVistaMascotasEnAdopcion();
        entoncesDeberiaSerRedirigidoALaVistaDeMascotasEnAdopcion();
    }

    @Test
    void deberiaPoderAbrirModalConInformacionDeMascota() throws MalformedURLException{
        dadoQueElUsuarioIniciaSesion("regi@gmail.com", "test");
        vistaMascotas = new VistaMascotas(vistaLogin.getPage());
        dadoQueElUsuarioNavegaALaVistaMascotasEnAdopcion();
        cuandoElUsuarioAbreElModalDeMascota();
        entoncesDeberiaVerElModalConDetallesDeLaMascota();
    }

    @Test
    void deberiaPoderEnviarUnaSolicitudDeAdopcionAUnaMascota(){
        dadoQueElUsuarioIniciaSesion("regi@gmail.com", "test");
        vistaMascotas = new VistaMascotas(vistaLogin.getPage());
        dadoQueElUsuarioNavegaALaVistaMascotasEnAdopcion();
        dadoQueElUsuarioAbraElModalDeMascota();
        dadoQueElUsuarioTocaElBotonDeAdoptar();
        dadoQueElUsuarioCargaSusDatosParaAdoptarCon("German", "german@gmail.com", "Casa", "true", "Tengo experiencia cuidando perros y gatos.", "Tengo un patio amplio y seguro.");
        cuandoElUsuarioTocaEnviarSolicitud();
        entoncesDeberiaVerConfirmacionDeEnvio();
    }

    private void dadoQueElUsuarioIniciaSesion(String email, String clave) {
        vistaLogin.escribirEMAIL(email);
        vistaLogin.escribirClave(clave);
        vistaLogin.darClickEnIniciarSesion();
    }

    private void dadoQueElUsuarioNavegaALaVistaMascotasEnAdopcion() {
        vistaMascotas.darClickEnMascotasEnAdopcion();
    }

    private void cuandoElUsuarioTocaEnviarSolicitud() {
        vistaMascotas.darClickEnviarSolicitudAdopcion();
    }

    private void entoncesDeberiaSerRedirigidoALaVistaDeMascotasEnAdopcion() throws MalformedURLException {
        URL url = vistaMascotas.obtenerURLActual();
        assertThat(url.getPath(), matchesPattern("^/tallerwebi-base-1.0-SNAPSHOT/mascotas(?:;jsessionid=[^/\\s]+)?$"));
    }

    private void cuandoElUsuarioAbreElModalDeMascota() {
        vistaMascotas.darClickEnPrimerBotonDetalle();
    }

    private void dadoQueElUsuarioAbraElModalDeMascota() {
        vistaMascotas.darClickEnPrimerBotonDetalle();
    }

    private void dadoQueElUsuarioTocaElBotonDeAdoptar() {
        vistaMascotas.darClickEnAdoptar();
    }

    private void dadoQueElUsuarioCargaSusDatosParaAdoptarCon(String nombre,String email,String tipoVivienda,String tieneAnimales,String experiencia,String espacio ) {
        vistaMascotas.escribirNombre(nombre);
        vistaMascotas.escribirEmail(email);
        vistaMascotas.seleccionarTipoVivienda(tipoVivienda);
        vistaMascotas.seleccionarSiTieneAnimales(tieneAnimales);
        vistaMascotas.escribirExperiencia(experiencia);
        vistaMascotas.escribirEspacioDisponible(espacio);

    }

    private void entoncesDeberiaVerElModalConDetallesDeLaMascota(){
        Page page = context.pages().get(0);
        Locator modal = page.locator("#modalMascota");
        modal.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        assertThat(modal.isVisible(), is(true));
    }

    private void entoncesDeberiaVerConfirmacionDeEnvio() {
        String texto = vistaMascotas.obtenerMensajeDeExito();
        assertThat("¡Formulario enviado con éxito! Te enviaremos un correo electrónico y nos pondremos en contacto cuando resolvamos tu petición.", equalToIgnoringCase(texto));
    }
}

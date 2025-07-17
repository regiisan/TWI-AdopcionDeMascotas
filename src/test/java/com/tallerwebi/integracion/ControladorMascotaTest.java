package com.tallerwebi.integracion;

import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class ControladorMascotaTest {

    private Usuario usuarioMock;

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @BeforeEach
    public void init(){
        usuarioMock = mock(Usuario.class);
        when(usuarioMock.getEmail()).thenReturn("dami@unlam.com");
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void debeRetornarLaVistaMascotasCuandoSeNavegaASuRuta() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/mascotas"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        assert modelAndView != null;
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("mascotas"));
        assertThat(modelAndView.getModel(), hasKey("mascotas"));
    }

    @Test
    public void debeRedirigirAlLoginCuandoElUsuarioNoEstaLogueadoYAccedeAlFormularioDeNuevaMascota() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/mascota/nueva"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/login"));
    }

    @Test
    public void debeRetornarVistaDarEnAdopcionConFormularioYListaDeMascotasPendientesCuandoElUsuarioEsAdmin() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/mascota/nueva")
                .sessionAttr("idUsuario", 1L)
                .sessionAttr("ROL", "ADMIN"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("formulario-dar-en-adopcion"));
        assertThat(modelAndView.getModel(), hasKey("mascotasPendientes"));
    }

    @Test
    public void debeRetornarVistaDarEnAdopcionConFormularioCuandoElUsuarioNoEsAdmin() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/mascota/nueva")
                        .sessionAttr("idUsuario", 1L)
                        .sessionAttr("ROL", "USUARIO"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("formulario-dar-en-adopcion"));
        assertThat(modelAndView.getModel(), not(hasKey("mascotasPendientes")));
    }

    @Test
    public void debeMostrarMascotasConFiltros() throws Exception {
        MvcResult result = mockMvc.perform(get("/mascotas")
                        .param("tipo", "PERRO")
                        .param("sexo", "MACHO")
                        .param("tamano", "GRANDE")
                        .param("energia", "ALTA"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        assert modelAndView != null;
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("mascotas"));
        assertTrue(modelAndView.getModel().containsKey("mascotas"));
        assertTrue(modelAndView.getModel().containsKey("tipoSeleccionado"));
        assertTrue(modelAndView.getModel().containsKey("sexoSeleccionado"));
        assertTrue(modelAndView.getModel().containsKey("tamanoSeleccionado"));
        assertTrue(modelAndView.getModel().containsKey("energiaSeleccionada"));
    }
}

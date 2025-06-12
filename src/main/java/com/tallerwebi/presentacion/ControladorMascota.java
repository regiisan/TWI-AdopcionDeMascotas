package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.*;
import com.tallerwebi.dominio.servicios.ServicioMascota;
import com.tallerwebi.dominio.servicios.ServicioRecomendacion;
import com.tallerwebi.dominio.servicios.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ControladorMascota {

    private ServicioMascota servicioMascota;
    private ServicioRecomendacion servicioRecomendacion;
    private ServicioUsuario servicioUsuario;

    @Autowired
    public ControladorMascota(ServicioMascota servicioMascota, ServicioRecomendacion servicioRecomendacion, ServicioUsuario servicioUsuario) {
        this.servicioMascota = servicioMascota;
        this.servicioRecomendacion = servicioRecomendacion;
        this.servicioUsuario = servicioUsuario;
    }

    /*
    @RequestMapping(path = "/mascotas", method = RequestMethod.GET)
    public ModelAndView mostrarMascotas(){
        List<MascotaDto> mascotas = servicioMascota.obtenerMascotas();
        boolean mostrarSugerenciaDePreferencias = true;

        ModelAndView model = new ModelAndView("mascotas");
        model.addObject("mascotas",mascotas);
        model.addObject("mostrarSugerenciaDePreferencias",mostrarSugerenciaDePreferencias);

        return model;
    }*/

    @RequestMapping(path = "/mascotas", method = RequestMethod.GET)
    public ModelAndView mostrarMascotas(HttpSession session) {
        Long id = (Long) session.getAttribute("idUsuario");

        ModelAndView model = new ModelAndView("mascotas");

        if (id != null) {
            Usuario usuario = servicioUsuario.buscarPorId(id);
            boolean mostrarSugerenciaDePreferencias = !servicioUsuario.tienePreferenciasCargadas(usuario);

            List<MascotaDto> mascotasRecomendadas = servicioRecomendacion.obtenerMascotasRecomendadas(usuario);

            model.addObject("mascotas", mascotasRecomendadas);
            model.addObject("usuario", usuario);
            model.addObject("mostrarSugerenciaDePreferencias", mostrarSugerenciaDePreferencias);
        } else {
            List<MascotaDto> mascotas = servicioMascota.obtenerMascotas();
            model.addObject("mascotas", mascotas);
            model.addObject("mostrarSugerenciaDePreferencias", true);
        }

        return model;
    }

    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public ModelAndView mostrarMascotasDestacadas(){
        List<MascotaDto> mascotasDestacadas = servicioMascota.obtenerMascotasDestacadas();

        ModelAndView model = new ModelAndView("home");
        model.addObject("mascotas",mascotasDestacadas);

        return model;
    }

    @RequestMapping(path = "/mascota/nueva", method = RequestMethod.GET)
    public ModelAndView mostrarFormularioAdopcion() {
        ModelAndView mav = new ModelAndView("formulario-dar-en-adopcion");
        mav.addObject("mascota", new Mascota());
        return mav;
    }

}

package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Mascota;
import com.tallerwebi.dominio.ServicioMascota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorMascota {

    private ServicioMascota servicioMascota;

    @Autowired
    public ControladorMascota(ServicioMascota servicioMascota) {
        this.servicioMascota = servicioMascota;
    }

    @RequestMapping(path = "/mascotas", method = RequestMethod.GET)
    public ModelAndView mostrarMascotas(){

        List<Mascota> mascotas = servicioMascota.obtenerMascotas();
        ModelAndView model = new ModelAndView("mascotas");
        model.addObject("mascotas",mascotas);
        return model;
    }

    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public ModelAndView mostrarMascotasDestacadas(){

        List<Mascota> mascotasDestacadas = servicioMascota.obtenerMascotasDestacadas();
        ModelAndView model = new ModelAndView("home");
        model.addObject("mascotas",mascotasDestacadas);
        return model;
    }
}

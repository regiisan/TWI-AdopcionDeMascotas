package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.Mascota;
import com.tallerwebi.dominio.entidades.SolicitudAdopcion;
import com.tallerwebi.dominio.servicios.ServicioMascota;
import com.tallerwebi.dominio.servicios.ServicioSolicitudAdoptar;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ControladorSolicitudAdoptar {

    private final ServicioMascota servicioMascota;
    private final ServicioSolicitudAdoptar servicioSolicitudAdoptar;

    public ControladorSolicitudAdoptar(ServicioMascota servicioMascota, ServicioSolicitudAdoptar servicioSolicitudAdoptar) {
        this.servicioMascota = servicioMascota;
        this.servicioSolicitudAdoptar = servicioSolicitudAdoptar;
    }

    @GetMapping("/mascota/{id}/adoptar")
    public ModelAndView mostrarFormularioAdopcion(@PathVariable Long id) {
        SolicitudAdopcion solicitud = new SolicitudAdopcion();
        // solicitud.setMascotaId(id);
        Mascota mascota = servicioMascota.obtenerMascotaPorId(id);
        solicitud.setMascota(mascota);

        ModelAndView model = new ModelAndView("formulario-adopcion");
        model.addObject("mascota", mascota);
        model.addObject("solicitud", solicitud);

        return model;
    }

    @PostMapping("/mascota/{id}/adoptar/guardar")
    public String guardarSolicitudAdopcion(@ModelAttribute("solicitud") SolicitudAdopcion solicitud, @PathVariable Long id) {
        // solicitud.setMascotaId(id);
        Mascota mascota = servicioMascota.obtenerMascotaPorId(id);
        solicitud.setMascota(mascota);
        solicitud.setEstado("Pendiente");

        servicioSolicitudAdoptar.guardar(solicitud);

        return "redirect:/mascotas";
    }

}


package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.Mascota;
import com.tallerwebi.dominio.repositorios.RepositorioMascota;
import com.tallerwebi.dominio.repositorios.RepositorioSolicitud;
import com.tallerwebi.dominio.entidades.SolicitudAdopcion;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
public class ControladorSolicitud {

    private final RepositorioMascota repoMascota;
    private final RepositorioSolicitud repoSolicitud;

    public ControladorSolicitud(RepositorioMascota repoMascota, RepositorioSolicitud repoSolicitud) {
        this.repoMascota = repoMascota;
        this.repoSolicitud = repoSolicitud;
    }

    @GetMapping("/mascota/{id}/adoptar")
    public String mostrarFormularioAdopcion(@PathVariable Long id, Model model) {
        SolicitudAdopcion solicitud = new SolicitudAdopcion();
        solicitud.setMascotaId(id);

        model.addAttribute("mascota", repoMascota.buscarPorId(id));
        model.addAttribute("solicitud", solicitud);

        return "formulario-adopcion";
    }

    public String guardarSolicitudAdopcion(@ModelAttribute("solicitud") SolicitudAdopcion solicitud, @PathVariable Long id) {
        solicitud.setMascotaId(id);
        solicitud.setEstado("Pendiente");

        repoSolicitud.guardar(solicitud);

        return "redirect:/mascotas";
    }


    @PostMapping("/mascota/{id}/adoptar")
    public String procesarFormularioAdopcion(@PathVariable Long id,
                                             @ModelAttribute SolicitudAdopcion solicitud) {

        Mascota mascota = repoMascota.buscarPorId(id);
        if (mascota == null) {
            return "redirect:/mascotas";
        }

        solicitud.setMascotaId(id);
        solicitud.setEstado("pendiente");

        repoSolicitud.guardar(solicitud);

        return "redirect:/mascotas?solicitud_enviada";
    }
}


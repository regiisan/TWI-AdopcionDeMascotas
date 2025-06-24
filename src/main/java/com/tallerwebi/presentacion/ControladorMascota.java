package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.*;
import com.tallerwebi.dominio.servicios.ServicioMascota;
import com.tallerwebi.dominio.servicios.ServicioRecomendacion;
import com.tallerwebi.dominio.servicios.ServicioUsuario;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

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

    // ✅ Método original sin filtros (llamado por los tests y por navegación simple)
    @RequestMapping(path = "/mascotas", method = RequestMethod.GET, params = {"!tipo", "!sexo", "!tamano", "!energia"})
    public ModelAndView mostrarMascotas(HttpSession session) {
        return mostrarMascotas(null, null, null, null, session);
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

    @RequestMapping(path = "/mascotas", method = RequestMethod.GET)
    public ModelAndView mostrarMascotas(
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String sexo,
            @RequestParam(required = false) String tamano,
            @RequestParam(required = false) String energia,
            HttpSession session) {

        Long id = (Long) session.getAttribute("idUsuario");
        ModelAndView model = new ModelAndView("mascotas");

        List<MascotaDto> mascotas;

        if (id != null) {
            Usuario usuario = servicioUsuario.buscarPorId(id);
            boolean mostrarSugerenciaDePreferencias = !servicioUsuario.tienePreferenciasCargadas(usuario);

            mascotas = servicioRecomendacion.obtenerMascotasRecomendadas(usuario);

            model.addObject("usuario", usuario);
            model.addObject("mostrarSugerenciaDePreferencias", mostrarSugerenciaDePreferencias);
        } else {
            mascotas = servicioMascota.obtenerMascotas();
            model.addObject("mostrarSugerenciaDePreferencias", true);
        }

        if (tipo != null) {
            mascotas = mascotas.stream()
                    .filter(m -> m.getTipo().name().equalsIgnoreCase(tipo))
                    .collect(Collectors.toList());
        }

        if (sexo != null) {
            mascotas = mascotas.stream()
                    .filter(m -> m.getSexo().name().equalsIgnoreCase(sexo))
                    .collect(Collectors.toList());
        }

        if (tamano != null) {
            mascotas = mascotas.stream()
                    .filter(m -> m.getTamano().name().equalsIgnoreCase(tamano))
                    .collect(Collectors.toList());
        }

        if (energia != null) {
            mascotas = mascotas.stream()
                    .filter(m -> m.getNivelEnergia().name().equalsIgnoreCase(energia))
                    .collect(Collectors.toList());
        }

        model.addObject("mascotas", mascotas);
        return model;
    }

    @RequestMapping(path = "/mascotas/filtrar", method = RequestMethod.GET)
    public ModelAndView filtrarMascotas(@RequestParam(required = false) Tipo tipo,
                                        @RequestParam(required = false) Sexo sexo,
                                        @RequestParam(required = false) Tamano tamano,
                                        @RequestParam(required = false) NivelEnergia energia,
                                        HttpSession session) {

        List<MascotaDto> mascotasFiltradas = servicioMascota.obtenerMascotasFiltradas(tipo, sexo, tamano, energia);
        ModelAndView model = new ModelAndView("mascotas");
        model.addObject("mascotas", mascotasFiltradas);
        model.addObject("mostrarSugerenciaDePreferencias", true);
        return model;
    }

}

package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.*;
import com.tallerwebi.dominio.servicios.ServicioMascota;
import com.tallerwebi.dominio.servicios.ServicioRecomendacion;
import com.tallerwebi.dominio.servicios.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class ControladorMascota {

    private ServicioMascota servicioMascota;
    private ServicioRecomendacion servicioRecomendacion;
    private ServicioUsuario servicioUsuario;
    //private final String UPLOAD_DIRECTORY = "src/main/webapp/resources/core/images/mascotas/";
    private final String UPLOAD_DIRECTORY = "/app/uploads/mascotas/";

    @Autowired
    public ControladorMascota(ServicioMascota servicioMascota, ServicioRecomendacion servicioRecomendacion, ServicioUsuario servicioUsuario) {
        this.servicioMascota = servicioMascota;
        this.servicioRecomendacion = servicioRecomendacion;
        this.servicioUsuario = servicioUsuario;
        File uploadDir = new File(UPLOAD_DIRECTORY);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
    }

    @RequestMapping(path = "/mascotas", method = RequestMethod.GET, params = {"!tipo", "!sexo", "!tamano", "!energia"})
    public ModelAndView mostrarMascotas(HttpSession session) {
        return mostrarMascotas(null, null, null, null, session);
    }

    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public ModelAndView mostrarMascotasDestacadas() {
        List<MascotaDto> mascotasDestacadas = servicioMascota.obtenerMascotasDestacadas();

        ModelAndView model = new ModelAndView("home");
        model.addObject("mascotas", mascotasDestacadas);

        return model;
    }

    @RequestMapping(path = "/mascota/nueva", method = RequestMethod.GET)
    public ModelAndView mostrarFormularioAdopcion(HttpSession session) {
        String rol = (String) session.getAttribute("ROL");
        Long idUsuario = (Long) session.getAttribute("idUsuario");

        if (idUsuario == null) {
            return new ModelAndView("redirect:/login");
        }

        ModelAndView mav = new ModelAndView("formulario-dar-en-adopcion");
        mav.addObject("mascota", new Mascota());

        if (rol != null && rol.equalsIgnoreCase("ADMIN")) {
            List<Mascota> mascotasPendientes = servicioMascota.obtenerMascotasPorEstadoEntidad("Pendiente");
            mav.addObject("mascotasPendientes", mascotasPendientes);
        }

        return mav;
    }

    @RequestMapping(path = "/mascota/guardar", method = RequestMethod.POST)
    public ModelAndView guardarMascota(@ModelAttribute("mascota") Mascota mascota,
                                       @RequestParam("imagen") MultipartFile imagen,
                                       HttpSession session,
                                       RedirectAttributes redirectAttributes) {
        Long idUsuario = (Long) session.getAttribute("idUsuario");
        String rol = (String) session.getAttribute("ROL");

        if (idUsuario == null) {
            return new ModelAndView("redirect:/login");
        }

        try {
            // Generar un nombre único para el archivo
            String nombreArchivo = UUID.randomUUID().toString() + "_" + imagen.getOriginalFilename();
            Path rutaCompleta = Paths.get(UPLOAD_DIRECTORY + nombreArchivo);

            // Guardar el archivo
            Files.write(rutaCompleta, imagen.getBytes());

            // Actualizar el campo img de la mascota con la ruta relativa
            mascota.setImg("uploads/mascotas/" + nombreArchivo);

            Usuario usuario = servicioUsuario.buscarPorId(idUsuario);
            mascota.setUsuario(usuario);

            servicioMascota.guardar(mascota);

            if (rol != null && rol.equalsIgnoreCase("ADMIN")) {
                redirectAttributes.addFlashAttribute("mensaje", "¡Mascota agregada correctamente!");
                return new ModelAndView("redirect:/mascotas");
            }

            redirectAttributes.addFlashAttribute("mensaje", "¡Publicación exitosa! Te enviaremos un correo electrónico cuando tu mascota esté visible para los adoptantes.");
            return new ModelAndView("redirect:/home");

        } catch (IOException e) {
            ModelAndView model = new ModelAndView("formulario-dar-en-adopcion");
            model.addObject("error", "Error al subir la imagen. Por favor, intente nuevamente.");
            return model;
        }
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
        model.addObject("mascotas", mascotas);
        model.addObject("tipoSeleccionado", tipo);
        model.addObject("sexoSeleccionado", sexo);
        model.addObject("tamanoSeleccionado", tamano);
        model.addObject("energiaSeleccionada", energia);

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

        // 👉 Añadimos los valores seleccionados para mantenerlos en el HTML
        model.addObject("tipoSeleccionado", tipo);
        model.addObject("sexoSeleccionado", sexo);
        model.addObject("tamanoSeleccionado", tamano);
        model.addObject("energiaSeleccionada", energia);

        return model;
    }

    @RequestMapping(path = "/admin/mascotas/{id}/aprobar", method = RequestMethod.POST)
    public ModelAndView aprobarMascota(@PathVariable Long id, HttpSession session) {
        String rol = (String) session.getAttribute("ROL");

        if (rol != null && rol.equalsIgnoreCase("ADMIN")) {
            servicioMascota.aprobarMascota(id);
        }

        return new ModelAndView("redirect:/mascota/nueva");
    }

    @RequestMapping(path = "/admin/mascotas/{id}/rechazar", method = RequestMethod.POST)
    public ModelAndView rechazarMascota(@PathVariable Long id, HttpSession session) {
        String rol = (String) session.getAttribute("ROL");

        if (rol != null && rol.equalsIgnoreCase("ADMIN")) {
            servicioMascota.rechazarMascota(id);
        }

        return new ModelAndView("redirect:/mascota/nueva");
    }
}

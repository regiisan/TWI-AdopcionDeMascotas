package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.*;
import com.tallerwebi.dominio.servicios.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;


@Controller
public class ControladorUsuario {

    private ServicioUsuario servicioUsuario;

    @Autowired
    public ControladorUsuario(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }

    @GetMapping(path = "/preferencias")
    public ModelAndView mostrarFormularioPreferencias(HttpSession session) {
        ModelMap modelo = new ModelMap();

        Long id = (Long) session.getAttribute("idUsuario");

        if (id == null) {
            return new ModelAndView("redirect:/login");
        }

        Usuario usuario = servicioUsuario.buscarPorId(id);
        UsuarioDto usuarioDto = new UsuarioDto(usuario);

        modelo.addAttribute("usuario", usuarioDto);
        modelo.addAttribute("tipos", Tipo.values());
        modelo.addAttribute("sexos", Sexo.values());
        modelo.addAttribute("tamanos", Tamano.values());
        modelo.addAttribute("energias", NivelEnergia.values());

        return new ModelAndView("formulario-PreferenciasDelUsuario", modelo);
    }

    @PostMapping("/guardar-preferencias")
    public ModelAndView guardarPreferencias(@ModelAttribute("usuario") UsuarioDto usuarioConPreferencias, HttpSession session) {
        Long id = (Long) session.getAttribute("idUsuario");

        if (id == null) {
            return new ModelAndView("redirect:/login");
        }

        Usuario usuario = servicioUsuario.buscarPorId(id);
        usuarioConPreferencias.obtenerEntidad(usuario);

        servicioUsuario.modificar(usuario);

        return new ModelAndView("redirect:/mascotas");
    }

    @GetMapping("/perfil")
    public ModelAndView mostrarPerfil(HttpSession session) {
        ModelMap modelo = new ModelMap();

        Long id = (Long) session.getAttribute("idUsuario");
        if (id == null) {
            return new ModelAndView("redirect:/login");
        }

        Usuario usuario = servicioUsuario.buscarPorId(id);
        UsuarioDto usuarioDto = new UsuarioDto(usuario);

        modelo.addAttribute("usuario", usuarioDto);

        modelo.addAttribute("tipos", Tipo.values());
        modelo.addAttribute("sexos", Sexo.values());
        modelo.addAttribute("tamanos", Tamano.values());
        modelo.addAttribute("nivelesEnergia", NivelEnergia.values());

        return new ModelAndView("perfil", modelo);
    }

    @PostMapping("/editar-perfil")
    public ModelAndView editarPerfil(@ModelAttribute("usuario") UsuarioDto formUsuario,
                                     HttpSession session) {

        Long id = (Long) session.getAttribute("idUsuario");
        if (id == null) {
            return new ModelAndView("redirect:/login");
        }

        Usuario usuario = servicioUsuario.buscarPorId(id);
        formUsuario.obtenerEntidad(usuario);
        
        servicioUsuario.modificar(usuario);
        return new ModelAndView("redirect:/perfil");
    }

    @GetMapping("/nosotros")
public String nosotros() {
    return "nosotros";
}


}

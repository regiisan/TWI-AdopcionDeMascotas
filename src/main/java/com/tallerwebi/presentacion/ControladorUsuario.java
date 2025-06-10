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

        modelo.addAttribute("usuario", usuario);
        modelo.addAttribute("tipos", Tipo.values());
        modelo.addAttribute("sexos", Sexo.values());
        modelo.addAttribute("tamanos", Tamano.values());
        modelo.addAttribute("energias", NivelEnergia.values());

        return new ModelAndView("formulario-PreferenciasDelUsuario", modelo);
    }

    @PostMapping("/guardar-preferencias")
    public ModelAndView guardarPreferencias(@ModelAttribute("usuario") Usuario usuarioConPreferencias, HttpSession session) {
        Long id = (Long) session.getAttribute("idUsuario");

        if (id == null) {
            return new ModelAndView("redirect:/login");
        }

        Usuario usuario = servicioUsuario.buscarPorId(id);

        usuario.setTipoPreferido(usuarioConPreferencias.getTipoPreferido());
        usuario.setSexoPreferido(usuarioConPreferencias.getSexoPreferido());
        usuario.setEdadPreferida(usuarioConPreferencias.getEdadPreferida());
        usuario.setTamanoPreferido(usuarioConPreferencias.getTamanoPreferido());
        usuario.setNivelEnergiaPreferido(usuarioConPreferencias.getNivelEnergiaPreferido());

        servicioUsuario.modificar(usuario);

        return new ModelAndView("redirect:/mascotas");
    }
}

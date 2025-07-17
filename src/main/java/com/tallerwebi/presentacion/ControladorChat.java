package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.MensajeDto;
import com.tallerwebi.dominio.servicios.ServicioChat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ControladorChat {

    private ServicioChat servicioChat;

    @Autowired
    public ControladorChat(ServicioChat servicioChat) {
        this.servicioChat = servicioChat;
    }

    @RequestMapping(path = "/sala-chat", method = RequestMethod.GET)
    public ModelAndView mostrarChat(HttpSession session) {
        Long idUsuario = (Long) session.getAttribute("idUsuario");
        String nombreUsuario = (String) session.getAttribute("nombreUsuario");

        if (idUsuario == null) {
            return new ModelAndView("redirect:/login");
        }

        ModelAndView mav = new ModelAndView("sala-chat");
        mav.addObject("idUsuario", idUsuario);
        mav.addObject("nombreUsuario", nombreUsuario);

        List<MensajeDto> mensajes = servicioChat.obtenerMensajes();
        mav.addObject("mensajes", mensajes);

        return mav;
    }
}
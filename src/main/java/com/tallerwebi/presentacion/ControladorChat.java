package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class ControladorChat {

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

        return mav;
    }
}
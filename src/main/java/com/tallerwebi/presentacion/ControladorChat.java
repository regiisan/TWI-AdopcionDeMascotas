package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class ControladorChat {

    @RequestMapping(path = "/sala-chat", method = RequestMethod.GET)
    public ModelAndView irAHome(HttpSession session) {
        Long idUsuario = (Long) session.getAttribute("idUsuario");

        if (idUsuario == null) {
            return new ModelAndView("redirect:/login");
        }

        return new ModelAndView("sala-chat");
    }
}
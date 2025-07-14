package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.MensajeDto;
import com.tallerwebi.dominio.entidades.Mensaje;
import com.tallerwebi.dominio.servicios.ServicioChat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class ControladorChatSocket {

    private ServicioChat servicioChat;

    @Autowired
    public ControladorChatSocket(ServicioChat servicioChat) {
        this.servicioChat = servicioChat;
    }

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public MensajeDto getMessages(Mensaje mensajeRecibido) throws Exception {
        Mensaje mensaje = new Mensaje(
                mensajeRecibido.getMessage(),
                mensajeRecibido.getEmisorId(),
                mensajeRecibido.getNombreUsuario(),
                LocalDateTime.now()
        );

        servicioChat.guardarMensaje(mensaje);

        return new MensajeDto(
                mensaje.getMessage(),
                mensaje.getEmisorId(),
                mensaje.getNombreUsuario(),
                mensaje.getFecha()
        );
    }
}

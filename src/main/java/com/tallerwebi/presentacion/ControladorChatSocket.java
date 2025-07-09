package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.MensajeDto;
import com.tallerwebi.dominio.entidades.MensajeRecibido;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ControladorChatSocket {


    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public MensajeDto getMessages(MensajeRecibido mensajeRecibido) throws Exception {
        return new MensajeDto(
                mensajeRecibido.getMessage(),
                mensajeRecibido.getEmisorId(),
                mensajeRecibido.getNombreUsuario()
        );
    }
}

package com.tallerwebi.dominio.servicios;

import jakarta.mail.MessagingException;

public interface ServicioMail {
    void enviarMail(String destinatario, String asunto, String cuerpo) throws MessagingException;
}

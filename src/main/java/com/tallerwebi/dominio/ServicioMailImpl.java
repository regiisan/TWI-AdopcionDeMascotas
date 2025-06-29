package com.tallerwebi.dominio;

import com.tallerwebi.dominio.servicios.ServicioMail;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service("servicioMail")
public class ServicioMailImpl implements ServicioMail {

    private final String remitente = "ignaciomosquera1998@gmail.com";
    private final String contraseña = "hrvp kfvr xnlg rpce"; 

    private final Session session;

    public ServicioMailImpl() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, contraseña);
            }
        });
    }

    @Override
    public void enviarMail(String destinatario, String asunto, String cuerpo) throws MessagingException {
        Message mensaje = new MimeMessage(session);
        mensaje.setFrom(new InternetAddress(remitente));
        mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
        mensaje.setSubject(asunto);
        mensaje.setText(cuerpo);

        Transport.send(mensaje);
    }
}

package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.Mensaje;
import com.tallerwebi.dominio.entidades.MensajeDto;
import com.tallerwebi.dominio.repositorios.RepositorioChat;
import com.tallerwebi.dominio.repositorios.RepositorioUsuario;
import com.tallerwebi.dominio.servicios.ServicioChat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service("servicioChat")
@Transactional
public class ServicioChatImpl implements ServicioChat {

    private RepositorioChat repositorioChat;

    @Autowired
    public ServicioChatImpl(RepositorioChat repositorioChat) {
        this.repositorioChat = repositorioChat;
    }


    @Override
    public void guardarMensaje(Mensaje mensaje) {
        repositorioChat.guardarMensaje(mensaje);
    }

    @Override
    public List<MensajeDto> obtenerMensajes() {
        return repositorioChat.obtenerMensajes().stream()
                .map(m -> new MensajeDto(m.getMessage(), m.getEmisorId(), m.getNombreUsuario(), m.getFecha()))
                .collect(Collectors.toList());
    }
}

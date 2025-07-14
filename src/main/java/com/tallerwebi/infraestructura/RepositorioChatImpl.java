package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Mensaje;
import com.tallerwebi.dominio.repositorios.RepositorioChat;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioChat")
public class RepositorioChatImpl implements RepositorioChat {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioChatImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void guardarMensaje(Mensaje mensaje) {
        sessionFactory.getCurrentSession().save(mensaje);
    }

    @Override
    public List<Mensaje> obtenerMensajes() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Mensaje.class);
        return criteria.addOrder(Order.asc("fecha")).list();
    }
}

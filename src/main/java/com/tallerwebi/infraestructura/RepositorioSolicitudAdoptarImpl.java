package com.tallerwebi.infraestructura;


import com.tallerwebi.dominio.repositorios.RepositorioSolicitudAdoptar;
import com.tallerwebi.dominio.entidades.SolicitudAdopcion;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioSolicitud")
public class RepositorioSolicitudAdoptarImpl implements RepositorioSolicitudAdoptar {

    private SessionFactory sessionFactory;
    /*private final List<SolicitudAdopcion> solicitudes = new ArrayList<>();
     */

    @Autowired
    public RepositorioSolicitudAdoptarImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    public List<SolicitudAdopcion> obtenerTodas() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(SolicitudAdopcion.class);
        return criteria.list();
        //return solicitudes;
    }

    @Override
    public SolicitudAdopcion buscarSolicitudPorId(Long id) {
        return (SolicitudAdopcion) sessionFactory.getCurrentSession().createCriteria(SolicitudAdopcion.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();}

    @Override
    public void guardar(SolicitudAdopcion solicitud) {
        sessionFactory.getCurrentSession().save(solicitud);
    }
}


package com.tallerwebi.infraestructura;


import com.tallerwebi.dominio.entidades.Mascota;
import com.tallerwebi.dominio.repositorios.RepositorioSolicitudAdoptar;
import com.tallerwebi.dominio.entidades.SolicitudAdopcion;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioSolicitud")
public class RepositorioSolicitudAdoptarImpl implements RepositorioSolicitudAdoptar {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioSolicitudAdoptarImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    public List<SolicitudAdopcion> obtenerTodas() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(SolicitudAdopcion.class);
        return criteria.list();
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

    @Override
    public List<SolicitudAdopcion> listarSolicitudes() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(SolicitudAdopcion.class);
        criteria.setFetchMode("mascota", FetchMode.JOIN);
        return criteria.list();
    }

    @Override
    public List<SolicitudAdopcion> listarSolicitudesPorEstado(String estado) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM SolicitudAdopcion WHERE estado = :estado";
        return session.createQuery(hql, SolicitudAdopcion.class)
                .setParameter("estado", estado)
                .getResultList();
    }

    @Override
    public void modificar(SolicitudAdopcion solicitud) {
        sessionFactory.getCurrentSession().update(solicitud);
    }

    @Override
    public int contarSolicitudesPendientes() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT COUNT(*) FROM SolicitudAdopcion WHERE estado = :estado";
        Long count = session.createQuery(hql, Long.class)
                .setParameter("estado", "Pendiente")
                .uniqueResult();
        return count.intValue();
    }

    @Override
    public int contarSolicitudesAprobadas() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT COUNT(*) FROM SolicitudAdopcion WHERE estado = :estado";
        Long count = session.createQuery(hql, Long.class)
                .setParameter("estado", "Aprobada")
                .uniqueResult();
        return count.intValue();
    }


}


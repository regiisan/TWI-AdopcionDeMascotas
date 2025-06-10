package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.Mascota;
import com.tallerwebi.dominio.entidades.Usuario;
import com.tallerwebi.dominio.repositorios.RepositorioMascota;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioMascota")
public class RepositorioMascotaImpl implements RepositorioMascota {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioMascotaImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Mascota mascota) {
        sessionFactory.getCurrentSession().save(mascota);
    }

    @Override
    public List<Mascota> listarMascotas() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Mascota.class);
        return criteria.list();
    }

    @Override
    public List<Mascota> listarMascotasDestacadas() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Mascota.class);
        return criteria.setMaxResults(4).list();
    }

    @Override
    public Mascota buscarPorId(Long id) {
        return (Mascota) sessionFactory.getCurrentSession().get(Mascota.class, id);
    }


}

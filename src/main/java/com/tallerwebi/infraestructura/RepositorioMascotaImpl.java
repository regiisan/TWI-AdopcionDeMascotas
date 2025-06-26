package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.entidades.*;
import com.tallerwebi.dominio.repositorios.RepositorioMascota;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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

    @Override
    public List<Mascota> buscarPorFiltros(Tipo tipo, Sexo sexo, Tamano tamano, NivelEnergia energia) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Mascota.class);

        if(tipo != null) criteria.add(Restrictions.eq("tipo", tipo));
        if(sexo != null) criteria.add(Restrictions.eq("sexo", sexo));
        if(tamano != null) criteria.add(Restrictions.eq("tamano", tamano));
        if(energia != null) criteria.add(Restrictions.eq("nivelEnergia", energia));

        return criteria.list();
    }

    @Override
    public List<Mascota> listarMascotasFiltradas(String tipo, String sexo, String tamano, String energia) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Mascota.class);

        if (tipo != null && !tipo.isEmpty()) {
            criteria.add(Restrictions.eq("tipo", Tipo.valueOf(tipo.toUpperCase())));
        }
        if (sexo != null && !sexo.isEmpty()) {
            criteria.add(Restrictions.eq("sexo", Sexo.valueOf(sexo.toUpperCase())));
        }
        if (tamano != null && !tamano.isEmpty()) {
            criteria.add(Restrictions.eq("tamano", Tamano.valueOf(tamano.toUpperCase())));
        }
        if (energia != null && !energia.isEmpty()) {
            criteria.add(Restrictions.eq("nivelEnergia", NivelEnergia.valueOf(energia.toUpperCase())));
        }

        return criteria.list();
    }

    @Override
    public void modificar(Mascota mascota) {
        sessionFactory.getCurrentSession().update(mascota);
    }
}

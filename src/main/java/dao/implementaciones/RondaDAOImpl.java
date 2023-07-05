package dao.implementaciones;

import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.glassfish.jersey.process.internal.RequestScoped;
import org.jvnet.hk2.annotations.Service;

import auxiliares.EntityManagerSingleton;
import dao.interfaces.GenericoDAO;
import dao.interfaces.RondaDAO;
import entidades.Ronda;
import jakarta.inject.Inject;
import java.util.Date;
import java.sql.Time;

@Service
@RequestScoped
public class RondaDAOImpl extends GenericoDAOImpl<Ronda> implements RondaDAO {
	
	
	public Ronda rondaActual() {
		Query q = em.createQuery("FROM "+ Ronda.class.getName()+" WHERE fechaInicio <= CURRENT_DATE AND fechaFin >= CURRENT_DATE ");
		try {
			Ronda r=(Ronda)q.getSingleResult();
			return r;
		}catch(NoResultException e){
			return null;
		}
	}
}

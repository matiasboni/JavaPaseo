package dao.implementaciones;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.glassfish.jersey.process.internal.RequestScoped;
import org.jvnet.hk2.annotations.Service;

import auxiliares.EntityManagerSingleton;
import dao.interfaces.GenericoDAO;
import dao.interfaces.RubroDAO;
import entidades.Producto;
import entidades.Rubro;
import jakarta.inject.Inject;

@Service
@RequestScoped
public class RubroDAOImpl extends GenericoDAOImpl<Rubro> implements RubroDAO{
	
	public List<Producto> productosDeRubro(Rubro r){
		Query q = em.createQuery("SELECT p FROM Producto p where(p.rubro= :r)");
		q.setParameter("r", r);
		List<Producto> l=q.getResultList();
		return l;
	}
	
}

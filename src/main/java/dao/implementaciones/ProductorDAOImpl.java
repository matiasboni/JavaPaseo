package dao.implementaciones;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.glassfish.jersey.process.internal.RequestScoped;
import org.jvnet.hk2.annotations.Service;

import auxiliares.EntityManagerSingleton;
import dao.interfaces.GenericoDAO;
import dao.interfaces.ProductorDAO;
import entidades.Imagen;
import entidades.Producto;
import entidades.Productor;
import jakarta.inject.Inject;

@Service
@RequestScoped
public class ProductorDAOImpl extends GenericoDAOImpl<Productor> implements ProductorDAO {

	
	public List<Producto> productosDeProductor(Productor productor){
		Query q = em.createQuery("SELECT p FROM Producto p where(p.productor= :productor)");
		q.setParameter("productor", productor);
		List<Producto> l=q.getResultList();
		return l;
	}
	
	public List<Imagen> imagenesDeProductor(Productor productor){
		Query q = em.createQuery("SELECT i FROM Productor p JOIN p.imagenes i WHERE p = :productor");
		q.setParameter("productor", productor);
		List<Imagen> l=q.getResultList();
		return l;
	}
}

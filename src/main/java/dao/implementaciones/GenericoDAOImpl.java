package dao.implementaciones;

import java.lang.reflect.ParameterizedType;

import jakarta.inject.Inject;
import jakarta.inject.Named;

import javax.persistence.*;

import org.glassfish.jersey.process.internal.RequestScoped;
import org.jvnet.hk2.annotations.Service;

import auxiliares.EntityManagerSingleton;

import java.util.List;

import dao.interfaces.GenericoDAO;


@Service
public class GenericoDAOImpl <T> implements GenericoDAO<T> {
	
	private Class<T> entityClass=(Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	
	//@Inject
	protected EntityManager em = EntityManagerSingleton.getEntityManager();
	
	@Override
	public List<T> lista() {
		TypedQuery<T> q = em.createQuery("FROM "+entityClass.getName(),entityClass);
		List<T> l = q.getResultList();
		return l;
	}

	@Override
	public void guardar(T o) {
		EntityTransaction etx = em.getTransaction();
		try {
			etx.begin();
			em.persist(o);
			etx.commit();
		}catch(Exception e){
			if(etx!=null && etx.isActive())
				etx.rollback();
			throw(e);
		}
	}

	@Override
	public void eliminar(T o) {
		EntityTransaction etx = em.getTransaction();
		try {
			etx.begin();
			em.remove(o);
			etx.commit();
		}catch(Exception e){
			if(etx!=null && etx.isActive())
				etx.rollback();
		}
	}

	@Override
	public void modificar(T o) {
		EntityTransaction etx = em.getTransaction();
		try {
			etx.begin();
			em.merge(o);
			etx.commit();
		}catch(Exception e){
			if(etx!=null && etx.isActive())
				etx.rollback();
			throw(e);
		}
	}

	@Override
	public T getById(long id) {
		TypedQuery<T> q = em.createQuery("FROM "+ entityClass.getName() +" c where(c.id= :id)",entityClass);
		q.setParameter("id", id);
		T o;
		try {
			o=(T)q.getSingleResult();
		}catch(NoResultException e) {
			o=null;
		}
		return o;

	}
}

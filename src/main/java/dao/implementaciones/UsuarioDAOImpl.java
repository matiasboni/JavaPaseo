package dao.implementaciones;
import java.util.List;

import entidades.Pedido;
import entidades.Producto;
import entidades.Usuario;
import jakarta.inject.Inject;

import javax.persistence.*;

import org.glassfish.jersey.process.internal.RequestScoped;
import org.jvnet.hk2.annotations.Service;

import auxiliares.EntityManagerSingleton;
import dao.interfaces.GenericoDAO;


@Service
public class UsuarioDAOImpl extends GenericoDAOImpl<Usuario> implements dao.interfaces.UsuarioDAO{
	
	public  Usuario getByEmail(String email) {
		Query q = em.createQuery("SELECT u FROM Usuario u where(u.email= :email)");
		q.setParameter("email", email);
		try {
			Usuario u=(Usuario)q.getSingleResult();
			return u;
		}catch(NoResultException e){
			return null;
		}
	}
}
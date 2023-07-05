package dao.implementaciones;

import java.util.List;

import javax.persistence.Query;

import org.glassfish.jersey.process.internal.RequestScoped;
import org.jvnet.hk2.annotations.Service;

import dao.interfaces.DireccionDAO;
import entidades.Direccion;
import entidades.Pedido;
import entidades.Usuario;

@Service
@RequestScoped
public class DireccionDAOImpl extends GenericoDAOImpl<Direccion> implements DireccionDAO {
	public List<Direccion> direccionesDelUsuario(Usuario usuario) {
		Query q = em.createQuery("SELECT d FROM Direccion d where(d.usuario= :usuario)");
		q.setParameter("usuario", usuario);
		List<Direccion> l=q.getResultList();
		return l;
	}
	
}

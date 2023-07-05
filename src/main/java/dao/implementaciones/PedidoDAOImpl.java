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
import dao.interfaces.PedidoDAO;
import entidades.Estado;
import entidades.ModalidadEntrega;
import entidades.Pedido;
import entidades.Producto;
import entidades.Productor;
import entidades.Usuario;
import jakarta.inject.Inject;

@Service
@RequestScoped
public class PedidoDAOImpl extends GenericoDAOImpl<Pedido> implements PedidoDAO{
	
	public List<Pedido> pedidosPorModalidad(ModalidadEntrega unaModalidad) {
		Query q = em.createQuery("SELECT p FROM Pedido p where(p.modalidadEntrega= :modalidad and p.estado <> :estado)");
		q.setParameter("modalidad", unaModalidad);
		q.setParameter("estado", Estado.Cancelado);
		List<Pedido> l = q.getResultList();
		return l;
	}
	
	// pedidos es prudctos, productor es usuario
		public List<Pedido> pedidosDelUsuario(Usuario usuario) {
			Query q = em.createQuery("SELECT p FROM Pedido p where(p.usuario= :usuario)");
			q.setParameter("usuario", usuario);
			List<Pedido> l=q.getResultList();
			return l;
		}
		
		public Pedido pedidoPreparacion(Usuario usuario) {
			Query q = em.createQuery("SELECT p FROM Pedido p where(p.usuario= :usuario and p.estado = :estado)");
			q.setParameter("usuario", usuario);
			q.setParameter("estado",Estado.Preparacion);
			try {
				Pedido p=(Pedido)q.getSingleResult();
				return p;
			}catch(NoResultException e){
				return null;
			}
		}
	

}

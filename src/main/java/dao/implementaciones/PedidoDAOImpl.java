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
import entidades.Ronda;
import entidades.Usuario;
import jakarta.inject.Inject;

@Service
@RequestScoped
public class PedidoDAOImpl extends GenericoDAOImpl<Pedido> implements PedidoDAO{
	
	
	// pedidos es proudctos, productor es usuario
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
		
		public List<Pedido> pedidosPorModalidad(ModalidadEntrega unaModalidad) {
			Query q = em.createQuery("SELECT p FROM Pedido p where(p.modalidadEntrega = :modalidad) order by p.fechaPedido");
			q.setParameter("modalidad",unaModalidad);
			List<Pedido> l=q.getResultList();
			return l;
		}
		
		public List<Pedido> pedidosDeUnaRonda(Ronda unaRonda){
			Query q = em.createQuery("SELECT p FROM Pedido p where(p.ronda= :ronda) order by p.estado ASC");
			q.setParameter("ronda",unaRonda);
			List<Pedido> l=q.getResultList();
			return l;
		}
		
		public List<Pedido> pedidosPorEstado(Estado unEstado) {
			Query q = em.createQuery("SELECT p FROM Pedido p where(p.estado = :estado) order by p.fechaPedido");
			q.setParameter("estado",unEstado);
			List<Pedido> l=q.getResultList();
			return l;
		}
	

}

package dao.implementaciones;

import java.util.List;

import javax.persistence.Query;

import org.glassfish.jersey.process.internal.RequestScoped;
import org.jvnet.hk2.annotations.Service;

import dao.interfaces.ProductoPedidoDAO;
import entidades.Estado;
import entidades.ModalidadEntrega;
import entidades.Pedido;
import entidades.ProductoPedido;

@Service
@RequestScoped
public class ProductoPedidoDAOImpl extends GenericoDAOImpl<ProductoPedido> implements ProductoPedidoDAO {
	
	public List<ProductoPedido> listaDePedido(Pedido pedido) {
		Query q = em.createQuery("FROM ProductoPedido p where(p.pedido= :pedido)");
		q.setParameter("pedido", pedido);
		List<ProductoPedido> l = q.getResultList();
		return l;
	}
}

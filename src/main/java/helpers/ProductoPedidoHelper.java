package helpers;

import java.util.List;

import dao.implementaciones.ProductoPedidoDAOImpl;
import entidades.Pedido;
import entidades.ProductoPedido;

public class ProductoPedidoHelper {
	
	public static void eliminarAnteriores(Pedido p) {
		ProductoPedidoDAOImpl ppDAO=new ProductoPedidoDAOImpl();
		List<ProductoPedido>productosPedidosAEliminar=ppDAO.listaDePedido(p);
		for(ProductoPedido pp : productosPedidosAEliminar) {
			ppDAO.eliminar(pp);
		}
	}
}

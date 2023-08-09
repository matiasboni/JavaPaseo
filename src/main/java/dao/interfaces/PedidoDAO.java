package dao.interfaces;

import java.util.List;

import org.glassfish.jersey.spi.Contract;

import entidades.Estado;
import entidades.ModalidadEntrega;
import entidades.Pedido;
import entidades.Ronda;
import entidades.Usuario;

@Contract
public interface PedidoDAO extends GenericoDAO<Pedido> {

	public List<Pedido> pedidosPorModalidad(ModalidadEntrega unaModalidad) ;
	public List<Pedido> pedidosDelUsuario(Usuario usuario);
	public List<Pedido> pedidosDeUnaRonda(Ronda unaRonda);
	public Pedido pedidoPreparacion(Usuario usuario);
	public List<Pedido> pedidosPorEstado(Estado unEstado);
}

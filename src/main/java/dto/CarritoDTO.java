package dto;

import java.util.ArrayList;
import java.util.List;

public class CarritoDTO {
	
	private long id;
	private List<ProductoPedidoDTO> productosPedidos = new ArrayList<ProductoPedidoDTO>();
	private long direccionEntrega_id;
	private long puntoDeRetiro_id;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public List<ProductoPedidoDTO> getProductosPedidos() {
		return productosPedidos;
	}
	public void setProductosPedidos(List<ProductoPedidoDTO> productosPedidos) {
		this.productosPedidos = productosPedidos;
	}
	public long getDireccionEntrega_id() {
		return direccionEntrega_id;
	}
	public void setDireccionEntrega_id(long direccionEntrega_id) {
		this.direccionEntrega_id = direccionEntrega_id;
	}
	public long getPuntoDeRetiro_id() {
		return puntoDeRetiro_id;
	}
	public void setPuntoDeRetiro_id(long puntoDeRetiro_id) {
		this.puntoDeRetiro_id = puntoDeRetiro_id;
	}
	
	
	
	
	
	
}

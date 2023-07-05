package dto;

public class ProductoPedidoDTO {

	private long id;
	private long producto_id;
	private long pedido_id;
	private int cantidad;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getProducto_id() {
		return producto_id;
	}
	public void setProducto_id(long producto_id) {
		this.producto_id = producto_id;
	}
	public long getPedido_id() {
		return pedido_id;
	}
	public void setPedido_id(long pedido_id) {
		this.pedido_id = pedido_id;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	
}

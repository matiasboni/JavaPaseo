package entidades;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
public class ProductoPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne()
    @JoinColumn(name = "producto_id")
    private Producto producto;
    
    @ManyToOne()
    @JoinColumn(name = "pedido_id")
    @JsonBackReference
    private Pedido pedido;

    private int cantidad;
    
    public ProductoPedido() {
    	
    }
    
    public ProductoPedido(Producto unProducto) {
    	this.producto=unProducto;
    }
    
    public ProductoPedido(Producto unProducto,Pedido unPedido,int cantidad) {
    	this.producto=unProducto;
    	this.pedido=unPedido;
    	this.cantidad=cantidad;
    }
    

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	public String toString () {
		return "."+this.cantidad;
	}

    
}
package entidades;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Pedido {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Enumerated(EnumType.STRING)
	@Column(nullable= false)
	private Estado estado;
	private BigDecimal total=BigDecimal.ZERO;
	@Enumerated(EnumType.STRING)
	private ModalidadEntrega modalidadEntrega;
	private Date fechaPedido;
	private LocalDate fechaEntrega;
	@OneToOne
	private Direccion direccionEntrega;
	private String rangoHorarioD;
	@ManyToOne
	@JoinColumn(name="ronda_id")
	private Ronda ronda;
	@ManyToOne
	@JoinColumn(name="puntoDeRetiro_id")
	private PuntoDeRetiro puntoDeRetiro;
	@ManyToOne
	@JoinColumn(name="usuario_id",nullable=false)
	private Usuario usuario;
	@OneToMany(mappedBy="pedido",cascade =CascadeType.ALL)
	@JsonManagedReference
	private List<ProductoPedido> productosPedidos = new ArrayList<ProductoPedido>();
	
	public Pedido() {
		
	}
	
	public Pedido(Estado unEstado, Usuario unUsuario) {
		this.estado=unEstado;
		this.usuario=unUsuario;
	}
	
	
	
	
	public void agregarProductoPedido(ProductoPedido p) {
		     productosPedidos.add(p);
		     this.total=this.total.add(p.getProducto().getPrecio().multiply(BigDecimal.valueOf(p.getCantidad())));    
	}
	
	public void calcularTotal() {
		this.total= BigDecimal.ZERO;
		for(ProductoPedido p :this.productosPedidos) {
			this.total=this.total.add(p.getProducto().getPrecio().multiply(BigDecimal.valueOf(p.getCantidad())));
		}
	}
	
	public void eliminarProductoPedido(ProductoPedido p) {
	     productosPedidos.remove(p);
	     this.total=this.total.subtract(p.getProducto().getPrecio().multiply(BigDecimal.valueOf(p.getCantidad())));
	}
	
	public void eliminarProductosPedidos() {
		this.getProductosPedidos().clear();
	    this.total=BigDecimal.ZERO;
	}
	
	public void confirmar(Ronda unaRonda, Direccion unaDireccion,String unRango) { //Entrega
		if (this.estado.equals(Estado.Preparacion)) {
			this.modalidadEntrega = ModalidadEntrega.Reparto;
			this.direccionEntrega = unaDireccion;
			this.rangoHorarioD=unRango;
			this.setearConfirmar(unaRonda);
		}
		
	}
	
	private void setearConfirmar(Ronda unaRonda) {
		this.fechaPedido = new Date();
		this.ronda = unaRonda;
		this.estado=Estado.Pendiente;
	}
		
	public void confirmar(Ronda unaRonda, PuntoDeRetiro unPuntoDeRetiro) {  // Retiro
		if (this.estado.equals(Estado.Preparacion)) {
			this.modalidadEntrega = ModalidadEntrega.Retiro;
			this.puntoDeRetiro = unPuntoDeRetiro;
			this.setearConfirmar(unaRonda);
		
		}
	}
	public boolean cancelar() {
		boolean ok=false;
		if(this.estado.equals(Estado.Pendiente)) {
			this.estado=Estado.Cancelado;
			ok=true;
		}
		return ok;
	}
	
	public void entregado() {
		this.estado=Estado.Entregado;
		this.fechaEntrega=this.ronda.getFechaRetiro();
	}
	
	
	public void repetir(Pedido p,Ronda unaRonda) {
		this.usuario=p.getUsuario();
		this.modalidadEntrega=p.getModalidadEntrega();
		this.direccionEntrega=p.getDireccionEntrega();
		this.puntoDeRetiro=p.getPuntoDeRetiro();
		this.setearConfirmar(unaRonda);
	}
	
	public ModalidadEntrega getModalidadEntrega() {
		return modalidadEntrega;
	}

	public void setModalidadEntrega(ModalidadEntrega modalidadEntrega) {
		this.modalidadEntrega = modalidadEntrega;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}


	public Date getFechaPedido() {
		return fechaPedido;
	}

	public void setFechaPedido(Date fechaPedido) {
		this.fechaPedido = fechaPedido;
	}
	
	

	public LocalDate getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(LocalDate fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public Direccion getDireccionEntrega() {
		return direccionEntrega;
	}

	public void setDireccionEntrega(Direccion direccionEntrega) {
		this.direccionEntrega = direccionEntrega;
	}

	public Ronda getRonda() {
		return ronda;
	}

	public void setRonda(Ronda ronda) {
		this.ronda = ronda;
	}

	public PuntoDeRetiro getPuntoDeRetiro() {
		return puntoDeRetiro;
	}

	public void setPuntoDeRetiro(PuntoDeRetiro puntoDeRetiro) {
		this.puntoDeRetiro = puntoDeRetiro;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<ProductoPedido> getProductosPedidos() {
		return productosPedidos;
	}

	public void setProductosPedidos(List<ProductoPedido> productosPedidos) {
		this.productosPedidos = productosPedidos;
	}
	
	

	public String getRangoHorarioD() {
		return rangoHorarioD;
	}

	public void setRangoHorarioD(String rangoHorarioD) {
		this.rangoHorarioD = rangoHorarioD;
	}

	public String toString() {
		return this.estado+","+this.usuario.getEmail()+",Total:"+this.total+",ModalidadEntrega:"+this.modalidadEntrega;
	}
	
	
}

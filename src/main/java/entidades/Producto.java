package entidades;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
public class Producto {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Column(nullable= false)
	private String nombre;
	private String descripcion;
	@Column(nullable= false)
	private BigDecimal precio;
	private int stock;
	@ManyToOne
	@JoinColumn(name="rubro_id")
	private Rubro rubro;
	@ManyToOne
	@JoinColumn(name="productor_id")
	private Productor productor;
	@OneToMany(mappedBy="producto",cascade = CascadeType.ALL)
	private List<ProductoPedido> productosPedidos;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,mappedBy="producto")
	private List<Imagen> imagenes=new ArrayList<Imagen>();
	
	public Producto() {
		
	}
	
	public Producto(String unNombre,String unaDescripcion,BigDecimal unPrecio,Rubro unRubro,Productor unProductor) {
		this.nombre=unNombre;
		this.descripcion=unaDescripcion;
		this.precio=unPrecio;
		this.rubro=unRubro;
		this.productor=unProductor;
		this.stock=25;
	}
	
	public void agregarImagen(Imagen i) {
		this.imagenes.add(i);
	}
	
	public boolean hayStockDisponible(int cantidad) {
		return cantidad<=stock;
	}
	
	public void descontarStock(int c) {
		this.stock -= c;
	}
	
	public void sumarStock(int c) {
		this.stock += c;
	}
	
	
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public Rubro getRubro() {
		return rubro;
	}

	public void setRubro(Rubro rubro) {
		this.rubro = rubro;
	}

	public Productor getProductor() {
		return productor;
	}

	public void setProductor(Productor productor) {
		this.productor = productor;
	}


	public List<Imagen> getImagenes() {
		return imagenes;
	}

	public void setImagenes(List<Imagen> imagenes) {
		this.imagenes = imagenes;
	}

	public String toString() {
		return this.nombre+","+this.descripcion+","+this.rubro.getNombre()+",Stock:"+this.stock;
	}
}

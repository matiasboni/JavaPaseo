package entidades;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
public class Productor {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Column(nullable= false)
	private String nombre;
	private String descripcion;
	@OneToMany(mappedBy="productor")
	private List<Producto> productos=new ArrayList<Producto>();
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,mappedBy="productor")
	private List<Imagen> imagenes=new ArrayList<Imagen>();
	
	public Productor() {
		
	}
	
	public Productor(String unNombre,String unaDescripcion) {
		this.nombre=unNombre;
		this.descripcion=unaDescripcion;
	}
	
	public void agregarProducto(Producto p) {
		this.productos.add(p);
	}
	
	public void agregarImagen(Imagen i) {
		this.imagenes.add(i);
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
	
	
	public long getId() {
		return id;
	}

	public String toString() {
		return "Productor:"+this.nombre+",descripcion:"+this.descripcion;
	}
}

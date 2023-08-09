package entidades;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"productos"})
@Entity
public class Rubro {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Column(unique=true,nullable=false)
	private String nombre;
	@OneToMany(mappedBy="rubro")
	private List<Producto> productos=new ArrayList<Producto>();
	
	public Rubro() {
		
	}
	
	public Rubro(String unNombre) {
		this.nombre=unNombre;
	}
	
	public void agregarProducto(Producto p) {
		this.productos.add(p);
	}
	
	public String toString() {
		return this.nombre;
	}

	public long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
}

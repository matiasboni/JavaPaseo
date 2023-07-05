package entidades;

import java.time.LocalTime;
import java.util.List;

import javax.persistence.*;

@Entity
public class PuntoDeRetiro {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Column(nullable= false)
	private String nombre;
	@Column(nullable= false)
	private String direccion;
	@Column(nullable= false)
	private String franjaHoraria;
	@OneToMany(mappedBy="puntoDeRetiro")
	private List<Pedido> Pedido;
	
	public PuntoDeRetiro() {
		
	}
	
	public PuntoDeRetiro(String unNombre, String unaDireccion, String unaFranjaHoraria) {
		this.nombre=unNombre;
		this.direccion=unaDireccion;
		this.franjaHoraria=unaFranjaHoraria;
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

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getFranjaHoraria() {
		return franjaHoraria;
	}

	public void setFranjaHoraria(String franjaHoraria) {
		this.franjaHoraria = franjaHoraria;
	}
	
	
}

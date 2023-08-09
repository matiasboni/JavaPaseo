package entidades;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;

@JsonIgnoreProperties({"pedidos","direcciones"})
@Entity
public class Usuario {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Column(unique=true,nullable=false)
	private String email;
	@Column(nullable=false)
	private String password;
	@Column(nullable=false)
	private String nombre;
	@Column(nullable=false)
	private String apellido;
	@Column(nullable=false)
	private String dni;
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable=false)
	private Rol rol;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true,mappedBy="usuario")
	private List<Direccion> direcciones=new ArrayList<Direccion>();
	@OneToMany(cascade= CascadeType.DETACH,mappedBy="usuario")
	private List<Pedido> pedidos=new ArrayList<Pedido>();
	
	public Usuario() {

	}
	
	public Usuario(String aEmail,String aPassword,Rol aRol, String aNombre, String aApellido, String aDni) {
		this.email=aEmail;
		this.password=aPassword;
		this.rol=aRol;
		this.nombre=aNombre;
		this.apellido=aApellido;
		this.dni=aDni;
	}

	public long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public List<Direccion> getDirecciones() {
		return direcciones;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}
	
	public Rol getRol() {
		return rol;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}
	
	

	public void setRol(Rol rol) {
		this.rol = rol;
	}
	
	

	public void setPassword(String password) {
		this.password = password;
	}

	public String toString() {
		return this.id+","+this.email + ","+ this.password+","+this.rol;
	}
	
	public void agregarDireccion(Direccion unaDireccion) {
		direcciones.add(unaDireccion);
	}
	
	
	
	
}

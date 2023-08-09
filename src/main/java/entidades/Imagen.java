package entidades;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Imagen {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Column(nullable= false)
	private String url;
	@ManyToOne
	@JoinColumn(name="productor_id")
	private Productor productor;
	@ManyToOne
	@JoinColumn(name="producto_id")
	@JsonBackReference
	private Producto producto;
	
	public Imagen() {
		
	}
	
	public Imagen(String unaUrl) {
		url=unaUrl;
	}
	
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Productor getProductor() {
		return productor;
	}

	public void setProductor(Productor productor) {
		this.productor = productor;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public String toString() {
		return this.url;
	}
}

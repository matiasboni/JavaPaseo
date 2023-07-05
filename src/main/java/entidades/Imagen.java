package entidades;
import javax.persistence.*;

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
	private Producto producto;
	
	public Imagen() {
		
	}
	
	public Imagen(String unaUrl) {
		url=unaUrl;
	}
	
	public String toString() {
		return this.url;
	}
}

package entidades;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

@JsonIgnoreProperties("pedidos")
@Entity
public class Ronda {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Column(nullable= false)
	@JsonFormat(pattern="dd/MM/YYYY HH:mm:ss")
	private Date fechaInicio;
	@Column(nullable= false)
	@JsonFormat(pattern="dd/MM/YYYY HH:mm:ss")
	private Date fechaFin;
	@Column(nullable= false)
	@JsonFormat(pattern="dd/MM/YYYY")
	private Date fechaRetiro;
	@OneToMany(mappedBy="ronda")
	private List<Pedido>pedidos;
	
	public Ronda() {
		
	}
	
	public Ronda(Date unaFechaInicio, Date unaFechaFin, Date unaFechaRetiro) {
		this.fechaInicio=unaFechaInicio;
		this.fechaFin=unaFechaFin;
		this.fechaRetiro=unaFechaRetiro;

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaRetiro() {
		return fechaRetiro;
	}

	public void setFechaRetiro(Date fechaRetiro) {
		this.fechaRetiro = fechaRetiro;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
	
	
}

package entidades;

import java.time.LocalDate;
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
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
	private Date fechaInicio;
	@Column(nullable= false)
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
	private Date fechaFin;
	@Column(nullable= false)
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate fechaRetiro;
	@Column(nullable= false)
	@JsonFormat(pattern="HH:mm")
	private LocalTime retiroInicio;
	@Column(nullable= false)
	@JsonFormat(pattern="HH:mm")
	private LocalTime retiroFin;
	@OneToMany(mappedBy="ronda")
	private List<Pedido>pedidos;
	
	public Ronda() {
		
	}
	
	public Ronda(Date unaFechaInicio, Date unaFechaFin,LocalDate unaFechaRetiro,LocalTime unInicio,LocalTime unFin) {
		this.fechaInicio=unaFechaInicio;
		this.fechaFin=unaFechaFin;
		this.fechaRetiro=unaFechaRetiro;
		this.retiroInicio=unInicio;
		this.retiroFin=unFin;
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

	

	public LocalDate getFechaRetiro() {
		return fechaRetiro;
	}

	public void setFechaRetiro(LocalDate fechaRetiro) {
		this.fechaRetiro = fechaRetiro;
	}

	public LocalTime getRetiroInicio() {
		return retiroInicio;
	}

	public void setRetiroInicio(LocalTime retiroInicio) {
		this.retiroInicio = retiroInicio;
	}

	public LocalTime getRetiroFin() {
		return retiroFin;
	}

	public void setRetiroFin(LocalTime retiroFin) {
		this.retiroFin = retiroFin;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
	
	
}

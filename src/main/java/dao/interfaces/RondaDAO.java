package dao.interfaces;

import org.glassfish.jersey.spi.Contract;

import entidades.Ronda;

@Contract
public interface RondaDAO extends GenericoDAO<Ronda> {

	public Ronda rondaActual();
}

package dao.interfaces;

import java.util.List;

import org.glassfish.jersey.spi.Contract;

import entidades.Producto;
import entidades.Rubro;

@Contract
public interface RubroDAO extends GenericoDAO<Rubro> {

	public List<Producto> productosDeRubro(Rubro r);
}

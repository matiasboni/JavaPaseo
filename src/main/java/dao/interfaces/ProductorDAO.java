package dao.interfaces;

import java.util.List;

import org.glassfish.jersey.spi.Contract;

import entidades.Imagen;
import entidades.Producto;
import entidades.Productor;

@Contract
public interface ProductorDAO extends GenericoDAO<Productor> {

	public List<Producto> productosDeProductor(Productor productor);
	public List<Imagen> imagenesDeProductor(Productor productor);
}

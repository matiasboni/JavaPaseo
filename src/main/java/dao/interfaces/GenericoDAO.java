package dao.interfaces;

import java.util.List;
import org.glassfish.jersey.spi.Contract;

@Contract
public interface GenericoDAO<T>{
	
	public List<T> lista();
	
	public void guardar(T o);
	
	public void eliminar(T o);
	
	public void modificar(T o);
	
	public T getById(long id);
}

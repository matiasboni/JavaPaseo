package dao.implementaciones;

import org.glassfish.jersey.process.internal.RequestScoped;
import org.jvnet.hk2.annotations.Service;

import dao.interfaces.ProductoDAO;
import entidades.Producto;

@Service
@RequestScoped
public class ProductoDAOImpl extends GenericoDAOImpl<Producto> implements ProductoDAO {

}

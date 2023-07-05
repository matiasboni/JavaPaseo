package dao.implementaciones;

import org.glassfish.jersey.process.internal.RequestScoped;
import org.jvnet.hk2.annotations.Service;

import dao.interfaces.ImagenDAO;
import entidades.Imagen;

@Service
@RequestScoped
public class ImagenDAOImpl extends GenericoDAOImpl<Imagen> implements ImagenDAO {

}

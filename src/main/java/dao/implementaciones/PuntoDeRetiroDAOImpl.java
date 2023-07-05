package dao.implementaciones;

import org.glassfish.jersey.process.internal.RequestScoped;
import org.jvnet.hk2.annotations.Service;

import dao.interfaces.PuntoDeRetiroDAO;
import entidades.PuntoDeRetiro;

@Service
@RequestScoped
public class PuntoDeRetiroDAOImpl extends GenericoDAOImpl<PuntoDeRetiro> implements PuntoDeRetiroDAO {

}

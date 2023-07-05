package dao.interfaces;

import java.util.List;

import org.glassfish.jersey.spi.Contract;

import entidades.Pedido;
import entidades.Usuario;

@Contract
public interface UsuarioDAO extends GenericoDAO<Usuario> {

	public  Usuario getByEmail(String email);
}

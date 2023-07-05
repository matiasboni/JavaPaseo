package config;

import jakarta.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.glassfish.hk2.api.JustInTimeInjectionResolver;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;

import dao.implementaciones.*;
import dao.interfaces.*;
import servicios.implementaciones.EntityManagerProvider;

public class AppBinder extends AbstractBinder{
	@Override
	protected void configure() {
		bind(EntityManagerProvider.class).to(EntityManager.class).in(Singleton.class);
		bind(GenericoDAOImpl.class).to(GenericoDAO.class).in(RequestScoped.class);
		bind(DireccionDAOImpl.class).to(DireccionDAO.class).in(RequestScoped.class);
		bind(ImagenDAOImpl.class).to(ImagenDAO.class).in(RequestScoped.class);
		bind(PedidoDAOImpl.class).to(PedidoDAO.class).in(RequestScoped.class);
		bind(ProductoDAOImpl.class).to(ProductoDAO.class).in(RequestScoped.class);
		bind(ProductoPedidoDAOImpl.class).to(ProductoPedidoDAO.class).in(RequestScoped.class);
		bind(PuntoDeRetiroDAOImpl.class).to(PuntoDeRetiroDAO.class).in(RequestScoped.class);
		bind(RondaDAOImpl.class).to(RondaDAO.class).in(RequestScoped.class);
		bind(RubroDAOImpl.class).to(RubroDAO.class).in(RequestScoped.class);
		bind(UsuarioDAOImpl.class).to(UsuarioDAO.class).in(RequestScoped.class);
	}
}

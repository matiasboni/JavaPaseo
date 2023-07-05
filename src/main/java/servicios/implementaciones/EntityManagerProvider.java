package servicios.implementaciones;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.jvnet.hk2.annotations.Service;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.inject.Provider;
import jakarta.inject.Singleton;

@Service
@Singleton
public class EntityManagerProvider implements Provider<EntityManager> {

	@Inject
	@Named("prueba")
	private EntityManagerFactory emFactory;
	
	public EntityManagerProvider() {
		emFactory=Persistence.createEntityManagerFactory("paseounlp");
	}
	
	@Override
    public EntityManager get() {
        return emFactory.createEntityManager();
    }
	
}


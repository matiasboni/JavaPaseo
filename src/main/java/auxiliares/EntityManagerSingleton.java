package auxiliares;
import javax.persistence.*;

import org.glassfish.jersey.process.internal.RequestScoped;

@RequestScoped
public class EntityManagerSingleton {
    private static EntityManager entityManager;

    public static EntityManager getEntityManager() {
        if (entityManager == null) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("paseounlp");
            entityManager = emf.createEntityManager();
        }
        return entityManager;
    }
}

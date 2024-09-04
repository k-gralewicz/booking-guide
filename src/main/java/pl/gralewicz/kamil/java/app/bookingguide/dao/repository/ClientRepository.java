package pl.gralewicz.kamil.java.app.bookingguide.dao.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.stereotype.Repository;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ClientEntity;

import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class ClientRepository {

    private static final Logger LOGGER = Logger.getLogger(ClientRepository.class.getName());

    private SessionFactory sessionFactory;

    public ClientRepository() {
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();

        try {
            sessionFactory = new MetadataSources(serviceRegistry)
                    .buildMetadata()
                    .buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy(serviceRegistry);
        }
    }

    public ClientEntity create(ClientEntity clientEntity) {
        LOGGER.info("create(" + clientEntity + ")");
        Session session = sessionFactory.openSession();
        try {
            session.getTransaction().begin();

            Object savedObject = session.save(clientEntity);
            clientEntity.setId((Long) savedObject);
            LOGGER.info("create(" + savedObject + ")");

//        session.persist(clientEntity);

            session.getTransaction().commit();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unable to create client.", e);
            session.getTransaction().rollback();
        }

        LOGGER.info("create(...)=" + clientEntity);
        return clientEntity;
    }
}
// TODO: 21.08.2024 poprawić  Repository
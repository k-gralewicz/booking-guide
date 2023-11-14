package pl.gralewicz.kamil.java.app.bookingguide.dao.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.VisitEntity;

import java.util.logging.Level;
import java.util.logging.Logger;

public class VisitRepository {

    private static final Logger LOGGER = Logger.getLogger(VisitRepository.class.getName());

    private SessionFactory sessionFactory;

    public VisitRepository() {
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

    public VisitEntity create(VisitEntity visitEntity) {
        LOGGER.info("create(" + visitEntity + ")");
        Session session = sessionFactory.openSession();
        try {
            session.getTransaction().begin();

            Object savedObject = session.save(visitEntity);
            visitEntity.setId((Long) savedObject);
            LOGGER.info("create(" + savedObject + ")");

            session.getTransaction().commit();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unable to create client.", e);
            session.getTransaction().rollback();
        }

        LOGGER.info("create(...)=" + visitEntity);
        return visitEntity;
    }
}
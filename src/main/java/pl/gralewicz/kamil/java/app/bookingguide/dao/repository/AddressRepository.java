package pl.gralewicz.kamil.java.app.bookingguide.dao.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.AddressEntity;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AddressRepository {

    private static final Logger LOGGER = Logger.getLogger(AddressRepository.class.getName());

    private SessionFactory sessionFactory;

    public AddressRepository() {
        // TODO: 20.09.2023 zastąpić zawartość konstruktora nową oddzielną klasą, która będzie singleton'em.
//        przenieść zawartość konstruktora do nowej klasy będącej singletone'em.
//        metodę z nowej klasy wywyłać w ciele konstruktora.
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

    public AddressEntity create(AddressEntity addressEntity) {
        LOGGER.info("create(" + addressEntity + ")");
        Session session = sessionFactory.openSession();
        try {
            session.getTransaction().begin();
            session.save(addressEntity);
            session.getTransaction().commit();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unable to create address.", e);
            session.getTransaction().rollback();
        }

        LOGGER.info("create(...)=");
        return addressEntity;
    }
}

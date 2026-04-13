package pl.gralewicz.kamil.java.app.bookingguide.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ShopEntity;

@Repository
public interface ShopRepository extends JpaRepository<ShopEntity, Long> {

//    private static final Logger LOGGER = Logger.getLogger(ShopRepository.class.getName());
//
//    private SessionFactory sessionFactory;
//
//    public ShopRepository() {
//        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
//                .configure("hibernate.cfg.xml")
//                .build();
//
//        try {
//            sessionFactory = new MetadataSources(serviceRegistry)
//                    .buildMetadata()
//                    .buildSessionFactory();
//        } catch (Exception e) {
//            e.printStackTrace();
//            StandardServiceRegistryBuilder.destroy(serviceRegistry);
//        }
//    }
//
//    public ShopEntity create(ShopEntity shopEntity) {
//        LOGGER.info("create(" + shopEntity + ")");
//        Session session = sessionFactory.openSession();
//        try {
//            session.getTransaction().begin();
//
//            Object savedObject = session.save(shopEntity);
//            shopEntity.setId((Long) savedObject);
//            LOGGER.info("create(" + savedObject + ")");
//
//            session.getTransaction().commit();
//        } catch (Exception e) {
//            LOGGER.log(Level.SEVERE, "Unable to create client.", e);
//            session.getTransaction().rollback();
//        }
//
//        LOGGER.info("create(...)=" + shopEntity);
//        return shopEntity;
//    }
}

package pl.gralewicz.kamil.java.app.bookingguide.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ServiceEntity;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {

//    private static final Logger LOGGER = Logger.getLogger(ShopRepository.class.getName());
//
//    private SessionFactory sessionFactory;
//
//    public ServiceRepository() {
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
//    public ServiceEntity create(ServiceEntity serviceEntity) {
//        LOGGER.info("create(" + serviceEntity + ")");
//        Session session = sessionFactory.openSession();
//        try {
//            session.getTransaction().begin();
//
//            Object savedObject = session.save(serviceEntity);
//            serviceEntity.setId((Long) savedObject);
//            LOGGER.info("create(" + savedObject + ")");
//
//            session.getTransaction().commit();
//        } catch (Exception e) {
//            LOGGER.log(Level.SEVERE, "Unable to create client.", e);
//            session.getTransaction().rollback();
//        }
//
//        LOGGER.info("create(...)=" + serviceEntity);
//        return serviceEntity;
//    }
}

package pl.gralewicz.kamil.java.app.bookingguide.dao.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ClientEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ServiceEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.UserEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.VisitEntity;

import java.util.List;

@SpringBootTest
public class VisitRepositoryIntegrationTest {

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Transactional
    @Test
    void listVisitForClient() {
        //given
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setFirstName("Ala");
        ClientEntity savedClientEntity = clientRepository.save(clientEntity);

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("Ala");
        userEntity.setClient(savedClientEntity);
        UserEntity savedUserEntity = userRepository.save(userEntity);

        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setName("Kwas");
        ServiceEntity saveServiceEntity = serviceRepository.save(serviceEntity);

        VisitEntity visitOneEntity = new VisitEntity();
        visitOneEntity.setService(serviceEntity);
        VisitEntity saveVisitOneEntity = visitRepository.save(visitOneEntity);
        visitOneEntity.setClient(savedClientEntity);
        saveVisitOneEntity.setService(saveServiceEntity);

        VisitEntity visitTwoEntity = new VisitEntity();
        visitTwoEntity.setService(serviceEntity);
        VisitEntity saveVisitTwoEntity = visitRepository.save(visitTwoEntity);
        visitTwoEntity.setClient(savedClientEntity);
        saveVisitTwoEntity.setService(saveServiceEntity);

        //when
        List<VisitEntity> visitsByClientId = visitRepository.findByClientId(savedClientEntity.getId());

        //then
        int size = visitsByClientId.size();
        System.out.println("numer of visits: " + size);
    }
}

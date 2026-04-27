package pl.gralewicz.kamil.java.app.bookingguide.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.*;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.*;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@SpringBootTest
@Transactional
class VisitServiceSpringIntegrationTest { // Zmieniona nazwa

    @Autowired
    private VisitService visitService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private UserService userService;

    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private UserRepository userRepository;

    private ClientEntity clientEntity;
    private ServiceEntity serviceEntity;
    private ShopEntity shopEntity;
    private ClientEntity secondClientEntity;
    private ServiceEntity secondServiceEntity;
    private ShopEntity secondShopEntity;
    private UserEntity userEntity;

    @BeforeEach
    void setupTestData() {
        ClientEntity client1 = new ClientEntity();
        clientEntity = clientRepository.save(client1);
        ServiceEntity service1 = new ServiceEntity();
        serviceEntity = serviceRepository.save(service1);
        ShopEntity shop1 = new ShopEntity();
        shopEntity = shopRepository.save(shop1);

        ClientEntity client2 = new ClientEntity();
        secondClientEntity = clientRepository.save(client2);
        ServiceEntity service2 = new ServiceEntity();
        secondServiceEntity = serviceRepository.save(service2);
        ShopEntity shop2 = new ShopEntity();
        secondShopEntity = shopRepository.save(shop2);
    }


    @Test
    void list() {
        // given
        VisitEntity visitEntity1 = new VisitEntity();
//        visitEntity1.setDueDate(LocalDateTime.now().plusDays(1));
        visitEntity1.setClient(clientEntity);
        visitEntity1.setService(serviceEntity);
        visitEntity1.setShop(shopEntity);
        VisitEntity savedVisitEntity1 = visitRepository.save(visitEntity1); // Zapisz zwróconą encję
        Long visit1Id = savedVisitEntity1.getId(); // Pobierz ID

        VisitEntity visitEntity2 = new VisitEntity();
//        visitEntity2.setDueDate(LocalDateTime.now().plusDays(2));
        visitEntity2.setClient(secondClientEntity);
        visitEntity2.setService(secondServiceEntity);
        visitEntity2.setShop(secondShopEntity);
        VisitEntity savedVisitEntity2 = visitRepository.save(visitEntity2); // Zapisz zwróconą encję
        Long visit2Id = savedVisitEntity2.getId(); // Pobierz ID
        // when
        List<Visit> visits = visitService.list();

        // then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(visits, "Lista wizyt nie powinna być null"),
                () -> Assertions.assertEquals(2, visits.size(), "Lista powinna zawierać 2 wizyty"),
                () -> {
                    Visit foundVisit1 = visits.stream()
                            .filter(v -> v.getId() != null && v.getId().equals(visit1Id))
                            .findFirst()
                            .orElse(null);
                    Assertions.assertNotNull(foundVisit1, "Nie znaleziono wizyty 1 (filtrowanie po ID)");
                    Assertions.assertNotNull(foundVisit1.getClient(), "Client w wizycie 1 nie powinien być null");
                    Assertions.assertEquals(clientEntity.getId(), foundVisit1.getClient().getId(), "Niezgodne ID klienta w wizycie 1");
                    // ... itd.
                },
                () -> {
                    Visit foundVisit2 = visits.stream()
                            .filter(v -> v.getId() != null && v.getId().equals(visit2Id))
                            .findFirst()
                            .orElse(null);
                    Assertions.assertNotNull(foundVisit2, "Nie znaleziono wizyty 2 (filtrowanie po ID)");
                    Assertions.assertNotNull(foundVisit2.getClient(), "Client w wizycie 2 nie powinien być null");
                    Assertions.assertEquals(secondClientEntity.getId(), foundVisit2.getClient().getId(), "Niezgodne ID klienta w wizycie 2");
                }
        );
    }

    @Test
    @Transactional
    void listByUsername() {
        //given
        Client client = new Client();
        client.setFirstName("Anna");

        User user = new User();
        user.setClient(client);
        user.setUsername("Anna");
        User createdUser = userService.create(user);

        Service service = new Service();
        service.setName("zabieg");
        Service createdService = serviceService.create(service);

        Visit firstVisit = new Visit();
        firstVisit.setService(createdService);
        firstVisit.setClient(createdUser.getClient());
        Visit createdFirstVisit = visitService.create(firstVisit);

        Visit secondVisit = new Visit();
        secondVisit.setService(createdService);
        secondVisit.setClient(createdUser.getClient());
        Visit createdSecondVisit = visitService.create(secondVisit);

        Visit thirdVisit = new Visit();
        thirdVisit.setService(createdService);
        Visit createdThirdVisit = visitService.create(thirdVisit);


        //when
        String userByUsername = user.getUsername();
        List<Visit> visitsByUsername = visitService.list(userByUsername);
        List<Visit> visits = visitService.list();

        //then
        Assertions.assertAll(
                ()->Assertions.assertNotNull(visitsByUsername, "visitsByUsername is null"),
                ()->Assertions.assertEquals(2, visitsByUsername.size(), "visitsByUsername isn't equals")
        );
    }

    @Test
    void create() {
        // given
        Visit visitToCreate = new Visit();
//        visitToCreate.setDueDate(LocalDateTime.now().plusHours(1));
        Client clientRef = new Client();
        clientRef.setId(clientEntity.getId());
        Service serviceRef = new Service();
        serviceRef.setId(serviceEntity.getId());
        Shop shopRef = new Shop();
        shopRef.setId(shopEntity.getId());
        visitToCreate.setClient(clientRef);
        visitToCreate.setService(serviceRef);
        visitToCreate.setShop(shopRef);
        long countBefore = visitRepository.count();

        // when
        Visit createdVisit = visitService.create(visitToCreate);

        // then
        Assertions.assertAll("Sprawdzenie zwróconego obiektu Visit po utworzeniu",
                () -> Assertions.assertNotNull(createdVisit, "Utworzona wizyta nie powinna być null"),
                () -> Assertions.assertNotNull(createdVisit.getId(), "ID utworzonej wizyty nie powinno być null"),
                () -> Assertions.assertEquals(visitToCreate.getDueDate(), createdVisit.getDueDate(), "Data wizyty (DueDate) niezgodna"),
                () -> Assertions.assertNotNull(createdVisit.getClient(), "Client w utworzonej wizycie nie powinien być null"),
                () -> Assertions.assertEquals(clientEntity.getId(), createdVisit.getClient().getId(), "Niezgodne ID klienta"),
                () -> Assertions.assertNotNull(createdVisit.getService(), "Service w utworzonej wizycie nie powinien być null"),
                () -> Assertions.assertEquals(serviceEntity.getId(), createdVisit.getService().getId(), "Niezgodne ID usługi"),
                () -> Assertions.assertNotNull(createdVisit.getShop(), "Shop w utworzonej wizycie nie powinien być null"),
                () -> Assertions.assertEquals(shopEntity.getId(), createdVisit.getShop().getId(), "Niezgodne ID sklepu")
        );

        Assertions.assertAll("Sprawdzenie stanu bazy danych po utworzeniu wizyty",
                () -> Assertions.assertEquals(countBefore + 1, visitRepository.count(), "Liczba wizyt w bazie danych niezgodna"),
                () -> {
                    Optional<VisitEntity> persistedEntityOpt = visitRepository.findById(createdVisit.getId());
                    Assertions.assertTrue(persistedEntityOpt.isPresent(), "Nie znaleziono zapisanej encji w bazie danych");
                    persistedEntityOpt.ifPresent(persistedEntity -> {
//                        Assertions.assertEquals(visitToCreate.getDueDate(), persistedEntity.getDueDate(), "DueDate w bazie niezgodne");
                        Assertions.assertNotNull(persistedEntity.getClient(), "Client w encji w bazie jest null");
                        Assertions.assertEquals(clientEntity.getId(), persistedEntity.getClient().getId(), "FK klienta w bazie niezgodne");
                        Assertions.assertNotNull(persistedEntity.getService(), "Service w encji w bazie jest null");
                        Assertions.assertEquals(serviceEntity.getId(), persistedEntity.getService().getId(), "FK usługi w bazie niezgodne");
                        Assertions.assertNotNull(persistedEntity.getShop(), "Shop w encji w bazie jest null");
                        Assertions.assertEquals(shopEntity.getId(), persistedEntity.getShop().getId(), "FK sklepu w bazie niezgodne");
                    });
                }
        );
    }

    @Test
    void createWithShop() {
        // given
        Client client = new Client();
        Client createdClient = clientService.create(client);

        Shop shop = new Shop();
        Shop createdShop = shopService.create(shop);

        Service service = new Service();
        Service createdService = serviceService.createWithShop(service, createdShop.getId());

        Visit visit = new Visit();
        visit.setService(createdService);

        // when
        Visit createdVisit = visitService.createWithShop(null, null);

        // then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(createdVisit, "Created visit is null")
        );
    }

    @Test
    void read() {
        // given
        VisitEntity visitEntityToRead = new VisitEntity();
//        visitEntityToRead.setDueDate(LocalDateTime.now().plusDays(5));
        visitEntityToRead.setClient(clientEntity);
        visitEntityToRead.setService(serviceEntity);
        visitEntityToRead.setShop(shopEntity);
        VisitEntity savedEntity = visitRepository.save(visitEntityToRead);
        Long existingId = savedEntity.getId();

        // when
        Visit foundVisit = visitService.read(existingId);

        // then
        Assertions.assertAll("Sprawdzenie wizyty odczytanej przez ID",
                () -> Assertions.assertNotNull(foundVisit, "Znaleziona wizyta nie powinna być null"),
                () -> Assertions.assertEquals(existingId, foundVisit.getId(), "ID odczytanej wizyty niezgodne"),
//                () -> Assertions.assertEquals(savedEntity.getDueDate(), foundVisit.getDueDate(), "DueDate odczytanej wizyty niezgodne"),
                () -> Assertions.assertNotNull(foundVisit.getClient(), "Client w odczytanej wizycie nie powinien być null"),
                () -> Assertions.assertEquals(clientEntity.getId(), foundVisit.getClient().getId(), "Niezgodne ID klienta w odczytanej wizycie"),
                () -> Assertions.assertNotNull(foundVisit.getService(), "Service w odczytanej wizycie nie powinien być null"),
                () -> Assertions.assertEquals(serviceEntity.getId(), foundVisit.getService().getId(), "Niezgodne ID usługi w odczytanej wizycie"),
                () -> Assertions.assertNotNull(foundVisit.getShop(), "Shop w odczytanej wizycie nie powinien być null"),
                () -> Assertions.assertEquals(shopEntity.getId(), foundVisit.getShop().getId(), "Niezgodne ID sklepu w odczytanej wizycie")
        );
    }

    @Test
    void readWithException() {
        // given
        Long nonExistentId = 9999L;
        Assertions.assertFalse(visitRepository.existsById(nonExistentId), "ID nie powinno istnieć przed testem");

        // when & then
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            visitService.read(nonExistentId);
        }, "Oczekiwano wyjątku NoSuchElementException dla nieistniejącego ID");
    }

    @Test
    void delete() {
        // given
        VisitEntity visitEntityToDelete = new VisitEntity();
//        visitEntityToDelete.setDueDate(LocalDateTime.now().plusDays(10));
        visitEntityToDelete.setClient(clientEntity);
        visitEntityToDelete.setService(serviceEntity);
        visitEntityToDelete.setShop(shopEntity);
        VisitEntity savedEntity = visitRepository.save(visitEntityToDelete);
        Long idToDelete = savedEntity.getId();
        long countBefore = visitRepository.count();
        Assertions.assertTrue(visitRepository.existsById(idToDelete), "Wizyta powinna istnieć przed usunięciem");

        // when
        visitService.delete(idToDelete);

        // then
        Assertions.assertAll("Sprawdzenie stanu bazy po usunięciu wizyty",
                () -> Assertions.assertFalse(visitRepository.existsById(idToDelete), "Wizyta nadal istnieje w bazie po usunięciu"),
                () -> Assertions.assertEquals(countBefore - 1, visitRepository.count(), "Liczba wizyt w bazie nie zmniejszyła się o 1")
        );
    }
}
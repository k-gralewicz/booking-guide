package pl.gralewicz.kamil.java.app.bookingguide.service;

// Importy JUnit i Mockito
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

// Importy Twoich klas
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Client;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Service;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Shop;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Visit; // Model DTO
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ClientEntity; // Encja
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ServiceEntity; // Encja
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ShopEntity;   // Encja
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.VisitEntity; // Encja
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.VisitRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.VisitMapper;

// Inne potrzebne importy
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

// Importy statyczne Mockito i JUnit
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Testy jednostkowe dla klasy VisitService.
 */
@ExtendWith(MockitoExtension.class)
class VisitServiceMockTest {

    @Mock
    private VisitRepository visitRepositoryMock;

    @Mock
    private VisitMapper visitMapperMock;

    @InjectMocks
    private VisitService visitService;

    // Captor do przechwytywania VisitEntity przekazanej do metody save
    @Captor
    private ArgumentCaptor<VisitEntity> visitEntityCaptor;

    private Visit visitDto1;
    private VisitEntity visitEntity1;
    private Visit visitDto2;
    private VisitEntity visitEntity2;

    @BeforeEach
    void setUp() {
        // Przygotowanie przykładowych danych dla wizyt
        // Zakładamy, że Visit DTO i VisitEntity mają referencje lub ID do Client, Service, Shop
        Client client1 = new Client(); client1.setId(1L);
        Service service1 = new Service(); service1.setId(10L); service1.setName("Strzyżenie");
        Shop shop1 = new Shop(); shop1.setId(100L); shop1.setName("Salon Fryzjerski A");

        ClientEntity clientEntity1 = new ClientEntity(); clientEntity1.setId(1L);
        ServiceEntity serviceEntity1 = new ServiceEntity(); serviceEntity1.setId(10L);
        ShopEntity shopEntity1 = new ShopEntity(); shopEntity1.setId(100L);

        visitDto1 = new Visit();
        visitDto1.setId(1L);
        visitDto1.setClient(client1); // Zakładamy, że DTO trzyma obiekty
        visitDto1.setService(service1);
        visitDto1.setShop(shop1);
        visitDto1.setDueDate(LocalDateTime.of(2025, 5, 10, 10, 0));

        visitEntity1 = new VisitEntity();
        visitEntity1.setId(1L);
        visitEntity1.setClient(clientEntity1); // Zakładamy, że Encja trzyma obiekty encji
        visitEntity1.setService(serviceEntity1);
        visitEntity1.setShop(shopEntity1);
        visitEntity1.setDueDate(LocalDateTime.of(2025, 5, 10, 10, 0));

        visitDto2 = new Visit(); // Druga wizyta dla testu listy
        visitDto2.setId(2L);
        visitDto2.setDueDate(LocalDateTime.of(2025, 5, 11, 11, 0));

        visitEntity2 = new VisitEntity();
        visitEntity2.setId(2L);
        visitEntity2.setDueDate(LocalDateTime.of(2025, 5, 11, 11, 0));
    }


    // --- Testy dla metody list() ---

    @Test
    void list() {
        // Given
        List<VisitEntity> entitiesFromRepo = List.of(visitEntity1, visitEntity2);
        List<Visit> expectedVisits = List.of(visitDto1, visitDto2);

        when(visitRepositoryMock.findAll()).thenReturn(entitiesFromRepo);
        when(visitMapperMock.fromEntities(entitiesFromRepo)).thenReturn(expectedVisits);

        // When
        List<Visit> actualVisits = visitService.list();

        // Then
        Assertions.assertAll("Sprawdzenie listy wizyt",
                () -> Assertions.assertNotNull(actualVisits, "Lista nie powinna być null"),
                () -> Assertions.assertEquals(2, actualVisits.size(), "Rozmiar listy"),
                () -> Assertions.assertEquals(expectedVisits.get(0).getId(), actualVisits.get(0).getId(), "ID pierwszej wizyty"),
                () -> Assertions.assertEquals(expectedVisits.get(1).getId(), actualVisits.get(1).getId(), "ID drugiej wizyty")
                // Można dodać więcej sprawdzeń pól, jeśli potrzeba
        );

        verify(visitRepositoryMock).findAll();
        verify(visitMapperMock).fromEntities(entitiesFromRepo);
        verifyNoMoreInteractions(visitRepositoryMock, visitMapperMock);
    }

    @Test
    void listEmpty() {
        // Given
        when(visitRepositoryMock.findAll()).thenReturn(Collections.emptyList());
        when(visitMapperMock.fromEntities(Collections.emptyList())).thenReturn(Collections.emptyList());

        // When
        List<Visit> actualVisits = visitService.list();

        // Then
        Assertions.assertAll("Sprawdzenie pustej listy wizyt",
                () -> Assertions.assertNotNull(actualVisits, "Lista nie powinna być null"),
                () -> Assertions.assertTrue(actualVisits.isEmpty(), "Lista powinna być pusta")
        );

        verify(visitRepositoryMock).findAll();
        verify(visitMapperMock).fromEntities(Collections.emptyList());
        verifyNoMoreInteractions(visitRepositoryMock, visitMapperMock);
    }

    // --- Testy dla metody create() ---

    @Test
    void create() {
        // Given
        // Użyjemy visitDto1 bez ID jako wejście
        Visit inputVisitDto = new Visit();
        inputVisitDto.setClient(visitDto1.getClient());
        inputVisitDto.setService(visitDto1.getService());
        inputVisitDto.setShop(visitDto1.getShop());
        inputVisitDto.setDueDate(visitDto1.getDueDate());

        // Encja przed zapisem (wynik mapowania DTO -> Entity)
        VisitEntity entityToSave = new VisitEntity();
        // Załóżmy, że mapper poprawnie mapuje obiekty DTO na referencje do encji lub ich ID
        // entityToSave.setClient(clientEntity1); // Jeśli mapper tak robi
        // entityToSave.setService(serviceEntity1);
        // entityToSave.setShop(shopEntity1);
        entityToSave.setDueDate(visitDto1.getDueDate());


        // Encja po zapisie (wynik repo.save) - użyjemy visitEntity1 z setup
        VisitEntity savedEntity = visitEntity1;

        // DTO wyjściowe (wynik mapowania Entity -> DTO) - użyjemy visitDto1 z setup
        Visit expectedOutputVisit = visitDto1;

        // Mockowanie
        when(visitMapperMock.from(inputVisitDto)).thenReturn(entityToSave);
        // Używamy any(), bo stan entityToSave może się różnić od tego co faktycznie trafi do save
        when(visitRepositoryMock.save(any(VisitEntity.class))).thenReturn(savedEntity);
        when(visitMapperMock.from(savedEntity)).thenReturn(expectedOutputVisit);

        // When
        Visit actualCreatedVisit = visitService.create(inputVisitDto);

        // Then
        Assertions.assertAll("Sprawdzenie utworzonej wizyty",
                () -> Assertions.assertNotNull(actualCreatedVisit, "Utworzona wizyta nie powinna być null"),
                () -> Assertions.assertEquals(expectedOutputVisit.getId(), actualCreatedVisit.getId(), "Niezgodne ID"),
                () -> Assertions.assertEquals(expectedOutputVisit.getDueDate(), actualCreatedVisit.getDueDate(), "Niezgodna data"),
                // Sprawdźmy też ID powiązanych obiektów, jeśli DTO je zawiera
                () -> Assertions.assertNotNull(actualCreatedVisit.getClient(), "Klient nie powinien być null"),
                () -> Assertions.assertEquals(expectedOutputVisit.getClient().getId(), actualCreatedVisit.getClient().getId(), "Niezgodne ID klienta"),
                () -> Assertions.assertNotNull(actualCreatedVisit.getService(), "Serwis nie powinien być null"),
                () -> Assertions.assertEquals(expectedOutputVisit.getService().getId(), actualCreatedVisit.getService().getId(), "Niezgodne ID serwisu"),
                () -> Assertions.assertNotNull(actualCreatedVisit.getShop(), "Sklep nie powinien być null"),
                () -> Assertions.assertEquals(expectedOutputVisit.getShop().getId(), actualCreatedVisit.getShop().getId(), "Niezgodne ID sklepu")

        );

        // Weryfikacja wywołań
        verify(visitMapperMock).from(inputVisitDto); // DTO -> Entity
        verify(visitRepositoryMock).save(visitEntityCaptor.capture()); // Przechwyć argument save
        verify(visitMapperMock).from(savedEntity); // Entity -> DTO

        // Weryfikacja encji przekazanej do zapisu
        VisitEntity capturedEntity = visitEntityCaptor.getValue();
        Assertions.assertNotNull(capturedEntity, "Przechwycona encja nie powinna być null");
        Assertions.assertEquals(inputVisitDto.getDueDate(), capturedEntity.getDueDate(), "Data w encji do zapisu");
        // Można dodać sprawdzenie powiązanych encji w capturedEntity, jeśli mapper je ustawia

        verifyNoMoreInteractions(visitRepositoryMock, visitMapperMock);
    }

    // --- Testy dla metody read() ---

    @Test
    void read() {
        // Given
        Long visitId = 1L;
        // Używamy obiektów z @BeforeEach
        when(visitRepositoryMock.findById(visitId)).thenReturn(Optional.of(visitEntity1));
        when(visitMapperMock.from(visitEntity1)).thenReturn(visitDto1);

        // When
        Visit actualVisit = visitService.read(visitId);

        // Then
        Assertions.assertAll("Sprawdzenie odczytanej wizyty",
                () -> Assertions.assertNotNull(actualVisit, "Zwrócona wizyta nie powinna być null"),
                () -> Assertions.assertEquals(visitDto1.getId(), actualVisit.getId(), "Niezgodne ID"),
                () -> Assertions.assertEquals(visitDto1.getDueDate(), actualVisit.getDueDate(), "Niezgodna data")
                // Dodaj więcej sprawdzeń pól, jeśli potrzeba
        );

        verify(visitRepositoryMock).findById(visitId);
        verify(visitMapperMock).from(visitEntity1);
        verifyNoMoreInteractions(visitRepositoryMock, visitMapperMock);
    }

    @Test
    void readWithException() {
        // Given
        Long nonExistentId = 99L;
        when(visitRepositoryMock.findById(nonExistentId)).thenReturn(Optional.empty());

        // When & Then
        // Kod serwisu read() jest poprawny i rzuca NoSuchElementException
        assertThrows(NoSuchElementException.class, () -> {
            visitService.read(nonExistentId);
        }, "Oczekiwano NoSuchElementException dla nieistniejącego ID wizyty");

        verify(visitRepositoryMock).findById(nonExistentId);
        verify(visitMapperMock, never()).from(any(VisitEntity.class)); // Mapper nie powinien być wywołany
        verifyNoMoreInteractions(visitRepositoryMock);
        verifyNoInteractions(visitMapperMock);
    }

    // --- Testy dla metody delete() ---

    @Test
    void delete() {
        // Given
        Long idToDelete = 10L;
        // Opcjonalnie: Można dodać when(visitRepositoryMock.existsById(idToDelete)).thenReturn(true);
        //              jeśli metoda delete w serwisie by to sprawdzała. Obecnie nie sprawdza.

        // When
        visitService.delete(idToDelete);

        // Then
        // Weryfikujemy tylko wywołanie metody repozytorium
        verify(visitRepositoryMock).deleteById(idToDelete);
        verifyNoMoreInteractions(visitRepositoryMock);
        verifyNoInteractions(visitMapperMock);
    }
}
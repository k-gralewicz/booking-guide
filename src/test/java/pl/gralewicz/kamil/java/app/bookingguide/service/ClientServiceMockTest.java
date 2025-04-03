package pl.gralewicz.kamil.java.app.bookingguide.service;

// Importy JUnit i Mockito
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

// Importy Twoich klas
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Client; // Model DTO
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ClientEntity; // Encja
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.ClientRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.ClientMapper;

// Inne potrzebne importy
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

// Importy statyczne Mockito i JUnit
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceMockTest {

    @Mock // Mock repozytorium
    private ClientRepository clientRepositoryMock;

    @Mock // Mock mappera
    private ClientMapper clientMapperMock;

    @InjectMocks // Testowana klasa serwisu z wstrzykniętymi mockami
    private ClientService clientService;

    // --- Testy dla metody list() ---

    @Test
    void list() {
        // Given - Przygotowanie danych i zachowania mocków
        ClientEntity entity1 = new ClientEntity(); entity1.setId(1L); entity1.setFirstName("Jan");
        ClientEntity entity2 = new ClientEntity(); entity2.setId(2L); entity2.setFirstName("Anna");
        List<ClientEntity> entitiesFromRepo = List.of(entity1, entity2);

        Client clientDto1 = new Client(); clientDto1.setId(1L); clientDto1.setFirstName("Jan");
        Client clientDto2 = new Client(); clientDto2.setId(2L); clientDto2.setFirstName("Anna");
        List<Client> expectedClients = List.of(clientDto1, clientDto2);

        when(clientRepositoryMock.findAll()).thenReturn(entitiesFromRepo);
        when(clientMapperMock.fromEntities(entitiesFromRepo)).thenReturn(expectedClients);

        // When - Wywołanie testowanej metody
        List<Client> actualClients = clientService.list();

        // Then - Asercje i weryfikacje
        Assertions.assertAll("Sprawdzenie listy klientów",
                () -> Assertions.assertNotNull(actualClients, "Lista nie powinna być null"),
                () -> Assertions.assertEquals(2, actualClients.size(), "Rozmiar listy"),
                // Zakładając stałą kolejność lub poprawny equals() w Client
                () -> Assertions.assertEquals(expectedClients.get(0).getId(), actualClients.get(0).getId(), "ID pierwszego klienta"),
                () -> Assertions.assertEquals(expectedClients.get(0).getFirstName(), actualClients.get(0).getFirstName(), "Imię pierwszego klienta"),
                () -> Assertions.assertEquals(expectedClients.get(1).getId(), actualClients.get(1).getId(), "ID drugiego klienta"),
                () -> Assertions.assertEquals(expectedClients.get(1).getFirstName(), actualClients.get(1).getFirstName(), "Imię drugiego klienta")
        );

        verify(clientRepositoryMock).findAll();
        verify(clientMapperMock).fromEntities(entitiesFromRepo);
        verifyNoMoreInteractions(clientRepositoryMock, clientMapperMock);
    }

    @Test
    void listEmpty() {
        // Given
        when(clientRepositoryMock.findAll()).thenReturn(Collections.emptyList());
        when(clientMapperMock.fromEntities(Collections.emptyList())).thenReturn(Collections.emptyList());

        // When
        List<Client> actualClients = clientService.list();

        // Then
        Assertions.assertAll("Sprawdzenie pustej listy klientów",
                () -> Assertions.assertNotNull(actualClients, "Lista nie powinna być null"),
                () -> Assertions.assertTrue(actualClients.isEmpty(), "Lista powinna być pusta")
        );

        verify(clientRepositoryMock).findAll();
        verify(clientMapperMock).fromEntities(Collections.emptyList());
        verifyNoMoreInteractions(clientRepositoryMock, clientMapperMock);
    }

    // --- Testy dla metody create() ---

    @Test
    void create() {
        // Given
        Client inputClient = new Client(); // DTO wejściowe
        inputClient.setFirstName("Nowy");
        inputClient.setLastName("Klient");
        inputClient.setEmail("nowy@test.pl");

        ClientEntity entityToSave = new ClientEntity(); // Encja przed zapisem (wynik mapowania DTO->Entity)
        ClientEntity savedEntity = new ClientEntity(); // Encja po zapisie (wynik repo.save)
        savedEntity.setId(1L);
        savedEntity.setFirstName("Nowy");
        savedEntity.setLastName("Klient");
        savedEntity.setEmail("nowy@test.pl");

        Client expectedOutputClient = new Client(); // DTO wyjściowe (wynik mapowania Entity->DTO)
        expectedOutputClient.setId(1L);
        expectedOutputClient.setFirstName("Nowy");
        expectedOutputClient.setLastName("Klient");
        expectedOutputClient.setEmail("nowy@test.pl");

        when(clientMapperMock.from(inputClient)).thenReturn(entityToSave);
        when(clientRepositoryMock.save(entityToSave)).thenReturn(savedEntity);
        when(clientMapperMock.from(savedEntity)).thenReturn(expectedOutputClient);

        // When
        Client actualCreatedClient = clientService.create(inputClient);

        // Then
        Assertions.assertAll("Sprawdzenie utworzonego klienta",
                () -> Assertions.assertNotNull(actualCreatedClient, "Utworzony klient nie powinien być null"),
                () -> Assertions.assertEquals(expectedOutputClient.getId(), actualCreatedClient.getId(), "Niezgodne ID"),
                () -> Assertions.assertEquals(expectedOutputClient.getFirstName(), actualCreatedClient.getFirstName(), "Niezgodne Imię"),
                () -> Assertions.assertEquals(expectedOutputClient.getLastName(), actualCreatedClient.getLastName(), "Niezgodne Nazwisko"),
                () -> Assertions.assertEquals(expectedOutputClient.getEmail(), actualCreatedClient.getEmail(), "Niezgodny Email")
        );

        verify(clientMapperMock).from(inputClient);
        verify(clientRepositoryMock).save(entityToSave);
        verify(clientMapperMock).from(savedEntity);
        verifyNoMoreInteractions(clientRepositoryMock, clientMapperMock);
    }

    // --- Testy dla metody read() ---

    @Test
    void read() {
        // Given
        Long clientId = 5L;
        ClientEntity foundEntity = new ClientEntity();
        foundEntity.setId(clientId);
        foundEntity.setFirstName("Znaleziony");
        foundEntity.setEmail("found@test.com");

        Client expectedClientDto = new Client(); // Oczekiwany wynik DTO
        expectedClientDto.setId(clientId);
        expectedClientDto.setFirstName("Znaleziony");
        expectedClientDto.setEmail("found@test.com");

        when(clientRepositoryMock.findById(clientId)).thenReturn(Optional.of(foundEntity));
        when(clientMapperMock.from(foundEntity)).thenReturn(expectedClientDto);

        // When
        Client actualClient = clientService.read(clientId);

        // Then
        Assertions.assertAll("Sprawdzenie odczytanego klienta",
                () -> Assertions.assertNotNull(actualClient, "Zwrócony klient nie powinien być null"),
                () -> Assertions.assertEquals(expectedClientDto.getId(), actualClient.getId(), "Niezgodne ID"),
                () -> Assertions.assertEquals(expectedClientDto.getFirstName(), actualClient.getFirstName(), "Niezgodne Imię"),
                () -> Assertions.assertEquals(expectedClientDto.getEmail(), actualClient.getEmail(), "Niezgodny Email")
        );

        verify(clientRepositoryMock).findById(clientId);
        verify(clientMapperMock).from(foundEntity);
        verifyNoMoreInteractions(clientRepositoryMock, clientMapperMock);
    }

    @Test
    void readWithException() {
        // Given
        Long nonExistentId = 99L;
        when(clientRepositoryMock.findById(nonExistentId)).thenReturn(Optional.empty());

        // When & Then
        // Sprawdzamy, czy zostanie rzucony wyjątek NoSuchElementException
        assertThrows(NoSuchElementException.class, () -> {
            clientService.read(nonExistentId);
        }, "Oczekiwano NoSuchElementException dla nieistniejącego ID klienta");

        verify(clientRepositoryMock).findById(nonExistentId);
        verify(clientMapperMock, never()).from(any(ClientEntity.class)); // Mapper nie powinien być wywołany
        verifyNoMoreInteractions(clientRepositoryMock);
        verifyNoInteractions(clientMapperMock);
    }

    // --- Testy dla metody delete() ---

    @Test
    void delete() {
        // Given
        Long idToDelete = 10L;
        // Nie potrzebujemy when() dla metody void deleteById w repo

        // When
        clientService.delete(idToDelete);

        // Then
        // Weryfikujemy tylko, czy metoda repozytorium została wywołana
        verify(clientRepositoryMock).deleteById(idToDelete);
        verifyNoMoreInteractions(clientRepositoryMock);
        verifyNoInteractions(clientMapperMock);
    }
}
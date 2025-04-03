package pl.gralewicz.kamil.java.app.bookingguide.service;

// Importy JUnit i Mockito
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

// Importy Twoich klas
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.DurationType; // Potrzebny enum
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Service; // Model DTO
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ServiceEntity; // Encja
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.ServiceRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.ServiceMapper;

// Inne potrzebne importy
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

// Importy statyczne Mockito i JUnit
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Testy jednostkowe dla klasy ServiceService.
 */
@ExtendWith(MockitoExtension.class)
class ServiceServiceMockTest {

    @Mock
    private ServiceRepository serviceRepositoryMock;

    @Mock
    private ServiceMapper serviceMapperMock;

    @InjectMocks
    private ServiceService serviceService;

    // --- Testy dla metody list() ---

    @Test
    void list() {
        // Given
        ServiceEntity entity1 = new ServiceEntity(); entity1.setId(1L); entity1.setName("Service A");
        ServiceEntity entity2 = new ServiceEntity(); entity2.setId(2L); entity2.setName("Service B");
        List<ServiceEntity> entitiesFromRepo = List.of(entity1, entity2);

        Service serviceDto1 = new Service(); serviceDto1.setId(1L); serviceDto1.setName("Service A");
        Service serviceDto2 = new Service(); serviceDto2.setId(2L); serviceDto2.setName("Service B");
        List<Service> expectedServices = List.of(serviceDto1, serviceDto2);

        when(serviceRepositoryMock.findAll()).thenReturn(entitiesFromRepo);
        when(serviceMapperMock.fromEntities(entitiesFromRepo)).thenReturn(expectedServices); // Serwis używa fromEntities

        // When
        List<Service> actualServices = serviceService.list();

        // Then
        Assertions.assertAll("Sprawdzenie listy usług",
                () -> Assertions.assertNotNull(actualServices, "Lista nie powinna być null"),
                () -> Assertions.assertEquals(2, actualServices.size(), "Rozmiar listy"),
                () -> Assertions.assertEquals(expectedServices.get(0).getId(), actualServices.get(0).getId(), "ID pierwszej usługi"),
                () -> Assertions.assertEquals(expectedServices.get(0).getName(), actualServices.get(0).getName(), "Nazwa pierwszej usługi"),
                () -> Assertions.assertEquals(expectedServices.get(1).getId(), actualServices.get(1).getId(), "ID drugiej usługi"),
                () -> Assertions.assertEquals(expectedServices.get(1).getName(), actualServices.get(1).getName(), "Nazwa drugiej usługi")
                // Można użyć Assertions.assertEquals(expectedServices, actualServices), jeśli Service ma equals()
        );

        verify(serviceRepositoryMock).findAll();
        verify(serviceMapperMock).fromEntities(entitiesFromRepo); // Weryfikujemy wywołanie fromEntities
        verifyNoMoreInteractions(serviceRepositoryMock, serviceMapperMock);
    }

    @Test
    void listEmpty() {
        // Given
        when(serviceRepositoryMock.findAll()).thenReturn(Collections.emptyList());
        when(serviceMapperMock.fromEntities(Collections.emptyList())).thenReturn(Collections.emptyList());

        // When
        List<Service> actualServices = serviceService.list();

        // Then
        Assertions.assertAll("Sprawdzenie pustej listy usług",
                () -> Assertions.assertNotNull(actualServices, "Lista nie powinna być null"),
                () -> Assertions.assertTrue(actualServices.isEmpty(), "Lista powinna być pusta")
        );

        verify(serviceRepositoryMock).findAll();
        verify(serviceMapperMock).fromEntities(Collections.emptyList());
        verifyNoMoreInteractions(serviceRepositoryMock, serviceMapperMock);
    }

    // --- Testy dla metody create() ---

    @Test
    void create() {
        // Given
        Service inputService = new Service(); // DTO wejściowe
        inputService.setName("Nowa Usługa");
        inputService.setPrice(BigDecimal.valueOf(100));
        inputService.setDuration(30);
        inputService.setDurationType(DurationType.MINUTES);

        ServiceEntity entityToSave = new ServiceEntity(); // Encja przed zapisem
        ServiceEntity savedEntity = new ServiceEntity(); // Encja po zapisie
        savedEntity.setId(1L);
        savedEntity.setName("Nowa Usługa");
        savedEntity.setPrice(BigDecimal.valueOf(100));
        savedEntity.setDuration(30);
        savedEntity.setDurationType(DurationType.MINUTES);


        Service expectedOutputService = new Service(); // DTO wyjściowe
        expectedOutputService.setId(1L);
        expectedOutputService.setName("Nowa Usługa");
        expectedOutputService.setPrice(BigDecimal.valueOf(100));
        expectedOutputService.setDuration(30);
        expectedOutputService.setDurationType(DurationType.MINUTES);


        when(serviceMapperMock.from(inputService)).thenReturn(entityToSave);
        when(serviceRepositoryMock.save(entityToSave)).thenReturn(savedEntity);
        when(serviceMapperMock.from(savedEntity)).thenReturn(expectedOutputService);

        // When
        Service actualCreatedService = serviceService.create(inputService);

        // Then
        Assertions.assertAll("Sprawdzenie utworzonej usługi",
                () -> Assertions.assertNotNull(actualCreatedService, "Utworzona usługa nie powinna być null"),
                () -> Assertions.assertEquals(expectedOutputService.getId(), actualCreatedService.getId(), "Niezgodne ID"),
                () -> Assertions.assertEquals(expectedOutputService.getName(), actualCreatedService.getName(), "Niezgodna nazwa"),
                () -> Assertions.assertEquals(expectedOutputService.getPrice(), actualCreatedService.getPrice(), "Niezgodna cena"),
                () -> Assertions.assertEquals(expectedOutputService.getDuration(), actualCreatedService.getDuration(), "Niezgodny czas trwania"),
                () -> Assertions.assertEquals(expectedOutputService.getDurationType(), actualCreatedService.getDurationType(), "Niezgodny typ czasu trwania")
        );

        verify(serviceMapperMock).from(inputService);
        verify(serviceRepositoryMock).save(entityToSave);
        verify(serviceMapperMock).from(savedEntity);
        verifyNoMoreInteractions(serviceRepositoryMock, serviceMapperMock);
    }

    // --- Testy dla metody findById() ---

    @Test
    void findById() {
        // Given
        Long serviceId = 5L;
        ServiceEntity foundEntity = new ServiceEntity();
        foundEntity.setId(serviceId);
        foundEntity.setName("Znaleziona Usługa");

        Service expectedServiceDto = new Service();
        expectedServiceDto.setId(serviceId);
        expectedServiceDto.setName("Znaleziona Usługa");

        when(serviceRepositoryMock.findById(serviceId)).thenReturn(Optional.of(foundEntity));
        when(serviceMapperMock.from(foundEntity)).thenReturn(expectedServiceDto);

        // When
        Service actualService = serviceService.findById(serviceId);

        // Then
        Assertions.assertAll("Sprawdzenie usługi znalezionej przez findById",
                () -> Assertions.assertNotNull(actualService, "Zwrócona usługa nie powinna być null"),
                () -> Assertions.assertEquals(expectedServiceDto.getId(), actualService.getId(), "Niezgodne ID"),
                () -> Assertions.assertEquals(expectedServiceDto.getName(), actualService.getName(), "Niezgodna nazwa")
        );

        verify(serviceRepositoryMock).findById(serviceId);
        verify(serviceMapperMock).from(foundEntity);
        verifyNoMoreInteractions(serviceRepositoryMock, serviceMapperMock);
    }

    @Test
    void findByIdWithException() {
        // Given
        Long nonExistentId = 99L;
        when(serviceRepositoryMock.findById(nonExistentId)).thenReturn(Optional.empty());

        // When & Then
        // Metoda w serwisie używa orElseThrow() bez argumentu, co rzuca NoSuchElementException
        assertThrows(NoSuchElementException.class, () -> {
            serviceService.findById(nonExistentId);
        }, "Oczekiwano NoSuchElementException dla nieistniejącego ID usługi (findById)");

        verify(serviceRepositoryMock).findById(nonExistentId);
        verify(serviceMapperMock, never()).from(any(ServiceEntity.class));
        verifyNoMoreInteractions(serviceRepositoryMock);
        verifyNoInteractions(serviceMapperMock);
    }


    // --- Testy dla metody read() ---
    // Logika identyczna jak dla findById, ponieważ metody w serwisie są identyczne

    @Test
    void read() {
        // Given
        Long serviceId = 6L; // Inne ID dla pewności
        ServiceEntity foundEntity = new ServiceEntity();
        foundEntity.setId(serviceId);
        foundEntity.setName("Odczytana Usługa");

        Service expectedServiceDto = new Service();
        expectedServiceDto.setId(serviceId);
        expectedServiceDto.setName("Odczytana Usługa");

        when(serviceRepositoryMock.findById(serviceId)).thenReturn(Optional.of(foundEntity));
        when(serviceMapperMock.from(foundEntity)).thenReturn(expectedServiceDto);

        // When
        Service actualService = serviceService.read(serviceId);

        // Then
        Assertions.assertAll("Sprawdzenie usługi odczytanej przez read",
                () -> Assertions.assertNotNull(actualService, "Zwrócona usługa nie powinna być null"),
                () -> Assertions.assertEquals(expectedServiceDto.getId(), actualService.getId(), "Niezgodne ID"),
                () -> Assertions.assertEquals(expectedServiceDto.getName(), actualService.getName(), "Niezgodna nazwa")
        );

        verify(serviceRepositoryMock).findById(serviceId);
        verify(serviceMapperMock).from(foundEntity);
        verifyNoMoreInteractions(serviceRepositoryMock, serviceMapperMock);
    }

    @Test
    void readWithException() {
        // Given
        Long nonExistentId = 199L; // Inne ID dla pewności
        when(serviceRepositoryMock.findById(nonExistentId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NoSuchElementException.class, () -> {
            serviceService.read(nonExistentId);
        }, "Oczekiwano NoSuchElementException dla nieistniejącego ID usługi (read)");

        verify(serviceRepositoryMock).findById(nonExistentId);
        verify(serviceMapperMock, never()).from(any(ServiceEntity.class));
        verifyNoMoreInteractions(serviceRepositoryMock);
        verifyNoInteractions(serviceMapperMock);
    }

    // --- Testy dla metody delete() ---

    @Test
    void delete() {
        // Given
        Long idToDelete = 15L;

        // When
        serviceService.delete(idToDelete);

        // Then
        // Weryfikujemy tylko wywołanie metody repozytorium
        verify(serviceRepositoryMock).deleteById(idToDelete);
        verifyNoMoreInteractions(serviceRepositoryMock);
        verifyNoInteractions(serviceMapperMock);
    }
}
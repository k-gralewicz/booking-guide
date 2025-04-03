package pl.gralewicz.kamil.java.app.bookingguide.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Shop; // Model DTO
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ShopEntity; // Encja
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.ShopRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.ShopMapper;
import java.util.Collections; // Import dla Collections.emptyList()
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ShopServiceMockTest {

    @Mock
    private ShopRepository shopRepositoryMock;

    @Mock
    private ShopMapper shopMapperMock;

    @InjectMocks
    private ShopService shopService;

    @Test
    void list() {
        // Given - Przygotowanie danych i mocków dla poprawionej metody list()
        ShopEntity entity1 = new ShopEntity(); entity1.setId(1L); entity1.setName("Sklep A");
        ShopEntity entity2 = new ShopEntity(); entity2.setId(2L); entity2.setName("Sklep B");
        List<ShopEntity> entitiesFromRepo = List.of(entity1, entity2);

        Shop shopDto1 = new Shop(); shopDto1.setId(1L); shopDto1.setName("Sklep A");
        Shop shopDto2 = new Shop(); shopDto2.setId(2L); shopDto2.setName("Sklep B");
        List<Shop> expectedShops = List.of(shopDto1, shopDto2);

        // Mockujemy repozytorium i mapper
        when(shopRepositoryMock.findAll()).thenReturn(entitiesFromRepo);
        when(shopMapperMock.fromEntities(entitiesFromRepo)).thenReturn(expectedShops); // Zakładając użycie fromEntities

        // When
        List<Shop> actualShops = shopService.list();

        // Then
        // Weryfikujemy wynik zwrócony przez zamockowany mapper
        Assertions.assertAll("Sprawdzenie listy sklepów z repozytorium",
                () -> Assertions.assertNotNull(actualShops, "Lista nie powinna być null"),
                () -> Assertions.assertEquals(2, actualShops.size(), "Rozmiar listy"),
                () -> Assertions.assertEquals(expectedShops.get(0).getId(), actualShops.get(0).getId(), "ID pierwszego sklepu"),
                () -> Assertions.assertEquals(expectedShops.get(0).getName(), actualShops.get(0).getName(), "Nazwa pierwszego sklepu"),
                () -> Assertions.assertEquals(expectedShops.get(1).getId(), actualShops.get(1).getId(), "ID drugiego sklepu"),
                () -> Assertions.assertEquals(expectedShops.get(1).getName(), actualShops.get(1).getName(), "Nazwa drugiego sklepu")
        );

        // Weryfikujemy, że repozytorium i mapper zostały użyte
        verify(shopRepositoryMock).findAll();
        verify(shopMapperMock).fromEntities(entitiesFromRepo); // Weryfikujemy wywołanie fromEntities
        verifyNoMoreInteractions(shopRepositoryMock, shopMapperMock);
    }

    @Test
    void listEmpty() {
        // Given
        when(shopRepositoryMock.findAll()).thenReturn(Collections.emptyList());
        when(shopMapperMock.fromEntities(Collections.emptyList())).thenReturn(Collections.emptyList()); // Mock dla pustej listy

        // When
        List<Shop> actualShops = shopService.list();

        // Then
        Assertions.assertAll("Sprawdzenie pustej listy sklepów",
                () -> Assertions.assertNotNull(actualShops, "Lista nie powinna być null"),
                () -> Assertions.assertTrue(actualShops.isEmpty(), "Lista powinna być pusta")
        );

        verify(shopRepositoryMock).findAll();
        verify(shopMapperMock).fromEntities(Collections.emptyList()); // Weryfikujemy wywołanie mappera
        verifyNoMoreInteractions(shopRepositoryMock, shopMapperMock);
    }

    // --- Testy dla metody create() (bez zmian) ---

    @Test
    void create() {
        // Given
        Shop inputShop = new Shop();
        inputShop.setName("Nowy Sklep");
        inputShop.setDescription("Opis nowego sklepu");

        ShopEntity entityToSave = new ShopEntity();
        ShopEntity savedEntity = new ShopEntity();
        savedEntity.setId(1L);
        savedEntity.setName("Nowy Sklep");
        savedEntity.setDescription("Opis nowego sklepu");

        Shop expectedOutputShop = new Shop();
        expectedOutputShop.setId(1L);
        expectedOutputShop.setName("Nowy Sklep");
        expectedOutputShop.setDescription("Opis nowego sklepu");

        when(shopMapperMock.from(inputShop)).thenReturn(entityToSave);
        when(shopRepositoryMock.save(entityToSave)).thenReturn(savedEntity);
        when(shopMapperMock.from(savedEntity)).thenReturn(expectedOutputShop);

        // When
        Shop actualCreatedShop = shopService.create(inputShop);

        // Then
        Assertions.assertAll("Sprawdzenie utworzonego sklepu",
                () -> Assertions.assertNotNull(actualCreatedShop, "Utworzony sklep nie powinien być null"),
                () -> Assertions.assertEquals(expectedOutputShop.getId(), actualCreatedShop.getId(), "Niezgodne ID"),
                () -> Assertions.assertEquals(expectedOutputShop.getName(), actualCreatedShop.getName(), "Niezgodna nazwa"),
                () -> Assertions.assertEquals(expectedOutputShop.getDescription(), actualCreatedShop.getDescription(), "Niezgodny opis")
        );

        verify(shopMapperMock).from(inputShop);
        verify(shopRepositoryMock).save(entityToSave);
        verify(shopMapperMock).from(savedEntity);
        verifyNoMoreInteractions(shopRepositoryMock, shopMapperMock);
    }

    // --- Testy dla metody findById() (bez zmian) ---

    @Test
    void findById() {
        // Given
        Long shopId = 5L;
        ShopEntity foundEntity = new ShopEntity();
        foundEntity.setId(shopId);
        foundEntity.setName("Znaleziony Sklep");

        Shop expectedShopDto = new Shop();
        expectedShopDto.setId(shopId);
        expectedShopDto.setName("Znaleziony Sklep");

        when(shopRepositoryMock.findById(shopId)).thenReturn(Optional.of(foundEntity));
        when(shopMapperMock.from(foundEntity)).thenReturn(expectedShopDto);

        // When
        Shop actualShop = shopService.findById(shopId);

        // Then
        Assertions.assertAll("Sprawdzenie sklepu znalezionego przez findById",
                () -> Assertions.assertNotNull(actualShop, "Zwrócony sklep nie powinien być null"),
                () -> Assertions.assertEquals(expectedShopDto.getId(), actualShop.getId(), "Niezgodne ID"),
                () -> Assertions.assertEquals(expectedShopDto.getName(), actualShop.getName(), "Niezgodna nazwa")
        );

        verify(shopRepositoryMock).findById(shopId);
        verify(shopMapperMock).from(foundEntity);
        verifyNoMoreInteractions(shopRepositoryMock, shopMapperMock);
    }

    @Test
    void findByIdWithException() {
        // Given
        Long nonExistentId = 99L;
        when(shopRepositoryMock.findById(nonExistentId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NoSuchElementException.class, () -> {
            shopService.findById(nonExistentId);
        }, "Oczekiwano NoSuchElementException dla nieistniejącego ID sklepu (findById)");

        verify(shopRepositoryMock).findById(nonExistentId);
        verify(shopMapperMock, never()).from(any(ShopEntity.class));
        verifyNoMoreInteractions(shopRepositoryMock);
        verifyNoInteractions(shopMapperMock);
    }

    // --- Testy dla metody read() (bez zmian) ---

    @Test
    void read() {
        // Given
        Long shopId = 6L;
        ShopEntity foundEntity = new ShopEntity();
        foundEntity.setId(shopId);
        foundEntity.setName("Odczytany Sklep");

        Shop expectedShopDto = new Shop();
        expectedShopDto.setId(shopId);
        expectedShopDto.setName("Odczytany Sklep");

        when(shopRepositoryMock.findById(shopId)).thenReturn(Optional.of(foundEntity));
        when(shopMapperMock.from(foundEntity)).thenReturn(expectedShopDto);

        // When
        Shop actualShop = shopService.read(shopId);

        // Then
        Assertions.assertAll("Sprawdzenie sklepu odczytanego przez read",
                () -> Assertions.assertNotNull(actualShop, "Zwrócony sklep nie powinien być null"),
                () -> Assertions.assertEquals(expectedShopDto.getId(), actualShop.getId(), "Niezgodne ID"),
                () -> Assertions.assertEquals(expectedShopDto.getName(), actualShop.getName(), "Niezgodna nazwa")
        );

        verify(shopRepositoryMock).findById(shopId);
        verify(shopMapperMock).from(foundEntity);
        verifyNoMoreInteractions(shopRepositoryMock, shopMapperMock);
    }

    @Test
    void readWithException() {
        // Given
        Long nonExistentId = 199L;
        when(shopRepositoryMock.findById(nonExistentId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NoSuchElementException.class, () -> {
            shopService.read(nonExistentId);
        }, "Oczekiwano NoSuchElementException dla nieistniejącego ID sklepu (read)");

        verify(shopRepositoryMock).findById(nonExistentId);
        verify(shopMapperMock, never()).from(any(ShopEntity.class));
        verifyNoMoreInteractions(shopRepositoryMock);
        verifyNoInteractions(shopMapperMock);
    }

    // --- Testy dla metody delete() (bez zmian w logice, tylko dodano drugi test) ---

    @Test
    void delete() {
        // Given
        Long idToDelete = 15L;
        // Mockujemy existsById, bo poprawiona metoda delete w serwisie go używa
        when(shopRepositoryMock.existsById(idToDelete)).thenReturn(true);

        // When
        shopService.delete(idToDelete);

        // Then
        verify(shopRepositoryMock).existsById(idToDelete); // Sprawdź, czy istnienie było sprawdzane
        verify(shopRepositoryMock).deleteById(idToDelete); // Sprawdź, czy usunięcie było wywołane
        verifyNoMoreInteractions(shopRepositoryMock);
        verifyNoInteractions(shopMapperMock);
    }

    @Test
    void deleteWhenNotFound() {
        // Given
        Long idToDelete = 16L;
        when(shopRepositoryMock.existsById(idToDelete)).thenReturn(false); // Symulujemy brak encji

        // When
        shopService.delete(idToDelete);

        // Then
        verify(shopRepositoryMock).existsById(idToDelete); // Sprawdzono istnienie
        verify(shopRepositoryMock, never()).deleteById(idToDelete); // Usunięcie NIE zostało wywołane
        verifyNoMoreInteractions(shopRepositoryMock);
        verifyNoInteractions(shopMapperMock);
    }
}
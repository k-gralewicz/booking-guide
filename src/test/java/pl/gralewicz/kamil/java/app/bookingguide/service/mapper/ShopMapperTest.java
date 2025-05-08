package pl.gralewicz.kamil.java.app.bookingguide.service.mapper;

// Importy JUnit

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Shop;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ShopEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testy jednostkowe dla klasy ShopMapper.
 * Testuje rzeczywiste mapowanie za pomocą instancji ModelMapper.
 */
class ShopMapperTest {

    private ModelMapper modelMapper; // Prawdziwa instancja ModelMapper
    private ShopMapper shopMapper;   // Mapper, który testujemy

    @BeforeEach // Metoda setup uruchamiana przed każdym testem
    void setUp() {
        // Tworzymy nową, czystą instancję ModelMapper dla każdego testu
        modelMapper = new ModelMapper();
        // Tutaj możesz dodać specyficzną konfigurację dla modelMapper, jeśli jest potrzebna
        // np. dla mapowania zagnieżdżonych obiektów Address, jeśli by były:
        // modelMapper.addMappings(new PropertyMap<Shop, ShopEntity>() {
        //     protected void configure() {
        //         map().getAddress().setStreet(source.getAddress().getStreetName()); // Przykład
        //     }
        // });

        // Tworzymy instancję ShopMapper, przekazując prawdziwy ModelMapper
//        shopMapper = new ShopMapper(modelMapper);
    }

    // === Test dla metody from(Shop shop) DTO -> Entity ===
    @Test
    void fromDtoToEntity() {
        // Given
        Shop shopDto = new Shop();
        shopDto.setId(10L); // ID może być null przy tworzeniu, ale testujmy mapowanie
        shopDto.setName("Sklep DTO");
        shopDto.setDescription("Opis z DTO");
        shopDto.setPhoneNumber("123456789");
        // Można dodać Address, jeśli jest w modelu

        // When
        ShopEntity shopEntity = shopMapper.from(shopDto);

        // Then
        assertNotNull(shopEntity, "Encja nie powinna być null");
        // Sprawdzamy, czy pola zostały poprawnie zmapowane przez ModelMapper
        // (Zakładając, że nazwy pól w DTO i Encji są zgodne)
        assertAll("Sprawdzenie mapowania DTO -> Entity",
                () -> assertEquals(shopDto.getId(), shopEntity.getId(), "ID niezgodne"),
                () -> assertEquals(shopDto.getName(), shopEntity.getName(), "Nazwa niezgodna"),
                () -> assertEquals(shopDto.getDescription(), shopEntity.getDescription(), "Opis niezgodny"),
                () -> assertEquals(shopDto.getPhoneNumber(), shopEntity.getPhoneNumber(), "Nr telefonu niezgodny")
                // Dodaj asercje dla Address, jeśli istnieje
        );
    }

    // === Test dla metody from(ShopEntity shopEntity) Entity -> DTO ===
    @Test
    void fromEntityToDto() {
        // Given
        ShopEntity shopEntity = new ShopEntity();
        shopEntity.setId(20L);
        shopEntity.setName("Sklep Encja");
        shopEntity.setDescription("Opis z Encji");
        shopEntity.setPhoneNumber("987654321");
        // Można dodać AddressEntity, jeśli jest w modelu

        // When
        Shop shopDto = shopMapper.from(shopEntity);

        // Then
        assertNotNull(shopDto, "DTO nie powinien być null");
        assertAll("Sprawdzenie mapowania Entity -> DTO",
                () -> assertEquals(shopEntity.getId(), shopDto.getId(), "ID niezgodne"),
                () -> assertEquals(shopEntity.getName(), shopDto.getName(), "Nazwa niezgodna"),
                () -> assertEquals(shopEntity.getDescription(), shopDto.getDescription(), "Opis niezgodny"),
                () -> assertEquals(shopEntity.getPhoneNumber(), shopDto.getPhoneNumber(), "Nr telefonu niezgodny")
                // Dodaj asercje dla Address, jeśli istnieje
        );
        // Sprawdź też, czy pole 'visits' w DTO jest puste/null, zgodnie z oczekiwaniami
        // assertTrue(shopDto.getVisits() == null || shopDto.getVisits().isEmpty(), "Lista visits w DTO powinna być pusta");
    }

    // === Test dla metody fromEntities(List<ShopEntity> entities) ===
    @Test
    void fromEntities() {
        // Given
        ShopEntity entity1 = new ShopEntity(); entity1.setId(1L); entity1.setName("E1");
        ShopEntity entity2 = new ShopEntity(); entity2.setId(2L); entity2.setName("E2");
        List<ShopEntity> entityList = List.of(entity1, entity2);

        // When
        List<Shop> dtoList = shopMapper.fromEntities(entityList);

        // Then
        assertNotNull(dtoList);
        assertAll("Sprawdzenie mapowania listy encji",
                () -> assertEquals(2, dtoList.size(), "Rozmiar listy DTO"),
                () -> assertEquals(entity1.getId(), dtoList.get(0).getId(), "ID pierwszego DTO"),
                () -> assertEquals(entity1.getName(), dtoList.get(0).getName(), "Nazwa pierwszego DTO"),
                () -> assertEquals(entity2.getId(), dtoList.get(1).getId(), "ID drugiego DTO"),
                () -> assertEquals(entity2.getName(), dtoList.get(1).getName(), "Nazwa drugiego DTO")
        );
    }

    @Test
    void fromEntitiesHandlesNullInput() {
        // Given
        List<ShopEntity> entityList = null;

        // When
        List<Shop> dtoList = shopMapper.fromEntities(entityList);

        // Then
        assertNotNull(dtoList, "Lista nie powinna być null nawet dla wejścia null");
        assertTrue(dtoList.isEmpty(), "Lista powinna być pusta dla wejścia null");
    }

    @Test
    void fromEntitiesHandlesEmptyInput() {
        // Given
        List<ShopEntity> entityList = Collections.emptyList();

        // When
        List<Shop> dtoList = shopMapper.fromEntities(entityList);

        // Then
        assertNotNull(dtoList, "Lista nie powinna być null dla pustego wejścia");
        assertTrue(dtoList.isEmpty(), "Lista powinna być pusta dla pustego wejścia");
    }

    // Można dodać testy sprawdzające mapowanie null dla DTO -> Entity i Entity -> DTO
    @Test
    void fromDtoToEntityHandlesNullInput() {
        // Given
        Shop shopDto = null;
        // When
        // W obecnej implementacji ModelMapper rzuci IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            shopMapper.from(shopDto);
        }, "Oczekiwano IllegalArgumentException dla wejścia DTO null");
        // Alternatywnie, jeśli chcesz, aby mapper zwracał null:
        // ShopEntity shopEntity = shopMapper.from(shopDto);
        // assertNull(shopEntity);
        // Wymagałoby to zmiany w ShopMapper.from(Shop), np. dodania warunku if (shop == null) return null;
    }

    @Test
    void fromEntityToDtoHandlesNullInput() {
        // Given
        ShopEntity shopEntity = null;
        // When
        // W obecnej implementacji ModelMapper rzuci IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            shopMapper.from(shopEntity);
        }, "Oczekiwano IllegalArgumentException dla wejścia Entity null");
        // Alternatywnie, jeśli chcesz, aby mapper zwracał null:
        // Shop shopDto = shopMapper.from(shopEntity);
        // assertNull(shopDto);
        // Wymagałoby to zmiany w ShopMapper.from(ShopEntity), np. dodania warunku if (shopEntity == null) return null;
    }
}
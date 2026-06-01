package pl.gralewicz.kamil.java.app.bookingguide.service.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Shop;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ShopEntity;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ShopMapperSpringTest {

    @Autowired
    private ShopMapper shopMapper;

    @Test
    void fromDtoToEntity() {
        // Given
        Shop shopDto = new Shop();
        shopDto.setId(10L);
        shopDto.setName("Sklep DTO");
        shopDto.setDescription("Opis z DTO");
        shopDto.setPhoneNumber("123456789");

        // When
        ShopEntity shopEntity = shopMapper.from(shopDto);

        // Then
        assertNotNull(shopEntity, "Encja nie powinna być null");
        assertAll("Sprawdzenie mapowania DTO -> Entity",
                () -> assertEquals(shopDto.getId(), shopEntity.getId(), "ID niezgodne"),
                () -> assertEquals(shopDto.getName(), shopEntity.getName(), "Nazwa niezgodna"),
                () -> assertEquals(shopDto.getDescription(), shopEntity.getDescription(), "Opis niezgodny"),
                () -> assertEquals(shopDto.getPhoneNumber(), shopEntity.getPhoneNumber(), "Nr telefonu niezgodny")
        );
    }

    @Test
    void fromEntityToDto() {
        // Given
        ShopEntity shopEntity = new ShopEntity();
        shopEntity.setId(20L);
        shopEntity.setName("Sklep Encja");
        shopEntity.setDescription("Opis z Encji");
        shopEntity.setPhoneNumber("987654321");

        // When
        Shop shopDto = shopMapper.from(shopEntity);

        // Then
        assertNotNull(shopDto, "DTO nie powinien być null");
        assertAll("Sprawdzenie mapowania Entity -> DTO",
                () -> assertEquals(shopEntity.getId(), shopDto.getId(), "ID niezgodne"),
                () -> assertEquals(shopEntity.getName(), shopDto.getName(), "Nazwa niezgodna"),
                () -> assertEquals(shopEntity.getDescription(), shopDto.getDescription(), "Opis niezgodny"),
                () -> assertEquals(shopEntity.getPhoneNumber(), shopDto.getPhoneNumber(), "Nr telefonu niezgodny")
        );
    }

    @Test
    void fromEntities() {
        // Given
        ShopEntity entity1 = new ShopEntity(); entity1.setId(1L); entity1.setName("E1");
        ShopEntity entity2 = new ShopEntity(); entity2.setId(2L); entity2.setName("E2");
        List<ShopEntity> entityList = List.of(entity1, entity2);

        // When
        Set<Shop> dtoList = shopMapper.fromEntities(entityList);

        // Then
        assertNotNull(dtoList);
        assertEquals(2, dtoList.size(), "Rozmiar listy DTO");
    }

    @Test
    void fromEntitiesHandlesNullInput() {
        // Given
        List<ShopEntity> entityList = null;

        // When
        Set<Shop> dtoList = shopMapper.fromEntities(entityList);

        // Then
        assertNotNull(dtoList, "Lista nie powinna być null nawet dla wejścia null");
        assertTrue(dtoList.isEmpty(), "Lista powinna być pusta dla wejścia null");
    }

    @Test
    void fromEntitiesHandlesEmptyInput() {
        // Given
        List<ShopEntity> entityList = Collections.emptyList();

        // When
        Set<Shop> dtoList = shopMapper.fromEntities(entityList);

        // Then
        assertNotNull(dtoList, "Lista nie powinna być null dla pustego wejścia");
        assertTrue(dtoList.isEmpty(), "Lista powinna być pusta dla pustego wejścia");
    }

    @Test
    void fromDtoToEntityHandlesNullInput() {
        // Given
        Shop shopDto = null;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            shopMapper.from(shopDto);
        }, "Oczekiwano IllegalArgumentException dla wejścia DTO null");
    }

    @Test
    void fromEntityToDtoHandlesNullInput() {
        // Given
        ShopEntity shopEntity = null;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            shopMapper.from(shopEntity);
        }, "Oczekiwano IllegalArgumentException dla wejścia Entity null");
    }
}
package pl.gralewicz.kamil.java.app.bookingguide.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.gralewicz.kamil.java.app.bookingguide.api.RoleType; // Import enuma RoleType
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Role; // Model DTO
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.RoleEntity; // Encja
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.RoleRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.RoleMapper;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceMockTest {

    @Mock
    private RoleRepository roleRepositoryMock;

    @Mock
    private RoleMapper roleMapperMock;

    @InjectMocks
    private RoleService roleService;

    // --- Testy dla metody list() ---

    @Test
    void list() {
        // Given
        RoleEntity entity1 = new RoleEntity(); entity1.setId(1L); entity1.setName(RoleType.ADMIN); // Używamy enuma
        RoleEntity entity2 = new RoleEntity(); entity2.setId(2L); entity2.setName(RoleType.USER);  // Używamy enuma
        List<RoleEntity> entitiesFromRepo = List.of(entity1, entity2);

        Role roleDto1 = new Role(); roleDto1.setId(1L);
        roleDto1.setName(RoleType.ADMIN); // <<< POPRAWKA: Używamy enuma RoleType
        Role roleDto2 = new Role(); roleDto2.setId(2L);
        roleDto2.setName(RoleType.USER);  // <<< POPRAWKA: Używamy enuma RoleType
        List<Role> expectedRoles = List.of(roleDto1, roleDto2);

        when(roleRepositoryMock.findAll()).thenReturn(entitiesFromRepo);
        // Mockujemy indywidualne mapowania, bo serwis używa stream().map()
        when(roleMapperMock.from(entity1)).thenReturn(roleDto1);
        when(roleMapperMock.from(entity2)).thenReturn(roleDto2);

        // When
        List<Role> actualRoles = roleService.list();

        // Then
        Assertions.assertAll("Sprawdzenie listy ról",
                () -> Assertions.assertNotNull(actualRoles, "Lista nie powinna być null"),
                () -> Assertions.assertEquals(2, actualRoles.size(), "Rozmiar listy"),
                () -> Assertions.assertEquals(expectedRoles.get(0).getId(), actualRoles.get(0).getId(), "ID pierwszej roli"),
                () -> Assertions.assertEquals(expectedRoles.get(0).getName(), actualRoles.get(0).getName(), "Nazwa pierwszej roli"), // Porównanie enumów zadziała
                () -> Assertions.assertEquals(expectedRoles.get(1).getId(), actualRoles.get(1).getId(), "ID drugiej roli"),
                () -> Assertions.assertEquals(expectedRoles.get(1).getName(), actualRoles.get(1).getName(), "Nazwa drugiej roli") // Porównanie enumów zadziała
        );

        verify(roleRepositoryMock).findAll();
        verify(roleMapperMock).from(entity1);
        verify(roleMapperMock).from(entity2);
        verifyNoMoreInteractions(roleRepositoryMock, roleMapperMock);
    }

    @Test
    void listEmpty() {
        // Given
        when(roleRepositoryMock.findAll()).thenReturn(Collections.emptyList());

        // When
        List<Role> actualRoles = roleService.list();

        // Then
        Assertions.assertAll("Sprawdzenie pustej listy ról",
                () -> Assertions.assertNotNull(actualRoles, "Lista nie powinna być null"),
                () -> Assertions.assertTrue(actualRoles.isEmpty(), "Lista powinna być pusta")
        );

        verify(roleRepositoryMock).findAll();
        verifyNoInteractions(roleMapperMock);
        verifyNoMoreInteractions(roleRepositoryMock);
    }

    // --- Testy dla metody create() ---

    @Test
    void create() {
        // Given
        Role inputRole = new Role(); // DTO wejściowe
        inputRole.setName(RoleType.ADMIN);

        RoleEntity entityToSave = new RoleEntity(); // Encja przed zapisem
        RoleEntity savedEntity = new RoleEntity(); // Encja po zapisie
        savedEntity.setId(10L);
        savedEntity.setName(RoleType.ADMIN); // Ustawiamy enum także w mocku dla spójności

        Role expectedOutputRole = new Role(); // DTO wyjściowe
        expectedOutputRole.setId(10L);
        expectedOutputRole.setName(RoleType.ADMIN);

        // Mockujemy mapowanie DTO -> Entity i Entity -> DTO
        when(roleMapperMock.from(inputRole)).thenReturn(entityToSave);
        when(roleRepositoryMock.save(entityToSave)).thenReturn(savedEntity);
        when(roleMapperMock.from(savedEntity)).thenReturn(expectedOutputRole);

        // When
        Role actualCreatedRole = roleService.create(inputRole);

        // Then
        Assertions.assertAll("Sprawdzenie utworzonej roli",
                () -> Assertions.assertNotNull(actualCreatedRole, "Utworzona rola nie powinna być null"),
                () -> Assertions.assertEquals(expectedOutputRole.getId(), actualCreatedRole.getId(), "Niezgodne ID"),
                () -> Assertions.assertEquals(expectedOutputRole.getName(), actualCreatedRole.getName(), "Niezgodna nazwa") // Porównanie enumów zadziała
        );

        verify(roleMapperMock).from(inputRole);
        verify(roleRepositoryMock).save(entityToSave);
        verify(roleMapperMock).from(savedEntity);
        verifyNoMoreInteractions(roleRepositoryMock, roleMapperMock);
    }

    // --- Testy dla metody read() ---

    @Test
    void read() {
        // Given
        Long roleId = 1L;
        RoleEntity foundEntity = new RoleEntity();
        foundEntity.setId(roleId);
        foundEntity.setName(RoleType.ADMIN); // Używamy enuma

        Role expectedRoleDto = new Role(); // Oczekiwany wynik DTO
        expectedRoleDto.setId(roleId);
        expectedRoleDto.setName(RoleType.ADMIN);

        when(roleRepositoryMock.findById(roleId)).thenReturn(Optional.of(foundEntity));
        when(roleMapperMock.from(foundEntity)).thenReturn(expectedRoleDto);

        // When
        Role actualRole = roleService.read(roleId);

        // Then
        Assertions.assertAll("Sprawdzenie odczytanej roli",
                () -> Assertions.assertNotNull(actualRole, "Zwrócona rola nie powinna być null"),
                () -> Assertions.assertEquals(expectedRoleDto.getId(), actualRole.getId(), "Niezgodne ID"),
                () -> Assertions.assertEquals(expectedRoleDto.getName(), actualRole.getName(), "Niezgodna nazwa") // Porównanie enumów zadziała
        );

        verify(roleRepositoryMock).findById(roleId);
        verify(roleMapperMock).from(foundEntity);
        verifyNoMoreInteractions(roleRepositoryMock, roleMapperMock);
    }

    @Test
    void readWithException() {
        // Given
        Long nonExistentId = 99L;
        when(roleRepositoryMock.findById(nonExistentId)).thenReturn(Optional.empty());

        // When & Then
        // *** UWAGA: Testujemy OCZEKIWANE zachowanie (rzucenie NoSuchElementException), ***
        // *** ale kod produkcyjny w RoleService.read() ma BŁĄD (orElseThrow(null)). ***
        // *** Należy POPRAWIĆ kod w RoleService.read() zamieniając orElseThrow(null) np. na: ***
        // *** .orElseThrow(() -> new NoSuchElementException("Nie znaleziono roli o ID: " + id)); ***
        assertThrows(NoSuchElementException.class, () -> {
            roleService.read(nonExistentId);
        }, "Oczekiwano NoSuchElementException dla nieistniejącego ID roli");

        verify(roleRepositoryMock).findById(nonExistentId);
        verify(roleMapperMock, never()).from(any(RoleEntity.class));
        verifyNoMoreInteractions(roleRepositoryMock);
        verifyNoInteractions(roleMapperMock);
    }

    // --- Testy dla metody delete() ---

    @Test
    void delete() {
        // Given
        Long idToDelete = 5L;

        // When
        roleService.delete(idToDelete);

        // Then
        verify(roleRepositoryMock).deleteById(idToDelete);
        verifyNoMoreInteractions(roleRepositoryMock);
        verifyNoInteractions(roleMapperMock);
    }
}
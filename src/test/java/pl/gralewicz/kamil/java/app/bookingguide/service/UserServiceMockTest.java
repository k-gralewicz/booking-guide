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
import pl.gralewicz.kamil.java.app.bookingguide.api.RoleType;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.User;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.RoleEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.UserEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.RoleRepository;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.UserRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.RoleMapper;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.UserMapper;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.isNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Testy jednostkowe dla klasy UserService.
 * (Wersja z poprawionym testem updateUser)
 */
@ExtendWith(MockitoExtension.class)
class UserServiceMockTest {

    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private RoleRepository roleRepositoryMock;
    @Mock
    private UserMapper userMapperMock;
    @Mock
    private RoleMapper roleMapperMock;

    @InjectMocks
    private UserService userService;

    @Captor
    private ArgumentCaptor<UserEntity> userEntityCaptor;

    private User userDto1;
    private UserEntity userEntity1;
    private User userDto2;
    private UserEntity userEntity2;
    private RoleEntity roleEntityUser;

    @BeforeEach
    void setUp() {
        roleEntityUser = new RoleEntity();
        roleEntityUser.setId(1L);
        roleEntityUser.setName(RoleType.USER);

        userDto1 = new User();
        userDto1.setId(1L);
        userDto1.setUsername("user1");
        userDto1.setEmail("user1@test.com");
        userDto1.setRoleId(roleEntityUser.getId());

        userEntity1 = new UserEntity();
        userEntity1.setId(1L);
        userEntity1.setUsername("user1");
        userEntity1.setEmail("user1@test.com");
        userEntity1.addRole(roleEntityUser);

        userDto2 = new User();
        userDto2.setId(2L);
        userDto2.setUsername("user2");
        userDto2.setEmail("user2@test.com");

        userEntity2 = new UserEntity();
        userEntity2.setId(2L);
        userEntity2.setUsername("user2");
        userEntity2.setEmail("user2@test.com");
    }

    @Test
    void list() {
        // Given
        List<UserEntity> entitiesFromRepo = List.of(userEntity1, userEntity2);
        when(userRepositoryMock.findAll()).thenReturn(entitiesFromRepo);
        when(userMapperMock.from(userEntity1)).thenReturn(userDto1);
        when(userMapperMock.from(userEntity2)).thenReturn(userDto2);

        // When
        List<User> actualUsers = userService.list();

        // Then
        Assertions.assertAll("Sprawdzenie listy użytkowników",
                () -> Assertions.assertNotNull(actualUsers, "Lista nie powinna być null"),
                () -> Assertions.assertEquals(2, actualUsers.size(), "Rozmiar listy"),
                () -> Assertions.assertEquals(userDto1.getId(), actualUsers.get(0).getId(), "ID pierwszego usera"),
                () -> Assertions.assertEquals(userDto1.getUsername(), actualUsers.get(0).getUsername(), "Username pierwszego usera"),
                () -> Assertions.assertEquals(userDto2.getId(), actualUsers.get(1).getId(), "ID drugiego usera"),
                () -> Assertions.assertEquals(userDto2.getUsername(), actualUsers.get(1).getUsername(), "Username drugiego usera")
        );
        verify(userRepositoryMock).findAll();
        verify(userMapperMock).from(userEntity1);
        verify(userMapperMock).from(userEntity2);
        verifyNoMoreInteractions(userRepositoryMock, userMapperMock);
        verifyNoInteractions(roleRepositoryMock, roleMapperMock);
    }

    @Test
    void listEmpty() {
        // Given
        when(userRepositoryMock.findAll()).thenReturn(Collections.emptyList());

        // When
        List<User> actualUsers = userService.list();

        // Then
        Assertions.assertAll("Sprawdzenie pustej listy użytkowników",
                () -> Assertions.assertNotNull(actualUsers, "Lista nie powinna być null"),
                () -> Assertions.assertTrue(actualUsers.isEmpty(), "Lista powinna być pusta")
        );
        verify(userRepositoryMock).findAll();
        verifyNoInteractions(userMapperMock);
        verifyNoMoreInteractions(userRepositoryMock);
        verifyNoInteractions(roleRepositoryMock, roleMapperMock);
    }

    @Test
    void findByUsername() {
        // Given
        String username = "user1";
        when(userRepositoryMock.findByUsername(username)).thenReturn(userEntity1);
        when(userMapperMock.from(userEntity1)).thenReturn(userDto1);

        // When
        User actualUser = userService.findByUsername(username);

        // Then
        Assertions.assertAll("Sprawdzenie znalezionego użytkownika po nazwie",
                () -> Assertions.assertNotNull(actualUser, "Użytkownik nie powinien być null"),
                () -> Assertions.assertEquals(userDto1.getId(), actualUser.getId(), "Niezgodne ID"),
                () -> Assertions.assertEquals(userDto1.getUsername(), actualUser.getUsername(), "Niezgodny Username")
        );
        verify(userRepositoryMock).findByUsername(username);
        verify(userMapperMock).from(userEntity1);
        verifyNoMoreInteractions(userRepositoryMock, userMapperMock);
    }

    @Test
    void findByUsernameNotFound() {
        // Given
        String username = "nonexistent";
        when(userRepositoryMock.findByUsername(username)).thenReturn(null);
        // Zakładamy, że userMapper.from(null) rzuci IllegalArgumentException
        when(userMapperMock.from(isNull(UserEntity.class))).thenThrow(new IllegalArgumentException("source cannot be null"));

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> {
            userService.findByUsername(username);
        }, "Oczekiwano IllegalArgumentException, gdy repozytorium zwraca null");

        verify(userRepositoryMock).findByUsername(username);
        verify(userMapperMock).from(isNull(UserEntity.class));
        verifyNoMoreInteractions(userRepositoryMock, userMapperMock);
    }

    @Test
    void create() {
        // Given
        when(roleRepositoryMock.findById(userDto1.getRoleId())).thenReturn(Optional.of(roleEntityUser));
        when(userMapperMock.from(userDto1)).thenReturn(userEntity1);
        when(userRepositoryMock.save(any(UserEntity.class))).thenReturn(userEntity1);
        when(userMapperMock.from(userEntity1)).thenReturn(userDto1);

        // When
        User actualCreatedUser = userService.create(userDto1);

        // Then
        Assertions.assertAll("Sprawdzenie utworzonego użytkownika",
                () -> Assertions.assertNotNull(actualCreatedUser, "Utworzony użytkownik nie powinien być null"),
                () -> Assertions.assertEquals(userDto1.getId(), actualCreatedUser.getId(), "Niezgodne ID"),
                () -> Assertions.assertEquals(userDto1.getUsername(), actualCreatedUser.getUsername(), "Niezgodny Username")
        );
        verify(roleRepositoryMock).findById(userDto1.getRoleId());
        verify(userMapperMock).from(userDto1);
        verify(userRepositoryMock).save(userEntityCaptor.capture());
        verify(userMapperMock).from(userEntity1);
        UserEntity capturedEntity = userEntityCaptor.getValue();
        Assertions.assertTrue(capturedEntity.getRoles().contains(roleEntityUser), "Rola nie została dodana do encji przed zapisem");
        // Zmiana weryfikacji - sprawdzamy brak interakcji z mockami NIE używanymi w create
        verifyNoMoreInteractions(roleRepositoryMock, userMapperMock, userRepositoryMock);
        verifyNoInteractions(roleMapperMock);
    }

    @Test
    void createRoleNotFound() {
        // Given
        when(roleRepositoryMock.findById(userDto1.getRoleId())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NoSuchElementException.class, () -> {
            userService.create(userDto1);
        }, "Oczekiwano NoSuchElementException, gdy rola nie istnieje");

        verify(roleRepositoryMock).findById(userDto1.getRoleId());
        verifyNoInteractions(userRepositoryMock, userMapperMock); // UserRepository i UserMapper nie powinny być wywołane
        verifyNoMoreInteractions(roleRepositoryMock);
    }


    // === TEST updateUser Z USUNIĘTYM NIEPOTRZEBNYM STUBBINGIEM ===
    @Test
    void updateUser() {
        // Given
        Long userId = 1L;
        User updatedUserDto = new User();
        updatedUserDto.setId(userId);
        updatedUserDto.setUsername("updatedUser1");
        updatedUserDto.setEmail("updated@test.com");

        UserEntity existingUserEntity = new UserEntity();
        existingUserEntity.setId(userId);
        existingUserEntity.setUsername("user1");
        existingUserEntity.setEmail("user1@test.com");
        existingUserEntity.addRole(roleEntityUser);

        // Obiekt finalSavedEntity reprezentuje stan encji PO zapisie (z nowymi danymi)
        UserEntity finalSavedEntity = new UserEntity();
        finalSavedEntity.setId(userId);
        finalSavedEntity.setUsername("updatedUser1"); // Oczekujemy zaktualizowanej nazwy
        finalSavedEntity.setEmail("updated@test.com"); // Oczekujemy zaktualizowanego emaila
        finalSavedEntity.addRole(roleEntityUser); // Rola pozostaje (nie była zmieniana w DTO)

        // Obiekt finalOutputDto reprezentuje DTO zwrócone na końcu
        User finalOutputDto = new User();
        finalOutputDto.setId(userId);
        finalOutputDto.setUsername("updatedUser1");
        finalOutputDto.setEmail("updated@test.com");
        // finalOutputDto.setRoleId(roleEntityUser.getId()); // Ustaw, jeśli Twoje DTO ma roleId

        // Mockujemy tylko potrzebne wywołania
        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(existingUserEntity));
        // NIE mockujemy userMapperMock.from(updatedUserDto) - bo nie jest wołane w poprawionej metodzie serwisu!
        when(userRepositoryMock.save(any(UserEntity.class))).thenReturn(finalSavedEntity); // Save zwraca encję w stanie po zapisie
        when(userMapperMock.from(finalSavedEntity)).thenReturn(finalOutputDto); // Mapper mapuje finalną encję

        // When
        User actualUpdatedUser = userService.updateUser(userId, updatedUserDto);

        // Then
        Assertions.assertAll("Sprawdzenie zaktualizowanego użytkownika",
                () -> Assertions.assertNotNull(actualUpdatedUser),
                () -> Assertions.assertEquals(finalOutputDto.getId(), actualUpdatedUser.getId()),
                () -> Assertions.assertEquals(finalOutputDto.getUsername(), actualUpdatedUser.getUsername()),
                () -> Assertions.assertEquals(finalOutputDto.getEmail(), actualUpdatedUser.getEmail())
        );

        // Weryfikacja wywołań
        verify(userRepositoryMock).findById(userId);
        // NIE weryfikujemy userMapperMock.from(updatedUserDto);
        verify(userRepositoryMock).save(userEntityCaptor.capture()); // Przechwyć argument save
        verify(userMapperMock).from(finalSavedEntity); // Weryfikujemy mapowanie Entity -> DTO na końcu

        // Sprawdź, czy do save trafiła encja z poprawnym ID i zaktualizowanymi danymi
        UserEntity entityPassedToSave = userEntityCaptor.getValue();
        Assertions.assertEquals(userId, entityPassedToSave.getId(), "ID encji przekazanej do save");
        // Ta asercja powinna teraz przejść, bo poprawiony serwis aktualizuje encję przed zapisem
        Assertions.assertEquals(updatedUserDto.getUsername(), entityPassedToSave.getUsername(), "Username w encji do zapisu");
        Assertions.assertEquals(updatedUserDto.getEmail(), entityPassedToSave.getEmail(), "Email w encji do zapisu");
        Assertions.assertTrue(entityPassedToSave.getRoles().contains(roleEntityUser), "Rola powinna zostać zachowana");

        // Zaktualizowana weryfikacja braku dalszych interakcji
        verifyNoMoreInteractions(userRepositoryMock); // Sprawdzone findById i save
        verifyNoMoreInteractions(userMapperMock);   // Sprawdzone from(Entity)
        verifyNoInteractions(roleRepositoryMock, roleMapperMock); // Te nie powinny być używane w updateUser (chyba że rola jest aktualizowana)
    }


    @Test
    void updateUserNotFound() {
        // Given
        Long nonExistentId = 99L;
        User updatedUserDto = new User();
        updatedUserDto.setId(nonExistentId);
        when(userRepositoryMock.findById(nonExistentId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            userService.updateUser(nonExistentId, updatedUserDto);
        }, "Oczekiwano RuntimeException, gdy użytkownik do aktualizacji nie istnieje");

        verify(userRepositoryMock).findById(nonExistentId);
        verifyNoInteractions(userMapperMock, roleRepositoryMock, roleMapperMock);
        verifyNoMoreInteractions(userRepositoryMock);
    }

    @Test
    void read() {
        // Given
        Long userId = 1L;
        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(userEntity1));
        when(userMapperMock.from(userEntity1)).thenReturn(userDto1);

        // When
        User actualUser = userService.read(userId);

        // Then
        Assertions.assertAll("Sprawdzenie odczytanego użytkownika",
                () -> Assertions.assertNotNull(actualUser),
                () -> Assertions.assertEquals(userDto1.getId(), actualUser.getId()),
                () -> Assertions.assertEquals(userDto1.getUsername(), actualUser.getUsername())
        );
        verify(userRepositoryMock).findById(userId);
        verify(userMapperMock).from(userEntity1);
        verifyNoMoreInteractions(userRepositoryMock, userMapperMock);
    }

    @Test
    void readNotFound() {
        // Given
        Long nonExistentId = 99L;
        when(userRepositoryMock.findById(nonExistentId)).thenReturn(Optional.empty());

        // *** UWAGA: Testujemy OBECNE zachowanie serwisu read(), które używa orElseThrow() z lambdą ***
        // *** (Zakładając, że kod serwisu read() ZOSTAŁ POPRAWIONY zgodnie z sugestiami dla RoleService) ***
        // *** Jeśli kod read() nadal ma orElse(null), ten test zawiedzie - oczekując NSEE, dostanie np. IllegalArgumentException ***

        // When & Then
        assertThrows(NoSuchElementException.class, () -> { // Oczekujemy NSEE, jeśli orElseThrow jest poprawione
            userService.read(nonExistentId);
        }, "Oczekiwano NoSuchElementException, gdy użytkownik nie istnieje");

        verify(userRepositoryMock).findById(nonExistentId);
        verify(userMapperMock, never()).from(any(UserEntity.class)); // Mapper nie powinien być wywołany
        verifyNoMoreInteractions(userRepositoryMock);
        verifyNoInteractions(userMapperMock);
    }

    @Test
    void delete() {
        // Given
        Long idToDelete = 10L;
        // Zakładając, że delete w serwisie sprawdza istnienie
        when(userRepositoryMock.existsById(idToDelete)).thenReturn(true);

        // When
        userService.delete(idToDelete);

        // Then
        verify(userRepositoryMock).existsById(idToDelete); // Weryfikacja sprawdzenia
        verify(userRepositoryMock).deleteById(idToDelete); // Weryfikacja usunięcia
        verifyNoMoreInteractions(userRepositoryMock);
        verifyNoInteractions(userMapperMock, roleRepositoryMock, roleMapperMock);
    }

    @Test
    void deleteNotFound() {
        // Given
        Long idToDelete = 11L;
        // Zakładając, że delete w serwisie sprawdza istnienie
        when(userRepositoryMock.existsById(idToDelete)).thenReturn(false);

        // When
        userService.delete(idToDelete);

        // Then
        verify(userRepositoryMock).existsById(idToDelete); // Sprawdzono
        verify(userRepositoryMock, never()).deleteById(idToDelete); // Nie usunięto
        verifyNoMoreInteractions(userRepositoryMock);
        verifyNoInteractions(userMapperMock, roleRepositoryMock, roleMapperMock);
    }
}
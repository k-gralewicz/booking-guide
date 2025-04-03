package pl.gralewicz.kamil.java.app.bookingguide.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Address;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.AddressEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.AddressRepository;
import pl.gralewicz.kamil.java.app.bookingguide.service.mapper.AddressMapper;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceMockTest {

    @Mock
    private AddressRepository addressRepositoryMock;

    @Mock
    private AddressMapper addressMapperMock;

    @InjectMocks
    private AddressService addressService;

    // --- Testy dla metody list() ---

    @Test
    void list() {
        // Given
        AddressEntity entity1 = new AddressEntity();
        entity1.setId(1L);
        entity1.setCity("Miasto 1");
        AddressEntity entity2 = new AddressEntity();
        entity2.setId(2L);
        entity2.setCity("Miasto 2");
        List<AddressEntity> entitiesFromRepo = List.of(entity1, entity2);

        Address addressDto1 = new Address();
        addressDto1.setId(1L);
        addressDto1.setCity("Miasto 1");
        Address addressDto2 = new Address();
        addressDto2.setId(2L);
        addressDto2.setCity("Miasto 2");
        List<Address> expectedAddresses = List.of(addressDto1, addressDto2);

        when(addressRepositoryMock.findAll()).thenReturn(entitiesFromRepo);
        when(addressMapperMock.fromEntities(entitiesFromRepo)).thenReturn(expectedAddresses);

        // When
        List<Address> actualAddresses = addressService.list();

        // Then
        Assertions.assertAll("Sprawdzenie listy adresów",
                () -> Assertions.assertNotNull(actualAddresses, "Lista nie powinna być null"),
                () -> Assertions.assertEquals(2, actualAddresses.size(), "Rozmiar listy powinien wynosić 2"),
                // Sprawdzenie zawartości - zakładając, że kolejność jest zachowana
                // lub jeśli porównanie obiektów przez equals jest wystarczające.
                // Dla pewności można sprawdzić elementy indywidualnie:
                () -> Assertions.assertEquals(expectedAddresses.get(0).getId(), actualAddresses.get(0).getId(), "ID pierwszego adresu"),
                () -> Assertions.assertEquals(expectedAddresses.get(0).getCity(), actualAddresses.get(0).getCity(), "Miasto pierwszego adresu"),
                () -> Assertions.assertEquals(expectedAddresses.get(1).getId(), actualAddresses.get(1).getId(), "ID drugiego adresu"),
                () -> Assertions.assertEquals(expectedAddresses.get(1).getCity(), actualAddresses.get(1).getCity(), "Miasto drugiego adresu")
                // UWAGA: Porównanie całych list za pomocą Assertions.assertEquals(expectedAddresses, actualAddresses)
                // również zadziała, jeśli klasa Address ma poprawnie zaimplementowaną metodę equals() i kolejność elementów jest zgodna.
        );

        verify(addressRepositoryMock).findAll();
        verify(addressMapperMock).fromEntities(entitiesFromRepo);
        verifyNoMoreInteractions(addressRepositoryMock, addressMapperMock);
    }

    @Test
    void listEmpty() {
        // Given
        when(addressRepositoryMock.findAll()).thenReturn(Collections.emptyList());
        when(addressMapperMock.fromEntities(Collections.emptyList())).thenReturn(Collections.emptyList());

        // When
        List<Address> actualAddresses = addressService.list();

        // Then
        Assertions.assertAll("Sprawdzenie pustej listy",
                () -> Assertions.assertNotNull(actualAddresses, "Lista nie powinna być null"),
                () -> Assertions.assertTrue(actualAddresses.isEmpty(), "Lista powinna być pusta")
        );

        verify(addressRepositoryMock).findAll();
        verify(addressMapperMock).fromEntities(Collections.emptyList());
        verifyNoMoreInteractions(addressRepositoryMock, addressMapperMock);
    }

    // --- Testy dla metody create() ---

    @Test
    void create() {
        // Given
        Address inputAddress = new Address();
        inputAddress.setStreet("Testowa");
        inputAddress.setCity("Miasto Testów");

        AddressEntity entityToSave = new AddressEntity();
        AddressEntity savedEntity = new AddressEntity();
        savedEntity.setId(123L);
        savedEntity.setStreet("Testowa");
        savedEntity.setCity("Miasto Testów");

        Address expectedOutputAddress = new Address(); // Oczekiwany wynik z mappera
        expectedOutputAddress.setId(123L);
        expectedOutputAddress.setStreet("Testowa");
        expectedOutputAddress.setCity("Miasto Testów");

        when(addressMapperMock.from(inputAddress)).thenReturn(entityToSave);
        when(addressRepositoryMock.save(entityToSave)).thenReturn(savedEntity);
        when(addressMapperMock.from(savedEntity)).thenReturn(expectedOutputAddress);

        // When
        Address actualCreatedAddress = addressService.create(inputAddress);

        // Then
        Assertions.assertAll("Sprawdzenie utworzonego adresu",
                () -> Assertions.assertNotNull(actualCreatedAddress, "Utworzony adres nie powinien być null"),
                () -> Assertions.assertEquals(expectedOutputAddress.getId(), actualCreatedAddress.getId(), "Niezgodne ID"),
                () -> Assertions.assertEquals(expectedOutputAddress.getStreet(), actualCreatedAddress.getStreet(), "Niezgodna ulica"),
                () -> Assertions.assertEquals(expectedOutputAddress.getCity(), actualCreatedAddress.getCity(), "Niezgodne miasto")
                // Dodaj sprawdzenia innych pól, jeśli są
        );

        verify(addressMapperMock).from(inputAddress);
        verify(addressRepositoryMock).save(entityToSave);
        verify(addressMapperMock).from(savedEntity);
        verifyNoMoreInteractions(addressRepositoryMock, addressMapperMock);
    }

    // --- Testy dla metody read() ---

    @Test
    void read() {
        // Given
        Long id = 45L;
        AddressEntity foundEntity = new AddressEntity();
        foundEntity.setId(id);
        foundEntity.setStreet("Znaleziona");

        Address expectedAddressDto = new Address(); // Oczekiwany wynik z mappera
        expectedAddressDto.setId(id);
        expectedAddressDto.setStreet("Znaleziona");

        when(addressRepositoryMock.findById(id)).thenReturn(Optional.of(foundEntity));
        // Zakładamy, że kod produkcyjny read() został już POPRAWIONY i używa mappera
        when(addressMapperMock.from(foundEntity)).thenReturn(expectedAddressDto);

        // When
        Address actualAddress = addressService.read(id);

        // Then
        Assertions.assertAll("Sprawdzenie zwróconego adresu po ID",
                () -> Assertions.assertNotNull(actualAddress, "Zwrócony adres nie powinien być null"),
                () -> Assertions.assertEquals(expectedAddressDto.getId(), actualAddress.getId(), "ID adresu niezgodne"),
                () -> Assertions.assertEquals(expectedAddressDto.getStreet(), actualAddress.getStreet(), "Ulica niezgodna")
        );

        verify(addressRepositoryMock).findById(id);
        verify(addressMapperMock).from(foundEntity);
        verifyNoMoreInteractions(addressRepositoryMock, addressMapperMock);
    }

    @Test
    void readWithException() {
        // Given
        Long id = 99L;
        when(addressRepositoryMock.findById(id)).thenReturn(Optional.empty());

        // When & Then
        // AssertAll nie jest tu potrzebne, assertThrows wystarczy
        assertThrows(NoSuchElementException.class, () -> {
            addressService.read(id);
        }, "Oczekiwano NoSuchElementException dla nieistniejącego ID");

        verify(addressRepositoryMock).findById(id);
        verify(addressMapperMock, never()).from(any(AddressEntity.class));
        verifyNoMoreInteractions(addressRepositoryMock);
        verifyNoInteractions(addressMapperMock);
    }

    // --- Testy dla metody delete() ---

    @Test
    void delete() {
        // Given
        Long id = 77L;

        // When
        addressService.delete(id);

        // Then
        verify(addressRepositoryMock).deleteById(id);
        verifyNoMoreInteractions(addressRepositoryMock);
        verifyNoInteractions(addressMapperMock);
    }
}
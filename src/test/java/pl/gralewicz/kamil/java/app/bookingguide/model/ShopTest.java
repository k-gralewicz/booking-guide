package pl.gralewicz.kamil.java.app.bookingguide.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Address;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Client;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.DurationType;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Service;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Shop;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Visit;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

class ShopTest {

    @Test
    void givenShop_whenVisitAvailableForDueDate_thenVisitAvailableForDueDate() {
        //given - tworzymy obiekt klasy testowanej
        Shop shop = new Shop("Studio", "Randomowe studio urody", "555444888", null);
        LocalDateTime dueDate = LocalDateTime.of(2023, Month.JULY, 14, 20, 0);

        //when - wywołujemy testowaną metodę dla klasy testowej
        boolean isAvailable = shop.visitAvailable(dueDate);

        //then - sprawdzamy poprawność działania metody testowej
        Assertions.assertTrue(isAvailable, "Visit is not available");
    }

    @Test
    void givenShop_whenBook_thenVisitBooked() {
        //given
//        Address ulaAddress = new Address("Modlińska", "124", "03-120", "Warszawa", "Polska");
//        Client ulaClient = new Client("Ula", "Dudek", "ududek@o2.pl", "601222333", ulaAddress);
        Address ulaAddress = new Address();
        Client ulaClient = new Client();
        Service ustaService = new Service("Makijaż", "Makijaż permanentny ust", BigDecimal.valueOf(350), 3, DurationType.HOURS);

        Shop zuziaShop = new Shop("Makjiaże", "Makijaże permanentne", "608448998", null);

        LocalDateTime dueDate = LocalDateTime.of(2023, Month.JULY, 22, 10, 0);
        Visit ulaVisit = new Visit();

        //when
        Visit bookedVisit = zuziaShop.book(ulaVisit);

        //then
        Assertions.assertNotNull(bookedVisit, "bookedVisit is null");
    }
}
// TODO: 13.07.2023 dodać test jednostkowy dla metody book().
// dodać test jednostkowy sprawdzający dostępność wizyty dla zarezerwowanej wizyty.
// wykonać zadania na codewars:
// https://www.codewars.com/kata/57a0556c7cb1f31ab3000ad7
// https://www.codewars.com/kata/57e76bc428d6fbc2d500036d
// https://www.codewars.com/kata/57a0e5c372292dd76d000d7e
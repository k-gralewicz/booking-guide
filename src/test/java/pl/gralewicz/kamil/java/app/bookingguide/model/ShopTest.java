package pl.gralewicz.kamil.java.app.bookingguide.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

class ShopTest {

    @Test
    void visitAvailable() {
        //given - tworzymy obiekt klasy testowanej
        Address ewaAddress = new Address("Puławska", "121/56", "01-202", "Warszawa", "Polska");
        Client ewaClient = new Client("Ewa", "Iksińska", "ewax@wp.pl", "503777888", ewaAddress);
        Service masazService = new Service("Masaż", "Ogólny masaż twarzy", BigDecimal.valueOf(250), 1, DurationType.HOURS);

        Shop shop = new Shop("Studio", "Randomowe studio urody", "555444888", null);
//        Visit ewaVisit = new Visit(ewaClient, masazService, shop, LocalDateTime.of(2023, Month.JULY, 14, 20, 0));
//        shop.book(ewaVisit);

        LocalDateTime dueDate = LocalDateTime.of(2023, Month.JULY, 14, 20, 0);

        //when - wywołujemy testowaną metodę dla klasy testowej
        boolean isAvailable = shop.visitAvailable(dueDate);

        //then - sprawdzamy poprawność działania metody testowej
        Assertions.assertTrue(isAvailable, "Visit is not available");
    }

    @Test
    void book(){
        //given
        Address ulaAddress = new Address("Modlińska", "124", "03-120", "Warszawa", "Polska");
        Client ulaClient = new Client("Ula", "Dudek", "ududek@o2.pl", "601222333", ulaAddress);
        Service ustaService = new Service("Makijaż", "Makijaż permanentny ust", BigDecimal.valueOf(350), 3, DurationType.HOURS);

        Shop zuziaShop = new Shop("Makjiaże", "Makijaże permanentne", "608448998", null);

        LocalDateTime dueDate = LocalDateTime.of(2023, Month.JULY, 22, 10, 0);

        Visit ulaVisit = new Visit(ulaClient, ustaService, zuziaShop, dueDate);

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
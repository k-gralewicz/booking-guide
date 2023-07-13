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
}
// TODO: 05.07.2023 stworzyć klasę testową dla klasy Visit oraz metody booking.
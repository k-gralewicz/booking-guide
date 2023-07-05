package pl.gralewicz.kamil.java.app.bookingguide.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

class ShopTest {

    @Test
    void visitAvailable() {
        //given - tworzymy obiekt klasy testowanej
        Shop shop = new Shop("Studio", "Randomowe studio urody", "555444888", null);
        LocalDateTime dueDate = LocalDateTime.of(2023, Month.NOVEMBER, 20, 12, 0);

        //when - wywołujemy testowaną metodę dla klasy testowej
        shop.visitAvailable(dueDate);

        //then - sprawdzamy poprawność działania metody testowej
    }
}
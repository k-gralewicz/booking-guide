package pl.gralewicz.kamil.java.app.bookingguide.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Shop;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Visit;

import java.time.LocalDateTime;
import java.time.Month;

class VisitIntegrationTest {

    @Test
    void bookingForBookedVisit() {
        // given
        Shop shop = new Shop();
        Visit alaVisit = new Visit();
        Visit kajaVisit = new Visit();

        // when
        shop.book(kajaVisit);
        boolean bookedVisit = alaVisit.booking(LocalDateTime.of(2023, Month.JULY, 24, 12, 0));

        // then
        Assertions.assertFalse(bookedVisit, "Visit is booked");
    }
}

// TODO: 13.07.2023 dodać test jednostkowy sprawdzający
//  rejestrację wizyty dla zajętego terminu.
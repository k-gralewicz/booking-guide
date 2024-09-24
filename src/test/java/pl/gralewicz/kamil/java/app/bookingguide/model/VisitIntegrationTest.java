package pl.gralewicz.kamil.java.app.bookingguide.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Address;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Shop;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Visit;

import java.time.LocalDateTime;
import java.time.Month;

class VisitIntegrationTest {

    @Test
    void bookingForBookedVisit() {
        // given
        Shop shop = new Shop("Cudny Masaż", "studio masażu", "501222333", new Address());
        Visit alaVisit = new Visit(null, null, shop, LocalDateTime.of(2023, Month.JULY, 24, 12, 0));
        Visit kajaVisit = new Visit(null, null, shop, LocalDateTime.of(2023, Month.JULY, 24, 12, 0));

        // when
        shop.book(kajaVisit);
        boolean bookedVisit = alaVisit.booking(LocalDateTime.of(2023, Month.JULY, 24, 12, 0));

        // then
        Assertions.assertFalse(bookedVisit, "Visit is booked");
    }
}

// TODO: 13.07.2023 dodać test jednostkowy sprawdzający
//  rejestrację wizyty dla zajętego terminu.
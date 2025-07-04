package pl.gralewicz.kamil.java.app.bookingguide.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Client;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Service;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Shop;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Visit;

import java.time.LocalDateTime;
import java.time.Month;

class VisitTest {

    @Test
    void givenVisitClientServiceShop_whenBooking_thenVisitIsAvailable() {
        //given
//        Client client = new Client("Anna", "Nowak", "anna.nowak@wp.pl", "505444555", new Address());
        Client client = new Client();
        Service service = new Service();
        Shop shop = new Shop();
        Visit visit = new Visit();

        //when
        boolean isAvailable = visit.booking(LocalDateTime.of(2023, Month.JULY, 22, 11, 30));

        //then
        Assertions.assertTrue(isAvailable, "Visit is not available");
    }
}

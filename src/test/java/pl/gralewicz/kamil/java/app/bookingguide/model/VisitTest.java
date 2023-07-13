package pl.gralewicz.kamil.java.app.bookingguide.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.*;

class VisitTest {

    @Test
    void booking() {
        Client client = new Client("Anna", "Nowak", "anna.nowak@wp.pl", "505444555", new Address("Puławska", "124/24", "01-201", "Warszawa", "Polska"));
        Service service = new Service("Masaż", "Ogólny masala twarzy", BigDecimal.valueOf(250), DurationType.MINUTES.getDefaultValue(), DurationType.MINUTES);
        Shop shop = new Shop("Cudny Masaż", "studio masażu", "501222333", new Address("Akacjowa", "18", "02-222", "Warszawa", "Polska"));
        Visit visit = new Visit(client, service, shop,
                LocalDateTime.of(2023, Month.JULY, 22, 11, 30));
//        shop.book(visit);

        boolean isAvailable = visit.booking(LocalDateTime.of(2023, Month.JULY, 22, 11, 30));

        Assertions.assertTrue(isAvailable, "Visit is not available");
    }
}

// TODO: 13.07.2023 dodać test jednostkowy sprawdzający rejestrację wizyty dla zajętego terminu.
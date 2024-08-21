package pl.gralewicz.kamil.java.app.bookingguide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookingGuideApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookingGuideApplication.class,args);
    }
}

// TODO: 07.08.2024 Dla Address konrolera zrobić komponent i test springowy.
// dla wszystkich warstw Client (ctr, serw, mapper, repo), dodać SpringFramework oraz testy.
// na nowym branchu.
package pl.gralewicz.kamil.java.app.bookingguide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookingGuideApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookingGuideApplication.class,args);
    }
}

// TODO: 28.08.2024 dla client-create.html nanieść/dodać css i div wzorując się na SpringLearn
//dodać brakujące pola z modelu,
//

// TODO: 04.09.2024 Dla Address konrolera zrobić komponent i test springowy.
// dla wszystkich warstw Client (ctr, serw, mapper, repo), dodać SpringFramework oraz testy.
// na nowym branchu.
// poprawić Client Repository

// TODO: 04.09.2024 dla wszystkich warstw Service (ctr, serw, mapper, repo), dodać SpringFramework oraz testy.
// na nowym branchu.


// jak da radę resztę to też (Address, Shop, Visit) :)
// też na nowym branchu.

// TODO: 19.09.2024 rozbić adres w Client-create na poszczególne pola i podobnie w pozostałych modelach.
//  zrobić test controller Client analogicznie do AddressControllerWebApplicationTest (też create)

// TODO: 30.09.2024 dodać th do creatów html,
// TODO: 30.09.2024 dla controllera visit, dla metody create dodać html (przez wszystkie warstwy front-back
// łącznie z testami.
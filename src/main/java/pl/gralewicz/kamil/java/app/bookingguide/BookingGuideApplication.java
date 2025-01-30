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

// TODO: 07.10.2024 stworzyć dashboard dla visit
// TODO: 07.10.2024 dodać role do userów (repository i testy) 
// TODO: 07.10.2024 zrobić fragment th który będzie navigation bar (getbootstrap)

// TODO: 14.11.2024 dla widoku visit-create rozróżnić czy jest to tworzenie nowej wizyty czy aktualizacja istniejącej
// TODO: 14.11.2024 napisać testy dla ról użytkowników

// TODO: 21.11.2024 zaimplementować funkcjonalność dodawania nowego użytkownika
// TODO: 21.11.2024 zrobić fragment th który będzie navigation bar (getbootstrap)

// TODO: 23.12.2024 w Navbar zrobić tak, aby logout był po prawej stronie,
// TODO: 23.12.2024 dokończyć ten element, tak aby utworzył się użytkownik z przypisaną rolą.(klasa UserService)

// TODO: 16.01.2025 sprawdzić dlaczego nie działa opcja edycji usera (dlaczego hasło nie przepisuje się),
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

// TODO: 23.01.2025 dla zalogowanego użytkownika zaimpelentować wybór dostępnych usług (mniej więcej jak dla użytkownika dostępne role)

// TODO: 30.01.2025 testy jednostkowe dla ServiceService#read(), VisitService#create(),
// ServiceService#list(), ServiceMapper#from(), ServiceController#create(), ServiceController#read(),
// VisitControllerWebApplicationTest - testy jak w ClientControllerWebApplicationTest.
// https://justjoin.it/blog/komunikacja-frontend-www-z-backend-w-javie

// TODO: 06.02.2025 stworzyć od podstaw nowy projekt SpringFramework + SpringBoot
// szablon projektu stworzyć na stronie start.spring.io
// dodać niezbędne zależności dla projektu Web
// projekt aplikacji umożliwiającej tworzenie notatek przez autorów/użytkowników
// dwa główne modele to note i author
// aplikacja trójwarstwowa: Repo (repo i encja), Mapper, Service (service i mapper), Controller (controller i model),
// każda z warstw posiada metody CRUD,
// dla każdej z warstw i metod stworzyć testy jednostkowe i integracyjne
// frontend html + bootstrap + thymeleaf
// dla każdej publicznej metody loggery wejścia i wyjścia
// dla jednego modelu i jego wszystkich warstw oraz testów napisać komentarze wyjaśniające co technicznie dzieje się tam.
// dodać na GitHub.
// najpierw dodać wszystko od jednego modelu (warstwy), później od drugiego i dopiero relacje między nimi.

// TODO: 06.03.2025 klient może stworzyć nową wizytę, w której wybierze salon, usługę i datę.
// Utowrzyć panel klienta, na którym będą wizyty i przycisk Utwórz wizytę.
// TODO: 27.02.2025 poprawić przypisywanie ról dla użytkownika w UserDetailsService.

// TODO: 06.03.2025 przygotować przykłady strumieni (streams) (+ testy),
// zaprezentować różne constrainy dla kolumn w encji ( + testy),
// analogicznie jak dla Service zarządzać rolami użytkowników (dostępem do funkcjonalności),

// TODO: 20.03.2025 odkomentować relację w VisitEntity (linijka 17) i napisać testy jednostkowe,
// do istniejących serwisów z zaimplementowanymi metodami dodać testy jednostkowe do każdej metody i sprawdzić czy działają poprzednie.
// dokończyć ClientDashboard

// TODO: 03.04.2025 zamienić adres url na formularz w client-dashboard line 32,
//<br/> <button><a th:href="@{/visits/create?username=__${#authentication.name}__}">Book a new visit</a></button> </div>
// dokończyć dashboard - filtrowanie usług dla danego shopa -> filtrowanie serwisów dla użytkownika
// dodać tym razem poprawnie testy jednostkowe dla serwisów!
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

// TODO: 08.05.2025 z chatem GPT rozwiązać problem z userByUsername

// TODO: 15.05.2025 sprawdzić czy dodawanie shopa działa i wyświetla się dla admina

// TODO: 29.05.2025 dodać do shopa metody update i delete
// TODO: 29.05.2025 w teście VisitRepositoryTest dokończyć test poprzez przypisanie sklepu 

// TODO: 12.06.2025 shop - create żeby dodawał z adresem i następnie update też z adresem. 

// TODO: 26.06.2025 różnica między sygnaturą metody a definicją metody.  
// TODO: 26.06.2025 dokończyć implementację visitCreate z poziomu service.  
// TODO: 26.06.2025 albo dla danej usługi znaleźć sklep 
// TODO: 26.06.2025 albo pozostać przy liście usług i sklepów i dla wybranego sklepu wyświetlić dostępne usługi.  

// TODO: 03.07.2025 dokończyć implementację funkcji selectShop w javascript:
// TODO: 03.07.2025 pobranie identyfikatora wybranego shopa, 
// TODO: 03.07.2025 wykonanie request pod endpoint który wyświetla visit-create.html
// TODO: 26.06.2025 albo pozostać przy liście usług i sklepów i dla wybranego sklepu wyświetlić dostępne usługi.

// TODO: 17.07.2025 1b:     b. posiada więcej niż 1 Shop - ma możliwość wyboru Shop'a (nowy ekran z dropdown/select),

// TODO: 14.08.2025 wyciągnąć po username przypisane shopy w UserController.
// TODO: 14.08.2025 1b:     b. posiada więcej niż 1 Shop - ma możliwość wyboru Shop'a (nowy ekran z dropdown/select),

// TODO: 28.08.2025 dodać do Usera funkcjonalność dodawania kolejnego sklepu,
// TODO: 28.08.2025 za pomocą mechanizmu sesji dodać wybrany Shop do sesji,
// TODO: 28.08.2025 w NavBar wyświetlić informacje o wybranym Shop pobranym z sesji.

// TODO: 04.09.2025 dokończyć implementację dodawania Service do Shop.

// TODO: 18.09.2025 Navbar - obok wybranego shop dać możliwość zmiany CHANGE jeśli user ma więcej niż 1 shop,
// TODO: 18.09.2025 Napisać test integracyjny bez Mock dla ServiceService - nowa klasa testowa,
// TODO: 18.09.2025 services.html - dodać NavBar,
// TODO: 18.09.2025 do naprawy services metoda READ,
// TODO: 18.09.2025 sprawdzić czy działa metoda createWithShop - albo testami albo ręcznie,

// TODO: 02.10.2025 do pozostałych plików html dodać
//<div class="container-fluid">
//<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
//        integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
// TODO: 25.09.2025 przy rezerwacji wizyty przypisywać zapisany w sesji shop zarówno wizualnie w html jak i w kodzie java,

// TODO: 02.10.2025 to co wyżej +
// TODO: 02.10.2025 client-read.html,

// TODO: 09.10.2025 dodanie mechanizmu usuwania usług w Service: proponowany sposób realizacji:
// TODO: 09.10.2025 dodanie nowej kolumny STATUS w bazie danych,
// TODO: 09.10.2025 dodanie kolumny za pomocą pola w modelu i encji,
// TODO: 09.10.2025 wartości statusów za pomocą ENUM lub oddzielnych INSERT,
// TODO: 09.10.2025 testy dla nowej kolumny (delete),
// TODO: 09.10.2025 m.in. możliwość zmiany statusów w edycji service (np. z aktywny na nieaktywny i odwrotnie),

// w visit-create.html wyświetlenie shop
// analogicznie do navbar wyświetlić nazwę wybranego shop
// analogicznie do user-dashboard zaimplementować sposób wybierania shopa.
// do sesji dodać shopId analogicznie do ShopName

// createX - sprawdzić o co chodzi na następnym spotkaniu

// TODO: 13.11.2025 na następnym spotkaniu wszystkie metody update do napisania OD NOWA !

// TODO: 12.01.2026 visitController dokończyć implementację clientWithService dla wybranego klienta
// w html dokończyć wyświetlanie listy klientów.
// dla roli ADMIN i USER lista rozwijana, dla CLIENT dane z sesji
// 33 codeWars.
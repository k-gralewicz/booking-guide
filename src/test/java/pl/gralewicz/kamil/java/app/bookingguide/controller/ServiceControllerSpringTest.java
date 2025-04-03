package pl.gralewicz.kamil.java.app.bookingguide.controller;

// --- Podstawowe importy Spring i JUnit ---
import org.junit.jupiter.api.Assertions; // Dla dodatkowych asercji, jeśli potrzebne
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

// --- Importy specyficzne dla testowanych klas ---
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.DurationType;
import pl.gralewicz.kamil.java.app.bookingguide.dao.entity.ServiceEntity;
import pl.gralewicz.kamil.java.app.bookingguide.dao.repository.ServiceRepository;

import java.math.BigDecimal;

// --- Importy statyczne dla MockMvc, Security i Hamcrest ---
import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Testy integracyjne dla ServiceController skupione na operacjach Create i Read.
 * Używa MockMvc do symulowania żądań HTTP.
 */
@SpringBootTest
@AutoConfigureMockMvc
class ServiceControllerSpringTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ServiceRepository serviceRepository;

    @Test
    @Transactional
    void create_shouldCreateServiceAndRedirectWhenDataIsValid() throws Exception {
        // given
        long countBefore = serviceRepository.count();
        String serviceName = "Manicure Hybrydowy";
        String description = "Dokładne opracowanie paznokci i skórek plus hybryda.";
        String price = "150.00";
        String duration = "90";
        String durationType = DurationType.MINUTES.name(); // Użyj .name() dla enuma

        // when & then
        mockMvc.perform(post("/services/create") // Użyj ścieżki z @PostMapping("/create")
                        .param("name", serviceName)
                        .param("description", description)
                        .param("price", price)
                        .param("duration", duration)
                        .param("durationType", durationType)
                        .with(csrf()) // Dodaj token CSRF (jeśli używasz Spring Security)
                )
                .andExpect(status().is3xxRedirection()) // Oczekuj przekierowania (status 302 Found)
                .andExpect(redirectedUrl("/services")); // Oczekuj przekierowania na listę

        // Weryfikacja, czy serwis został dodany do bazy
        Assertions.assertEquals(countBefore + 1, serviceRepository.count());
        // Można dodać bardziej szczegółową weryfikację danych w bazie, jeśli potrzeba
    }

    @Test
    void create_shouldReturnFormWithErrorWhenDataIsInvalid() throws Exception {
        // given
        String invalidPrice = "nie-liczba"; // Niepoprawna cena

        // when & then
        mockMvc.perform(post("/services/create")
                        .param("name", "Test Service Invalid")
                        .param("description", "Desc")
                        .param("price", invalidPrice) // Wyślij niepoprawną cenę
                        .param("duration", "60")
                        .param("durationType", DurationType.MINUTES.name())
                        .with(csrf())
                )
                .andExpect(status().isOk()) // Oczekuj statusu 200 OK (bo wracamy do formularza)
                .andExpect(view().name("service-create")) // Oczekuj powrotu do widoku formularza
                .andExpect(model().attributeExists("service")) // Sprawdź, czy obiekt 'service' jest w modelu
                .andExpect(model().hasErrors()) // Sprawdź, czy są jakiekolwiek błędy bindowania
                .andExpect(model().attributeHasFieldErrorCode("service", "price", "typeMismatch")); // Sprawdź konkretny błąd dla pola 'price'
    }


    // === Testy dla odczytu usługi (GET /services/{id}) ===

    @Test
    @Transactional // Potrzebne, aby encja zapisana w 'given' była widoczna
    void read_shouldReturnServiceDetailsViewWhenFound() throws Exception {
        // given - Stwórzmy serwis, który będziemy odczytywać
        ServiceEntity entity = new ServiceEntity();
        entity.setName("Pedicure Klasyczny");
        entity.setDescription("Opracowanie stóp i paznokci.");
        entity.setPrice(BigDecimal.valueOf(180.00));
        entity.setDuration(75);
        entity.setDurationType(DurationType.MINUTES);
        ServiceEntity savedEntity = serviceRepository.saveAndFlush(entity); // saveAndFlush dla pewności zapisu przed GET
        Long savedId = savedEntity.getId();

        // when & then
        mockMvc.perform(get("/services/{id}", savedId)) // Symuluj GET na poprawny URL z ID
                .andExpect(status().isOk()) // Oczekuj statusu 200 OK
                .andExpect(view().name("service-details")) // Oczekuj widoku szczegółów (upewnij się, że kontroler zwraca tę nazwę)
                .andExpect(model().attributeExists("service")) // Oczekuj atrybutu 'service' w modelu
                .andExpect(model().attribute("service", hasProperty("id", is(savedId)))) // Sprawdź ID
                .andExpect(model().attribute("service", hasProperty("name", is("Pedicure Klasyczny")))) // Sprawdź nazwę
                .andExpect(model().attribute("service", hasProperty("duration", is(75)))); // Sprawdź czas trwania
    }

    @Test
    void read_shouldRedirectWhenNotFound() throws Exception {
        // given
        Long nonExistentId = 9999L;

        // when & then
        mockMvc.perform(get("/services/{id}", nonExistentId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/services?error=Service+not+found"));
    }

}
package pl.gralewicz.kamil.java.app.bookingguide.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Service;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Visit;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class VisitControllerWebApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void list() throws Exception {
        // given

        // when
        ResultActions resultActions = mockMvc.perform(get("/visits")).andDo(print());

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Visits list")));

    }

    @Test
    @WithMockUser
    void create() throws Exception {
        // given
        String content = """
                {
                    "name": "Kwasy"
                           
                }
                """;

        Visit visit = new Visit();
        Service service = new Service();
        service.setName("Masaż");
        visit.setService(service);
        ObjectMapper objectMapper = new ObjectMapper();
        String visitContent = objectMapper.writeValueAsString(visit);

        // when
        ResultActions resultActions = mockMvc.perform(post("/visits").content(visitContent).contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        // then
        resultActions
//                .andExpect(status().isOk())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/visits"));

    }
}
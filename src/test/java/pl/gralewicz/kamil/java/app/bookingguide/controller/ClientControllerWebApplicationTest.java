package pl.gralewicz.kamil.java.app.bookingguide.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.gralewicz.kamil.java.app.bookingguide.controller.model.Client;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class ClientControllerWebApplicationTest {

    @Autowired

    private MockMvc mockMvc;

    @Test
    void list() throws Exception {
        // given

        // when
        ResultActions resultActions = mockMvc.perform(get("/clients")).andDo(print());

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string(containsString("Client list")));

    }

    @Test
    void create() throws Exception{
        // given
        String content = """
        {
            "name": "Małgorzata"
            "lastName": "Andrzejewska"
                   
        }
        """;

        Client client = new Client();
        client.setFirstName("Anna");
        client.setLastName("Kowalska");
        ObjectMapper objectMapper = new ObjectMapper();
        String clientContent = objectMapper.writeValueAsString(client);

        // when
        ResultActions resultActions = mockMvc.perform(
                post("/clients").content(clientContent).contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        // then
        resultActions.andExpect(status().is3xxRedirection());
    }
}
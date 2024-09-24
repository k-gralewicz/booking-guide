package pl.gralewicz.kamil.java.app.bookingguide.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AddressControllerWebApplicationTest {

    @Autowired

    private MockMvc mockMvc;

    @Test
    void list() throws Exception{
        // given

        // when
        ResultActions resultActions = mockMvc.perform(get("/addresses")).andDo(print());
        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string(containsString("Address list")));
    }

}
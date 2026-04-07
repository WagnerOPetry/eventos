package com.desafio.eventos;

import com.desafio.eventos.domain.Event;
import com.desafio.eventos.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class EventosIntegrationTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private EventRepository repository;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void getEvents_endpoint_shouldReturnOk() throws Exception {
        Event e = new Event();
        e.setTitle("Integration");
        e.setDescription("Desc");
        e.setEventDateTime(LocalDateTime.now().plusDays(2));
        e.setLocation("Loc");
        repository.save(e);

        mvc.perform(get("/api/events").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
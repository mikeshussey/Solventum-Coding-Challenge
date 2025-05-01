package com.example.shortener;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ShortenerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void encodeShouldReturnShortUrl() throws Exception {
        mockMvc.perform(post("/encode")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"url\":\"https://example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url").exists());
    }

    @Test
    public void decodeShouldReturnOriginalUrl() throws Exception {
        mockMvc.perform(post("/encode")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"url\":\"https://abc.com\"}"))
                .andExpect(status().isOk());

        mockMvc.perform(post("/decode")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"url\":\"http://short.est/1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url").value("https://abc.com"));
    }
}
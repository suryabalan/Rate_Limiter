package com.example.UserTwo.controller;

import com.example.UserTwo.dto.LimitDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserTwoController.class)
class UserTwoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void apiOne() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/api-one")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void apiTwo() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/api-two")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void apiThree() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/api-three")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void insertLimit() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/master")
                        .content(asJsonString(new LimitDTO(2, 5, 10)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
package com.example.UserTwo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
}
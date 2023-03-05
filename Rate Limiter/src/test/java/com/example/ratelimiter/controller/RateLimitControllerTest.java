package com.example.ratelimiter.controller;

import com.example.ratelimiter.dto.ResponseDTO;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class RateLimitControllerTest {

    @InjectMocks
    private RateLimitController rateLimitController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFirstApi() {
        String user = "surya";
        ResponseDTO responseDTO = rateLimitController.firstApi(user);
        Assertions.assertEquals(200,responseDTO.getResponseCode());
    }
}
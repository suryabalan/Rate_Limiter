package com.example.UserTwo.controller;

import com.example.UserTwo.constants.AppConstants;
import com.example.UserTwo.constants.PathConstants;
import com.example.UserTwo.dto.ResponseDTO;
import com.example.UserTwo.exception.RateLimitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "api/v1")
public class UserTwoController {
    @Autowired
    private RestTemplate template;

    @GetMapping(value = PathConstants.API_ONE)
    @ResponseBody
    public ResponseDTO apiOne() {
        try {
            String user = AppConstants.NAME;
            return template.getForObject("http://localhost:7777/api/v1/api-one?user=" + user, ResponseDTO.class);
        }catch (RateLimitException e){
            throw e;
        }
    }

    @GetMapping(value = PathConstants.API_TWO)
    @ResponseBody
    public ResponseDTO apiTwo() {
        try {
            String user = AppConstants.NAME;
            return template.getForObject("http://localhost:7777/api/v1/api-two?user=" + user, ResponseDTO.class);
        }catch (RateLimitException e){
            throw e;
        }
    }

    @GetMapping(value = PathConstants.API_THREE)
    @ResponseBody
    public ResponseDTO apiThree() {
        try {
            String user = AppConstants.NAME;
            return template.getForObject("http://localhost:7777/api/v1/api-three?user=" + user, ResponseDTO.class);
        }catch (RateLimitException e){
            throw e;
        }
    }
}

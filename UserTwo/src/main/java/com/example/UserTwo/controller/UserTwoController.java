package com.example.UserTwo.controller;

import com.example.UserTwo.constants.PathConstants;
import com.example.UserTwo.dto.LimitDTO;
import com.example.UserTwo.dto.ResponseDTO;
import com.example.UserTwo.exception.RateLimitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
            String user = "Balan";
            return template.getForObject("http://localhost:7777/api/v1/api-one?user=" + user, ResponseDTO.class);
        }catch (RateLimitException e){
            throw e;
        }
    }

    @GetMapping(value = PathConstants.API_TWO)
    @ResponseBody
    public ResponseDTO apiTwo() {
        try {
            String user = "Surya";
            return template.getForObject("http://localhost:7777/api/v1/api-two?user=" + user, ResponseDTO.class);
        }catch (RateLimitException e){
            throw e;
        }
    }

    @GetMapping(value = PathConstants.API_THREE)
    @ResponseBody
    public ResponseDTO apiThree() {
        String user = "Surya";
        return template.getForObject("http://localhost:7777/api/v1/api-three?user=" + user, ResponseDTO.class);
    }

    @PostMapping(value = PathConstants.MASTER)
    @ResponseBody
    public ResponseDTO insertLimit(@RequestBody LimitDTO limitDTO){
        return template.postForObject("http://localhost:7777/api/v1/master",limitDTO,ResponseDTO.class);
    }
}

package com.example.UserOne.controller;

import com.example.UserOne.constants.AppConstants;
import com.example.UserOne.constants.PathConstants;
import com.example.UserOne.dto.LimitDTO;
import com.example.UserOne.dto.ResponseDTO;
import com.example.UserOne.exception.RateLimitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "api/v1")
public class UserOneController {
    @Autowired
    private RestTemplate template;

    @GetMapping(value = PathConstants.API_ONE)
    @ResponseBody
    public ResponseDTO apiOne() {
        try {
            String user = "Surya";
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

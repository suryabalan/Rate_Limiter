package com.example.UserTwo.dto;

import lombok.Data;

@Data
public class LimitDTO {
    private Integer apiOne;
    private Integer apiTwo;
    private Integer apiThree;

    public LimitDTO(int apiOne, int apiTwo, int apiThree) {
        this.apiOne = apiOne;
        this.apiTwo = apiTwo;
        this.apiThree = apiThree;
    }
}

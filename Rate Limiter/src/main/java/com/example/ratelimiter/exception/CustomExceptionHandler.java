package com.example.ratelimiter.exception;

import com.example.ratelimiter.dto.ResponseDTO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(RateLimitException.class)
    @ResponseBody
    public final ResponseDTO handleException(RateLimitException ex, WebRequest request){
        int statusCode = StatusCode.REQUEST_LIMIT_REACHED.getValue();
        ResponseDTO responseDTO = prepareErrorResponse(statusCode,ex.getMessage());
        return responseDTO;
    }

    @ExceptionHandler(InsertException.class)
    @ResponseBody
    public final ResponseDTO handleException(InsertException ex, WebRequest request){
        int statusCode = StatusCode.UNABLE_TO_ADD_LIMIT.getValue();
        ResponseDTO responseDTO = prepareErrorResponse(statusCode,ex.getMessage());
        return responseDTO;
    }

    private ResponseDTO prepareErrorResponse(int statusCode,String message){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setResponseCode(statusCode);
        responseDTO.setResponseMessage(message);
        return responseDTO;
    }
}

package com.example.ratelimiter.controller;


import com.example.ratelimiter.constants.AppConstants;
import com.example.ratelimiter.constants.PathConstants;
import com.example.ratelimiter.dto.LimitDTO;
import com.example.ratelimiter.dto.ResponseDTO;
import com.example.ratelimiter.exception.RateLimitException;
import com.example.ratelimiter.service.LimitDeciderService;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1")
public class RateLimitController {

	@Autowired
	private LimitDeciderService limitDeciderService;

	@GetMapping(value = PathConstants.API_ONE)
	@ResponseBody
	public ResponseDTO firstApi(@RequestParam(required = false) String user) {
		try {
			ResponseDTO responseDTO = new ResponseDTO();
			Bucket bucket = limitDeciderService.resolveBucket(user,AppConstants.API_ONE);
			ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
			if (probe.isConsumed()) {
				if (user.equals(AppConstants.EMPTY_STRING))  {
					responseDTO.setResponseCode(AppConstants.SUCCESS);
					responseDTO.setResponseMessage(AppConstants.GREETING);
					return responseDTO;
				} else {
					responseDTO.setResponseCode(AppConstants.SUCCESS);
					responseDTO.setResponseMessage(AppConstants.HELLO + " " + user);
					return responseDTO;
				}
			}else {
				long waitForRefill = probe.getNanosToWaitForRefill();
				double remainingSeconds = (double) waitForRefill / AppConstants.CONVERT;
				String message = AppConstants.EXCEPTION;
				message = message.replace("<time>",String.valueOf(remainingSeconds));
				throw new RateLimitException(message);
			}
		} catch (RateLimitException e){
			throw e;
		}
	}

	@GetMapping(value = PathConstants.API_TWO)
	@ResponseBody
	public ResponseDTO secondApi(@RequestParam(required = false) String user, HttpServletResponse response) {
		try {
			ResponseDTO responseDTO = new ResponseDTO();
			response.setContentType("text/plain");
			Bucket bucket = limitDeciderService.resolveBucket(user,AppConstants.API_TWO);
			ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
			if (probe.isConsumed()) {
				if (user.equals(AppConstants.EMPTY_STRING))  {
					responseDTO.setResponseCode(AppConstants.SUCCESS);
					responseDTO.setResponseMessage(AppConstants.GREETING);
					return responseDTO;
				} else {
					responseDTO.setResponseCode(AppConstants.SUCCESS);
					responseDTO.setResponseMessage(AppConstants.HELLO + " " + user);
					return responseDTO;
				}
			}else {
				long waitForRefill = probe.getNanosToWaitForRefill();
				double remainingSeconds = (double) waitForRefill / AppConstants.CONVERT;
				String message = AppConstants.EXCEPTION;
				message = message.replace("<time>",String.valueOf(remainingSeconds));
				throw new RateLimitException(message);
			}
		} catch (RateLimitException e){
			throw e;
		}
	}

	@GetMapping(value = PathConstants.API_THREE)
	@ResponseBody
	public ResponseDTO thirdApi(@RequestParam(required = false) String user, HttpServletResponse response) {
		try {
			ResponseDTO responseDTO = new ResponseDTO();
			response.setContentType("text/plain");
			Bucket bucket = limitDeciderService.resolveBucket(user,AppConstants.API_THREE);
			ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
			if (probe.isConsumed()) {
				if (user.equals(AppConstants.EMPTY_STRING))  {
					responseDTO.setResponseCode(AppConstants.SUCCESS);
					responseDTO.setResponseMessage(AppConstants.GREETING);
					return responseDTO;
				} else {
					responseDTO.setResponseCode(AppConstants.SUCCESS);
					responseDTO.setResponseMessage(AppConstants.HELLO + " " + user);
					return responseDTO;
				}
			}else {
				long waitForRefill = probe.getNanosToWaitForRefill();
				double remainingSeconds = (double) waitForRefill / AppConstants.CONVERT;
				String message = AppConstants.EXCEPTION;
				message = message.replace("<time>",String.valueOf(remainingSeconds));
				throw new RateLimitException(message);
			}
		} catch (RateLimitException e){
			throw e;
		}
	}

	@PostMapping(value = PathConstants.MASTER)
	@ResponseBody
	public ResponseDTO master(@RequestBody LimitDTO limitDTO){
		return limitDeciderService.setValues(limitDTO);
	}

}

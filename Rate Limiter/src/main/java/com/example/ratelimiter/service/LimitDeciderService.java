package com.example.ratelimiter.service;

import com.example.ratelimiter.constants.AppConstants;
import com.example.ratelimiter.dto.LimitDTO;
import com.example.ratelimiter.dto.ResponseDTO;
import com.example.ratelimiter.exception.InsertException;
import com.example.ratelimiter.exception.StatusCode;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LimitDeciderService {
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    public static Map<String, Integer> plan = new ConcurrentHashMap<>();

    public Bucket resolveBucket(String user,String apiKey) {
        String userApiCombo = apiKey.concat(user);
        return cache.computeIfAbsent(userApiCombo, this::newBucket);
    }


    public Bucket newBucket(String apiKey) {
         int limit = resolvePlanFromApiKey(apiKey);
         Bandwidth bandwidth = Bandwidth.classic(limit, Refill.intervally(limit, Duration.ofMinutes(1)));
         return Bucket.builder()
                .addLimit(bandwidth)
                .build();
    }

    public ResponseDTO setValues(LimitDTO limitDTO) {
        try {
            ResponseDTO responseDTO = new ResponseDTO();
            if(!Objects.isNull(limitDTO.getApiOne())) {
                plan.put(AppConstants.API_ONE,limitDTO.getApiOne());
            }
            if(!Objects.isNull(limitDTO.getApiTwo())) {
                plan.put(AppConstants.API_TWO, limitDTO.getApiTwo());
            }
            if(!Objects.isNull(limitDTO.getApiThree())) {
                plan.put(AppConstants.API_THREE,limitDTO.getApiThree());
            }
            responseDTO.setResponseCode(StatusCode.LIMIT_ADDED_SUCCESSFULLY.getValue());
            responseDTO.setResponseMessage(AppConstants.LIMIT_ADDED_SUCCESSFULLY);
            return responseDTO;
        }catch (Exception e){
            throw new InsertException(AppConstants.INSERT_EXCEPTION);
        }
    }

   public int resolvePlanFromApiKey(String apiKey) {
        if (apiKey == null || apiKey.isEmpty()) {
            if (!plan.containsKey(AppConstants.API_ONE)) {
                plan.put(AppConstants.API_ONE, AppConstants.TWO);
            }
            return plan.get(AppConstants.API_ONE);
        } else if (apiKey.startsWith(AppConstants.API_ONE)) {
            if (!plan.containsKey(AppConstants.API_ONE)) {
                plan.put(AppConstants.API_ONE, AppConstants.TWO);
            }
            return plan.get(AppConstants.API_ONE);
        } else if (apiKey.startsWith(AppConstants.API_TWO)) {
            if (!plan.containsKey(AppConstants.API_TWO)) {
                plan.put(AppConstants.API_TWO, AppConstants.FIVE);
            }
            return plan.get(AppConstants.API_TWO);
        }
        else if (apiKey.startsWith(AppConstants.API_THREE)) {
            if (!plan.containsKey(AppConstants.API_THREE)) {
                plan.put(AppConstants.API_THREE, AppConstants.TEN);
            }
            return plan.get(AppConstants.API_THREE);
        }
        if (!plan.containsKey(AppConstants.API_ONE)) {
            plan.put(AppConstants.API_ONE, AppConstants.TWO);
        }
        return plan.get(AppConstants.API_ONE);
    }
}

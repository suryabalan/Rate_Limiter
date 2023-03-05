package com.example.ratelimiter.service;

import com.example.ratelimiter.dto.LimitDTO;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class LimitDeciderServiceTest {


    @Mock
    LimitDeciderService limitDeciderService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    public static Map<String, Integer> plan = new ConcurrentHashMap<>();

    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    @Test
    public void testBucket() {
        Refill refill = Refill.intervally(10, Duration.ofMinutes(1));
        Bandwidth limit = Bandwidth.classic(10, refill);
        Bucket bucket = Bucket.builder()
                .addLimit(limit)
                .build();

        for (int i = 1; i <= 10; i++) {
            Assert.assertTrue(bucket.tryConsume(1));
        }
        Assert.assertFalse(bucket.tryConsume(1));
    }

    @Test
    public void testSetValues() {
        LimitDTO limitDTO = new LimitDTO();
        limitDTO.setApiOne(2);
        limitDTO.setApiTwo(5);
        limitDTO.setApiThree(10);
        plan.put("apiOne", limitDTO.getApiOne());
        plan.put("apiTwo", limitDTO.getApiTwo());
        plan.put("apiThree", limitDTO.getApiThree());
        int one = plan.get("apiOne");
        int two = plan.get("apiTwo");
        int three = plan.get("apiThree");
        Assert.assertEquals(2, one);
        Assert.assertEquals(5, two);
        Assert.assertEquals(10, three);

    }

    @Test
    public void testResolveBucket() {
        String apiKey = "apiOne";
        String user = "Surya";
        String apiKeyCombo = apiKey.concat(user);
        Bucket bucket = limitDeciderService.resolveBucket(apiKey,user);
        Assert.assertEquals(bucket, cache.get(apiKeyCombo));
    }

    @Test
    public void testResolvePlanFromApiKey() {
        String apiKeyOne = "apiOne";
        if (!plan.containsKey(apiKeyOne)) {
            plan.put(apiKeyOne, 2);
        }
        int limit = plan.get(apiKeyOne);
        Assert.assertEquals(2,limit);
    }
}
package com.example.flashSaleEngine.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class RateLimitingService {
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    public boolean tryAccess(String userId){
        Bucket bucket = buckets.computeIfAbsent(userId, this::createNewBucket);

        return bucket.tryConsume(1);
    }

    public Bucket createNewBucket(String userId) {
        Bandwidth limit = Bandwidth.classic(10, Refill.greedy(10, Duration.ofMinutes(1)));

        return Bucket.builder().addLimit(limit).build();
    }
}

package com.payhere.kimsan.common.utils;

import java.util.function.Supplier;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Component
public class LockManager {

    private final RedissonClient redissonClient;

    public LockManager(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    public RLock getLock(String lockKey) {
        return redissonClient.getLock(lockKey);
    }

    public <T> T doWithLock(String lockKey, Supplier<T> supplier) {
        RLock lock = getLock(lockKey);
        lock.lock();
        try {
            return supplier.get();
        } finally {
            lock.unlock();
        }
    }
}

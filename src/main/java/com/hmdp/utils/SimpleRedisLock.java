package com.hmdp.utils;

import cn.hutool.core.lang.UUID;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.security.Key;
import java.util.concurrent.TimeUnit;

public class SimpleRedisLock implements ILock {

    //具体业务名称，将前缀和业务名拼接之后当做Key
    private String name;
    //这里不需要@Autowired，因为该对象是我们使用构造函数手动new出来的
    private StringRedisTemplate stringRedisTemplate;
    //锁的前缀
    public static final String KEY_PREFIX = "lock:";
    public static final String ID_PREFIX = UUID.randomUUID().toString(true) + "-";

    public SimpleRedisLock(String name, StringRedisTemplate stringRedisTemplate) {
        this.name = name;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean tryLock(long timeoutSec) {
        String threadId = ID_PREFIX + Thread.currentThread().getId();
        Boolean success = stringRedisTemplate.opsForValue()
                .setIfAbsent(KEY_PREFIX + name, threadId, timeoutSec, TimeUnit.SECONDS);
        return Boolean.TRUE.equals(success);
    }

    @Override
    public void unLock() {
        String threadId = ID_PREFIX + Thread.currentThread().getId();
        String id = stringRedisTemplate.opsForValue().get(KEY_PREFIX + name);
        if (threadId.equals(id)) {
            stringRedisTemplate.delete(KEY_PREFIX + name);
        }
    }
}

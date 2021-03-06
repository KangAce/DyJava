package ink.kangaroo.douyin.common.locks;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.core.types.Expiration;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @Classname DyOpenService
 * @Description TODO
 * @Date 2021/11/29 5:39
 * @Created by Kangaroo
 */
public class RedisTemplateSimpleDistributedLock  implements Lock {

    @Getter
    private final StringRedisTemplate redisTemplate;
    @Getter
    private final String key;
    @Getter
    private final int leaseMilliseconds;

    private final ThreadLocal<String> valueThreadLocal = new ThreadLocal<>();

    public RedisTemplateSimpleDistributedLock(@NonNull StringRedisTemplate redisTemplate, int leaseMilliseconds) {
        this(redisTemplate, "lock:" + UUID.randomUUID().toString(), leaseMilliseconds);
    }

    public RedisTemplateSimpleDistributedLock(@NonNull StringRedisTemplate redisTemplate, @NonNull String key, int leaseMilliseconds) {
        if (leaseMilliseconds <= 0) {
            throw new IllegalArgumentException("Parameter 'leaseMilliseconds' must grate then 0: " + leaseMilliseconds);
        }
        this.redisTemplate = redisTemplate;
        this.key = key;
        this.leaseMilliseconds = leaseMilliseconds;
    }

    @Override
    public void lock() {
        while (!tryLock()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // Ignore
            }
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        while (!tryLock()) {
            Thread.sleep(1000);
        }
    }

    @Override
    public boolean tryLock() {
        String value = valueThreadLocal.get();
        if (value == null || value.length() == 0) {
            value = UUID.randomUUID().toString();
            valueThreadLocal.set(value);
        }
        final byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        final byte[] valueBytes = value.getBytes(StandardCharsets.UTF_8);
        List<Object> redisResults = redisTemplate.executePipelined((RedisCallback<String>) connection -> {
            connection.set(keyBytes, valueBytes, Expiration.milliseconds(leaseMilliseconds), RedisStringCommands.SetOption.SET_IF_ABSENT);
            connection.get(keyBytes);
            return null;
        });
        Object currentLockSecret = redisResults.size() > 1 ? redisResults.get(1) : redisResults.get(0);
        return currentLockSecret != null && currentLockSecret.toString().equals(value);
    }

    @Override
    public boolean tryLock(long time, @NonNull TimeUnit unit) throws InterruptedException {
        long waitMs = unit.toMillis(time);
        boolean locked = tryLock();
        while (!locked && waitMs > 0) {
            long sleep = waitMs < 1000 ? waitMs : 1000;
            Thread.sleep(sleep);
            waitMs -= sleep;
            locked = tryLock();
        }
        return locked;
    }

    @Override
    public void unlock() {
        if (valueThreadLocal.get() != null) {
            // ??????: ????????????returnType, ??????: ???????????????Long, ?????????Integer
            RedisScript<Long> script = new DefaultRedisScript("if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end", Long.class);
            redisTemplate.execute(script, Arrays.asList(key), valueThreadLocal.get());
            valueThreadLocal.remove();
        }
    }

    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException();
    }

    /**
     * ?????????????????????
     * return ??????null?????????????????????, ???????????????null????????????????????????????????????(redis???key??????????????????)
     */
    public String getLockSecretValue() {
        return valueThreadLocal.get();
    }
}

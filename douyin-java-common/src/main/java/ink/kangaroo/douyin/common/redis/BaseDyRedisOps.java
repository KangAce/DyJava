package ink.kangaroo.douyin.common.redis;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @Classname DyOpenService
 * @Description redis操作基本类
 * <p>
 * 非内置实现redis相关操作, 请实现该类
 * @Date 2021/11/29 5:39
 * @Created by Kangaroo
 */
public class BaseDyRedisOps implements DyRedisOps {

    @Override
    public String getValue(String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setValue(String key, String value, int expire, TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Long getExpire(String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void expire(String key, int expire, TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Lock getLock(String key) {
        throw new UnsupportedOperationException();
    }
}

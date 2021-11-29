package ink.kangaroo.spring.starter.dyjava.open.config;

import ink.kangaroo.spring.starter.dyjava.open.config.storage.DyOpenInJedisConfigStorageConfiguration;
import ink.kangaroo.spring.starter.dyjava.open.config.storage.DyOpenInMemoryConfigStorageConfiguration;
import ink.kangaroo.spring.starter.dyjava.open.config.storage.DyOpenInRedisTemplateConfigStorageConfiguration;
import ink.kangaroo.spring.starter.dyjava.open.config.storage.DyOpenInRedissonConfigStorageConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Classname DyOpenService
 * @Description TODO
 * @Date 2021/11/29 5:39
 * @Created by Kangaroo
 */
@Configuration
@Import({
        DyOpenInMemoryConfigStorageConfiguration.class,
        DyOpenInRedisTemplateConfigStorageConfiguration.class,
        DyOpenInJedisConfigStorageConfiguration.class,
        DyOpenInRedissonConfigStorageConfiguration.class
})
public class DyOpenStorageAutoConfiguration {
}

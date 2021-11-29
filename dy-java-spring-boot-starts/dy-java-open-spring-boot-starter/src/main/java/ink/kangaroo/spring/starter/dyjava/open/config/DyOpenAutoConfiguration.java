package ink.kangaroo.spring.starter.dyjava.open.config;

import ink.kangaroo.spring.starter.dyjava.open.properties.DyOpenProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Classname DyOpenService
 * @Description TODO
 * @Date 2021/11/29 5:39
 * @Created by Kangaroo
 */
@Configuration
@EnableConfigurationProperties(DyOpenProperties.class)
@Import({
        DyOpenStorageAutoConfiguration.class,
        DyOpenServiceAutoConfiguration.class
})
public class DyOpenAutoConfiguration {
}

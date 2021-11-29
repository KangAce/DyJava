package ink.kangaroo.spring.starter.dyjava.open.config.storage;

import ink.kangaroo.douyin.open.api.DyOpenConfigStorage;
import ink.kangaroo.douyin.open.api.impl.DyOpenInMemoryConfigStorage;
import ink.kangaroo.spring.starter.dyjava.open.properties.DyOpenProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname DyOpenService
 * @Description TODO
 * @Date 2021/11/29 5:39
 * @Created by Kangaroo
 */
@Configuration
@ConditionalOnProperty(
        prefix = DyOpenProperties.PREFIX + ".config-storage", name = "type",
        matchIfMissing = true, havingValue = "memory"
)
@RequiredArgsConstructor
public class DyOpenInMemoryConfigStorageConfiguration extends AbstractDyOpenConfigStorageConfiguration{
    private final DyOpenProperties properties;

    @Bean
    @ConditionalOnMissingBean(DyOpenConfigStorage.class)
    public DyOpenConfigStorage wxOpenConfigStorage() {
        DyOpenInMemoryConfigStorage config = new DyOpenInMemoryConfigStorage();
        return this.config(config, properties);
    }
}

package ink.kangaroo.spring.starter.dyjava.open.config.storage;

import ink.kangaroo.douyin.open.api.impl.DyOpenInMemoryConfigStorage;
import ink.kangaroo.spring.starter.dyjava.open.properties.DyOpenProperties;

/**
 * @Classname DyOpenService
 * @Description TODO
 * @Date 2021/11/29 5:39
 * @Created by Kangaroo
 */
public class AbstractDyOpenConfigStorageConfiguration {

    protected DyOpenInMemoryConfigStorage config(DyOpenInMemoryConfigStorage config, DyOpenProperties properties) {
        DyOpenProperties.ConfigStorage configStorageProperties = properties.getConfigStorage();
        config.setDyOpenInfo(properties.getAppId(), properties.getSecret(), properties.getToken(), properties.getAesKey());
        config.setHttpProxyHost(configStorageProperties.getHttpProxyHost());
        config.setHttpProxyUsername(configStorageProperties.getHttpProxyUsername());
        config.setHttpProxyPassword(configStorageProperties.getHttpProxyPassword());
        if (configStorageProperties.getHttpProxyPort() != null) {
            config.setHttpProxyPort(configStorageProperties.getHttpProxyPort());
        }
        int maxRetryTimes = configStorageProperties.getMaxRetryTimes();
        if (configStorageProperties.getMaxRetryTimes() < 0) {
            maxRetryTimes = 0;
        }
        int retrySleepMillis = configStorageProperties.getRetrySleepMillis();
        if (retrySleepMillis < 0) {
            retrySleepMillis = 1000;
        }
        config.setRetrySleepMillis(retrySleepMillis);
        config.setMaxRetryTimes(maxRetryTimes);
        return config;
    }
}

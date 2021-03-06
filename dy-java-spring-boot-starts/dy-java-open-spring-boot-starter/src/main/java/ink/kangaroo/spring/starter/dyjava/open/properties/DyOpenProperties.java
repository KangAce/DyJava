package ink.kangaroo.spring.starter.dyjava.open.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.io.Serializable;

import static ink.kangaroo.spring.starter.dyjava.open.properties.DyOpenProperties.PREFIX;

/**
 * @Classname DyOpenService
 * @Description TODO
 * @Date 2021/11/29 5:39
 * @Created by Kangaroo
 */
@Data
@ConfigurationProperties(PREFIX)
public class DyOpenProperties {

    public static final String PREFIX = "dy.open";

    /**
     * 设置微信开放平台的appid.
     */
    private String appId;

    /**
     * 设置微信开放平台的app secret.
     */
    private String secret;

    /**
     * 设置微信开放平台的token.
     */
    private String token;

    /**
     * 设置微信开放平台的EncodingAESKey.
     */
    private String aesKey;

    /**
     * 存储策略.
     */
    private ConfigStorage configStorage = new ConfigStorage();


    @Data
    public static class ConfigStorage implements Serializable {
        private static final long serialVersionUID = 4815731027000065434L;

        /**
         * 存储类型.
         */
        private StorageType type = StorageType.memory;

        /**
         * 指定key前缀.
         */
        private String keyPrefix = "wx";

        /**
         * redis连接配置.
         */
        @NestedConfigurationProperty
        private RedisProperties redis = new RedisProperties();

        /**
         * http客户端类型.
         */
        private HttpClientType httpClientType = HttpClientType.httpclient;

        /**
         * http代理主机.
         */
        private String httpProxyHost;

        /**
         * http代理端口.
         */
        private Integer httpProxyPort;

        /**
         * http代理用户名.
         */
        private String httpProxyUsername;

        /**
         * http代理密码.
         */
        private String httpProxyPassword;

        /**
         * http 请求重试间隔
         * <pre>
         *   {@link me.chanjar.weixin.mp.api.impl.BaseWxMpServiceImpl#setRetrySleepMillis(int)}
         *   {@link cn.binarywang.wx.miniapp.api.impl.BaseWxMaServiceImpl#setRetrySleepMillis(int)}
         * </pre>
         */
        private int retrySleepMillis = 1000;
        /**
         * http 请求最大重试次数
         * <pre>
         *   {@link me.chanjar.weixin.mp.api.impl.BaseWxMpServiceImpl#setMaxRetryTimes(int)}
         *   {@link cn.binarywang.wx.miniapp.api.impl.BaseWxMaServiceImpl#setMaxRetryTimes(int)}
         * </pre>
         */
        private int maxRetryTimes = 5;

    }

    public enum StorageType {
        /**
         * 内存.
         */
        memory,
        /**
         * jedis.
         */
        jedis,
        /**
         * redisson.
         */
        redisson,
        /**
         * redistemplate
         */
        redistemplate
    }

    public enum HttpClientType {
        /**
         * HttpClient.
         */
        httpclient
    }
}

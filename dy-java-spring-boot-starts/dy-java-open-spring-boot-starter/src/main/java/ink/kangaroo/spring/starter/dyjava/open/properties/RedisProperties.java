package ink.kangaroo.spring.starter.dyjava.open.properties;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname DyOpenService
 * @Description Redis配置.
 * @Date 2021/11/29 5:39
 * @Created by Kangaroo
 */
@Data
public class RedisProperties implements Serializable {

    /**
     * 主机地址.
     */
    private String host;

    /**
     * 端口号.
     */
    private int port = 6379;

    /**
     * 密码.
     */
    private String password;

    /**
     * 超时.
     */
    private int timeout = 2000;

    /**
     * 数据库.
     */
    private int database = 0;

    private Integer maxActive;
    private Integer maxIdle;
    private Integer maxWaitMillis;
    private Integer minIdle;
}

package ink.kangaroo.douyin.common.util.http;

import lombok.Builder;

/**
 * @Classname DyOpenService
 * @Description TODO
 * @Date 2021/11/29 5:39
 * @Created by Kangaroo
 */
public enum HttpType {
    /**
     * jodd-http.
     */
    JODD_HTTP,
    /**
     * apache httpclient.
     */
    APACHE_HTTP,
    /**
     * okhttp.
     */
    OK_HTTP,
    /**
     * okhttp.
     */
    DEFAULT,
}

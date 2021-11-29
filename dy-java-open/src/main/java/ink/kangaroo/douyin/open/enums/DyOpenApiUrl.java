package ink.kangaroo.douyin.open.enums;

import ink.kangaroo.config.DyHostConfig;
import ink.kangaroo.douyin.open.api.DyOpenConfigStorage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static ink.kangaroo.config.DyHostConfig.OPEN_DEFAULT_HOST_URL;
import static ink.kangaroo.config.DyHostConfig.buildUrl;

/**
 * @Classname DyOpenService
 * @Description TODO
 * @Date 2021/11/29 5:39
 * @Created by Kangaroo
 */
public interface DyOpenApiUrl {

    /**
     * 得到api完整地址.
     *
     * @param config 微信公众号配置
     * @return api地址
     */
    default String getUrl(DyOpenConfigStorage config) {
        DyHostConfig hostConfig = null;
        if (config != null) {
            hostConfig = config.getHostConfig();
        }
        return buildUrl(hostConfig, this.getPrefix(), this.getPath());

    }

    /**
     * the path
     *
     * @return path
     */
    String getPath();

    /**
     * the prefix
     *
     * @return prefix
     */
    String getPrefix();
    @AllArgsConstructor
    @Getter
    enum OAuth2 implements DyOpenApiUrl {
        /**
         * 用code换取oauth2的access token.
         */
        OAUTH2_ACCESS_TOKEN_URL(OPEN_DEFAULT_HOST_URL, "/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code"),
        /**
         * 刷新oauth2的access token.
         */
        OAUTH2_REFRESH_TOKEN_URL(OPEN_DEFAULT_HOST_URL, "/sns/oauth2/refresh_token?appid=%s&grant_type=refresh_token&refresh_token=%s"),
        /**
         * 用oauth2获取用户信息.
         */
        OAUTH2_USERINFO_URL(OPEN_DEFAULT_HOST_URL, "/sns/userinfo?access_token=%s&openid=%s&lang=%s"),
        /**
         * 验证oauth2的access token是否有效.
         */
        OAUTH2_VALIDATE_TOKEN_URL(OPEN_DEFAULT_HOST_URL, "/sns/auth?access_token=%s&openid=%s"),
        /**
         * oauth2授权的url连接.
         */
        CONNECT_OAUTH2_AUTHORIZE_URL(OPEN_DEFAULT_HOST_URL, "/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s&connect_redirect=1#wechat_redirect");

        private final String prefix;
        private final String path;

    }
}

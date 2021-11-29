package ink.kangaroo.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname DyOpenService
 * @Description TODO
 * @Date 2021/11/29 5:39
 * @Created by Kangaroo
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DyHostConfig {
    public static final String OPEN_DEFAULT_HOST_URL = "https://open.douyin.com/";

    /**
     * 对应于：https://open.weixin.qq.com
     */
    private String openHost;

    public static String buildUrl(DyHostConfig hostConfig, String prefix, String path) {
        if (hostConfig == null) {
            return prefix + path;
        }

        if (hostConfig.getOpenHost() != null && prefix.equals(OPEN_DEFAULT_HOST_URL)) {
            return hostConfig.getOpenHost() + path;
        }

        return prefix + path;
    }
}

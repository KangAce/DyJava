package ink.kangaroo.config;

import lombok.Data;

@Data
public class DouYinConfig {
    /**
     * 抖音的OAuth API以https://open.douyin.com/开头。
     */
    private String baseUrl = "https://open.douyin.com/";
}

package ink.kangaroo.douyin.common.bean.oauth2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import ink.kangaroo.douyin.common.bean.DyBaseResult;
import lombok.Data;

/**
 * @Classname DyOpenService
 * @Description TODO
 * @Date 2021/11/29 5:39
 * @Created by Kangaroo
 */
@Data
public class DyOAuth2AccessToken {

    @JSONField(name = "access_token")
    private String accessToken;

    @JSONField(name = "refresh_token")
    private String refreshToken;

    @JSONField(name = "open_id")
    private String openId;

    @JSONField(name = "refresh_expires_in")
    private String refreshExpiresIn;

    @JSONField(name = "scope")
    private String scope;

    @JSONField(name = "description")
    private String description;

    @JSONField(name = "error_code")
    private String errorCode;

    @JSONField(name = "expires_in")
    private String expiresIn;


    public static DyOAuth2AccessToken fromJson(String json) {
        DyBaseResult dyBaseResult = JSON.parseObject(json, DyBaseResult.class);
        DyOAuth2AccessToken dyOAuth2AccessToken = JSON.parseObject(JSON.toJSONString(dyBaseResult.getData()), DyOAuth2AccessToken.class);
        return dyOAuth2AccessToken;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}

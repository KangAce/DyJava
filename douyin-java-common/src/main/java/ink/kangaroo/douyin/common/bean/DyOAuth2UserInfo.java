package ink.kangaroo.douyin.common.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import ink.kangaroo.douyin.common.bean.oauth2.DyOAuth2AccessToken;
import lombok.Data;

/**
 * @Classname DyOpenService
 * @Description TODO
 * @Date 2021/11/29 5:39
 * @Created by Kangaroo
 */
@Data
public class DyOAuth2UserInfo {

    @JSONField(name = "country")
    private String country;

    @JSONField(name = "gender")
    private Long gender;

    @JSONField(name = "province")
    private String province;

    @JSONField(name = "city")
    private String city;

    @JSONField(name = "open_id")
    private String open_id;

    @JSONField(name = "nickname")
    private String nickname;

    @JSONField(name = "union_id")
    private String union_id;

    @JSONField(name = "description")
    private String description;

    @JSONField(name = "error_code")
    private Long error_code;

    @JSONField(name = "avatar")
    private String avatar;

    @JSONField(name = "e_account_role")
    private String e_account_role;


    public static DyOAuth2UserInfo fromJson(String json) {
        var dyBaseResult = JSON.parseObject(json, DyBaseResult.class);
        return JSON.parseObject(JSON.toJSONString(dyBaseResult.getData()), DyOAuth2UserInfo.class);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}

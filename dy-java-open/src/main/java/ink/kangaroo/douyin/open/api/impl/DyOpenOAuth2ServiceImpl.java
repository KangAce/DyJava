package ink.kangaroo.douyin.open.api.impl;

import ink.kangaroo.douyin.common.bean.DyOAuth2UserInfo;
import ink.kangaroo.douyin.common.bean.oauth2.DyOAuth2AccessToken;
import ink.kangaroo.douyin.common.enums.DyType;
import ink.kangaroo.douyin.common.error.DyErrorException;
import ink.kangaroo.douyin.common.error.DyRuntimeException;
import ink.kangaroo.douyin.common.service.DyOAuth2Service;
import ink.kangaroo.douyin.common.util.http.SimpleGetRequestExecutor;
import lombok.AllArgsConstructor;

import java.io.IOException;

import static ink.kangaroo.douyin.open.enums.DyOpenApiUrl.OAuth2.*;

/**
 * @Classname DyOpenService
 * @Description TODO
 * @Date 2021/11/29 5:39
 * @Created by Kangaroo
 */
@AllArgsConstructor
public class DyOpenOAuth2ServiceImpl extends DyOpenServiceImpl implements DyOAuth2Service {

    private final String appId;
    private final String appSecret;

    @Override
    public String buildAuthorizationUrl(String redirectUri, String scope, String state) {
        return null;
    }

    private DyOAuth2AccessToken getOAuth2AccessToken(String url) throws DyErrorException {
        return DyOAuth2AccessToken.fromJson(this.get(url, null));
    }

    @Override
    public DyOAuth2AccessToken getAccessToken(String code) throws DyErrorException {

        return this.getAccessToken(this.appId, this.appSecret, code);
    }

    @Override
    public DyOAuth2AccessToken getAccessToken(String appId, String appSecret, String code) throws DyErrorException {
        return this.getOAuth2AccessToken(String.format(OAUTH2_ACCESS_TOKEN_URL.getUrl(null), appId, appSecret, code));
    }

    @Override
    public DyOAuth2AccessToken refreshAccessToken(String refreshToken) throws DyErrorException {
        String url = String.format(OAUTH2_REFRESH_TOKEN_URL.getUrl(null), this.appId, refreshToken);
        return this.getOAuth2AccessToken(url);
    }

    @Override
    public DyOAuth2UserInfo getUserInfo(DyOAuth2AccessToken token, String lang) throws DyErrorException {
        if (lang == null) {
            lang = "zh_CN";
        }

        String url = String.format(OAUTH2_USERINFO_URL.getUrl(null), token.getAccessToken(), token.getOpenId(), lang);

        return DyOAuth2UserInfo.fromJson(this.get(url, null));
    }

    @Override
    public boolean validateAccessToken(DyOAuth2AccessToken token) {
        String url = String.format(OAUTH2_VALIDATE_TOKEN_URL.getUrl(null), token.getAccessToken(), token.getOpenId());

        try {
            SimpleGetRequestExecutor.create(this).execute(url, null, DyType.Open);
        } catch (IOException e) {
            throw new DyRuntimeException(e);
        } catch (DyErrorException e) {
            return false;
        }
        return true;
    }
}

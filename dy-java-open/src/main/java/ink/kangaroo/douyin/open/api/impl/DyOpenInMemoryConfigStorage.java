package ink.kangaroo.douyin.open.api.impl;

import ink.kangaroo.config.DyHostConfig;
import ink.kangaroo.douyin.open.api.DyOpenConfigStorage;
import lombok.Data;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Classname DyOpenService
 * @Description TODO
 * @Date 2021/11/29 5:39
 * @Created by Kangaroo
 */
@Data
public class DyOpenInMemoryConfigStorage implements DyOpenConfigStorage {

    private String componentAppId;
    private String componentAppSecret;
    private String componentToken;
    private String componentAesKey;
    private String componentVerifyTicket;
    private String componentAccessToken;
    private long componentExpiresTime;

    private String httpProxyHost;
    private int httpProxyPort;
    private String httpProxyUsername;
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
//    private ApacheHttpClientBuilder apacheHttpClientBuilder;

    private Map<String, Token> authorizerRefreshTokens = new ConcurrentHashMap<>();
    private Map<String, Token> authorizerAccessTokens = new ConcurrentHashMap<>();
    private Map<String, Token> jsapiTickets = new ConcurrentHashMap<>();
    private Map<String, Token> cardApiTickets = new ConcurrentHashMap<>();
    private Map<String, Lock> locks = new ConcurrentHashMap<>();

    @Override
    public String getComponentAppId() {
        return null;
    }

    @Override
    public void setComponentAppId(String componentAppId) {

    }

    @Override
    public String getComponentAppSecret() {
        return null;
    }

    @Override
    public void setComponentAppSecret(String componentAppSecret) {

    }

    @Override
    public String getComponentToken() {
        return null;
    }

    @Override
    public void setComponentToken(String componentToken) {

    }

    @Override
    public String getComponentAesKey() {
        return null;
    }

    @Override
    public void setComponentAesKey(String componentAesKey) {

    }

    @Override
    public String getComponentVerifyTicket() {
        return null;
    }

    @Override
    public void setComponentVerifyTicket(String componentVerifyTicket) {

    }

    @Override
    public String getComponentAccessToken() {
        return null;
    }

    @Override
    public boolean isComponentAccessTokenExpired() {
        return System.currentTimeMillis() > componentExpiresTime;
    }

    @Override
    public void expireComponentAccessToken() {
        this.componentExpiresTime = 0L;
    }

    @Override
    public String getHttpProxyHost() {
        return null;
    }

    @Override
    public Integer getHttpProxyPort() {
        return null;
    }

    @Override
    public String getHttpProxyUsername() {
        return null;
    }

    @Override
    public String getHttpProxyPassword() {
        return null;
    }

    @Override
    public int getRetrySleepMillis() {
        return 0;
    }

    @Override
    public int getMaxRetryTimes() {
        return 0;
    }

//    @Override
//    public void updateComponentAccessToken(WxOpenComponentAccessToken componentAccessToken) {
//        updateComponentAccessToken(componentAccessToken.getComponentAccessToken(), componentAccessToken.getExpiresIn());
//    }

    private Lock accessTokenLockInstance;

    @Override
    public Lock getComponentAccessTokenLock() {
        if (this.accessTokenLockInstance == null) {
            synchronized (this) {
                if (this.accessTokenLockInstance == null) {
                    this.accessTokenLockInstance = getLockByKey("componentAccessTokenLock");
                }
            }
        }
        return this.accessTokenLockInstance;
    }

    @Override
    public Lock getLockByKey(String key) {
        Lock lock = locks.get(key);
        if (lock == null) {
            synchronized (this) {
                lock = locks.get(key);
                if (lock == null) {
                    lock = new ReentrantLock();
                    locks.put(key, lock);
                }
            }
        }
        return lock;
    }

//    @Override
//    public WxMpConfigStorage getWxMpConfigStorage(String appId) {
//        return new WxOpenInnerConfigStorage(this, appId);
//    }

//    @Override
//    public WxMaConfig getWxMaConfig(String appId) {
//        return new WxOpenInnerConfigStorage(this, appId);
//    }

    @Override
    public void updateComponentAccessToken(String componentAccessToken, int expiresInSeconds) {
        this.componentAccessToken = componentAccessToken;
        this.componentExpiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L;
    }

    @Override
    public void setDyOpenInfo(String componentAppId, String componentAppSecret, String componentToken,
                              String componentAesKey) {
        setComponentAppId(componentAppId);
        setComponentAppSecret(componentAppSecret);
        setComponentToken(componentToken);
        setComponentAesKey(componentAesKey);
    }

    @Override
    public DyHostConfig getHostConfig() {
        return null;
    }

    @Override
    public boolean autoRefreshToken() {
        return true;
    }

    private String getTokenString(Map<String, Token> map, String key) {
        Token token = map.get(key);
        if (token == null || (token.expiresTime != null && System.currentTimeMillis() > token.expiresTime)) {
            return null;
        }
        return token.token;
    }

    private void expireToken(Map<String, Token> map, String key) {
        Token token = map.get(key);
        if (token != null) {
            token.expiresTime = 0L;
        }
    }

    private void updateToken(Map<String, Token> map, String key, String tokenString, Integer expiresInSeconds) {
        Token token = map.get(key);
        if (token == null) {
            token = new Token();
            map.put(key, token);
        }
        token.token = tokenString;
        if (expiresInSeconds != null && expiresInSeconds != -1) {
            token.expiresTime = System.currentTimeMillis() + (expiresInSeconds - 200) * 1000L;
        }
    }

    @Override
    public String getAuthorizerRefreshToken(String appId) {
        return getTokenString(authorizerRefreshTokens, appId);
    }

    @Override
    public void setAuthorizerRefreshToken(String appId, String authorizerRefreshToken) {
        updateToken(authorizerRefreshTokens, appId, authorizerRefreshToken, null);
    }

    @Override
    public void updateAuthorizerRefreshToken(String appId, String authorizerRefreshToken) {
        this.setAuthorizerRefreshToken(appId, authorizerRefreshToken);
    }

    @Override
    public String getAuthorizerAccessToken(String appId) {
        return getTokenString(authorizerAccessTokens, appId);
    }


    @Override
    public boolean isAuthorizerAccessTokenExpired(String appId) {
        return getTokenString(authorizerAccessTokens, appId) == null;
    }

    @Override
    public void expireAuthorizerAccessToken(String appId) {
        expireToken(authorizerAccessTokens, appId);
    }

//    @Override
//    public void updateAuthorizerAccessToken(String appId, WxOpenAuthorizerAccessToken authorizerAccessToken) {
//        updateAuthorizerAccessToken(appId, authorizerAccessToken.getAuthorizerAccessToken(),
//                authorizerAccessToken.getExpiresIn());
//    }

    @Override
    public void updateAuthorizerAccessToken(String appId, String authorizerAccessToken, int expiresInSeconds) {
        updateToken(authorizerAccessTokens, appId, authorizerAccessToken, expiresInSeconds);
    }

    @Override
    public String getJsapiTicket(String appId) {
        return getTokenString(jsapiTickets, appId);
    }

    @Override
    public boolean isJsapiTicketExpired(String appId) {
        return getTokenString(jsapiTickets, appId) == null;
    }

    @Override
    public void expireJsapiTicket(String appId) {
        expireToken(jsapiTickets, appId);
    }

    @Override
    public void updateJsapiTicket(String appId, String jsapiTicket, int expiresInSeconds) {
        updateToken(jsapiTickets, appId, jsapiTicket, expiresInSeconds);
    }

    @Override
    public String getCardApiTicket(String appId) {
        return getTokenString(cardApiTickets, appId);
    }

    @Override
    public boolean isCardApiTicketExpired(String appId) {
        return getTokenString(cardApiTickets, appId) == null;
    }

    @Override
    public void expireCardApiTicket(String appId) {
        expireToken(cardApiTickets, appId);
    }

    @Override
    public void updateCardApiTicket(String appId, String cardApiTicket, int expiresInSeconds) {
        updateToken(cardApiTickets, appId, cardApiTicket, expiresInSeconds);
    }

    @Data
    private static class Token {
        private String token;
        private Long expiresTime;
    }
}

package ink.kangaroo.douyin.open.api.impl;

import ink.kangaroo.douyin.common.error.DyErrorException;
import ink.kangaroo.douyin.common.util.http.HttpType;
import ink.kangaroo.douyin.common.util.http.SimpleGetRequestExecutor;
import ink.kangaroo.douyin.common.util.http.SimplePostRequestExecutor;
import ink.kangaroo.douyin.open.api.DyOpenComponentService;
import ink.kangaroo.douyin.open.api.DyOpenConfigStorage;

import java.net.CookieManager;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.http.HttpClient;

/**
 * @Classname DyOpenService
 * @Description TODO
 * @Date 2021/11/29 5:39
 * @Created by Kangaroo
 */
public class DyOpenServiceDefaultHttpClientImpl extends DyOpenServiceAbstractImpl<HttpClient, ProxySelector> {
    private HttpClient httpClient;
    private ProxySelector httpProxy;
    @Override
    public void initHttp() {
        DyOpenConfigStorage configStorage = this.getWxOpenConfigStorage();

        CookieManager cookieManager = new CookieManager();
        HttpClient.Builder builder = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)//设置http协议版本
                .followRedirects(HttpClient.Redirect.NORMAL)//当https协议访问不同的时候，要不要跳转到http协议
                .cookieHandler(cookieManager);//设置cookies管理器
        if (configStorage.getHttpProxyHost() != null && configStorage.getHttpProxyPort() != null) {
            builder.proxy(ProxySelector.of(new InetSocketAddress(configStorage.getHttpProxyHost(), configStorage.getHttpProxyPort() )));
        }
        this.httpClient = builder.build();

    }

    @Override
    public HttpClient getRequestHttpClient() {
        return httpClient;
    }

    @Override
    public ProxySelector getRequestHttpProxy() {
        return httpProxy;
    }

    @Override
    public HttpType getRequestType() {
        return null;
    }

    @Override
    public DyOpenComponentService getDyOpenComponentService() {
        return null;
    }

    @Override
    public DyOpenConfigStorage getWxOpenConfigStorage() {
        return null;
    }

    @Override
    public void setWxOpenConfigStorage(DyOpenConfigStorage wxOpenConfigStorage) {

    }

    @Override
    public String get(String url, String queryParam) throws DyErrorException {
        return execute(SimpleGetRequestExecutor.create(this), url, queryParam);
    }

    @Override
    public String post(String url, String postData) throws DyErrorException {
        return execute(SimplePostRequestExecutor.create(this), url, postData);
    }
}

package ink.kangaroo.douyin.common.util.http.jdk;

import ink.kangaroo.douyin.common.enums.DyType;
import ink.kangaroo.douyin.common.error.DyErrorException;
import ink.kangaroo.douyin.common.util.http.RequestHttp;
import ink.kangaroo.douyin.common.util.http.SimplePostRequestExecutor;

import java.io.IOException;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

/**
 * @Classname DyOpenService
 * @Description TODO
 * @Date 2021/11/29 5:39
 * @Created by Kangaroo
 */
public class DefaultSimplePostRequestExecutor  extends SimplePostRequestExecutor<HttpClient, ProxySelector> {
    public DefaultSimplePostRequestExecutor(RequestHttp<HttpClient, ProxySelector> requestHttp) {
        super(requestHttp);
    }

    @Override
    public String execute(String uri, String data, DyType dyType) throws DyErrorException, IOException {
        //得到httpClient

        HttpClient client = requestHttp.getRequestHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri)).POST(HttpRequest.BodyPublishers.ofString(data)).build();

        try {
            String body = (String) client.send(request, null).body();
            return this.handleResponse(dyType, body);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }
}

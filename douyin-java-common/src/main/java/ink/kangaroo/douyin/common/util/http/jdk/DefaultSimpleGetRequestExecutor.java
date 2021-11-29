package ink.kangaroo.douyin.common.util.http.jdk;

import ink.kangaroo.douyin.common.enums.DyType;
import ink.kangaroo.douyin.common.error.DyErrorException;
import ink.kangaroo.douyin.common.util.http.RequestHttp;
import ink.kangaroo.douyin.common.util.http.SimpleGetRequestExecutor;

import java.io.IOException;
import java.io.InputStream;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

/**
 * @Classname DyOpenService
 * @Description TODO
 * @Date 2021/11/29 5:39
 * @Created by Kangaroo
 */
public class DefaultSimpleGetRequestExecutor  extends SimpleGetRequestExecutor<HttpClient, ProxySelector> {
    public DefaultSimpleGetRequestExecutor(RequestHttp requestHttp) {
        super(requestHttp);
    }

    @Override
    public String execute(String uri, String queryParam, DyType wxType) throws DyErrorException, IOException {

        if (queryParam != null) {
            if (uri.indexOf('?') == -1) {
                uri += '?';
            }
            uri += uri.endsWith("?") ? queryParam : '&' + queryParam;
        }

        //得到httpClient

        HttpClient client = requestHttp.getRequestHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri)).GET().build();

        try {
            String body = (String) client.send(request, null).body();
            return this.handleResponse(wxType, body);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

}

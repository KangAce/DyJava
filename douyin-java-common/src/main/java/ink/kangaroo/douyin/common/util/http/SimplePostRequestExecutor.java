package ink.kangaroo.douyin.common.util.http;

import ink.kangaroo.douyin.common.enums.DyType;
import ink.kangaroo.douyin.common.error.DyError;
import ink.kangaroo.douyin.common.error.DyErrorException;
import ink.kangaroo.douyin.common.util.http.jdk.DefaultSimpleGetRequestExecutor;
import ink.kangaroo.douyin.common.util.http.jdk.DefaultSimplePostRequestExecutor;

import java.io.IOException;
import java.net.ProxySelector;
import java.net.http.HttpClient;

/**
 * @Classname DyOpenService
 * @Description
 * 简单的POST请求执行器，请求的参数是String, 返回的结果也是String
 *
 * @Date 2021/11/29 5:39
 * @Created by Kangaroo
 */
public abstract class SimplePostRequestExecutor<H, P> implements RequestExecutor<String, String> {

    protected RequestHttp<H, P> requestHttp;
    public SimplePostRequestExecutor(RequestHttp<H, P> requestHttp) {
        this.requestHttp = requestHttp;
    }


    @Override
    public void execute(String uri, String data, ResponseHandler<String> handler, DyType dyType) throws DyErrorException, IOException {
        handler.handle(this.execute(uri, data, dyType));
    }

    public static RequestExecutor<String, String> create(RequestHttp<?, ?> requestHttp) {
        switch (requestHttp.getRequestType()) {
//            case APACHE_HTTP:
//                return new ApacheSimpleGetRequestExecutor(requestHttp);
//            case JODD_HTTP:
//                return new JoddHttpSimpleGetRequestExecutor(requestHttp);
//            case OK_HTTP:
//                return new OkHttpSimpleGetRequestExecutor(requestHttp);
            case DEFAULT:
                return new DefaultSimplePostRequestExecutor((RequestHttp<HttpClient, ProxySelector>) requestHttp);
            default:
                throw new IllegalArgumentException("非法请求参数");
        }
    }

    protected String handleResponse(DyType wxType, String responseContent) throws DyErrorException {
        if (responseContent.isEmpty()) {
            throw new DyErrorException("无响应内容");
        }

        if (responseContent.startsWith("<xml>")) {
            //xml格式输出直接返回
            return responseContent;
        }

        DyError error = DyError.fromJson(responseContent, wxType);
        if (error.getErrorCode() != 0) {
            throw new DyErrorException(error);
        }
        return responseContent;
    }
}

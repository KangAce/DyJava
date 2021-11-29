package ink.kangaroo.douyin.common.util.http;

import ink.kangaroo.douyin.common.enums.DyType;
import ink.kangaroo.douyin.common.error.DyErrorException;

import java.io.IOException;

/**
 * @Classname DyOpenService
 * @Description http请求执行器.
 * @Date 2021/11/29 5:39
 * @Created by Kangaroo
 *
 *
 * @param <T> 返回值类型
 * @param <E> 请求参数类型
 */
public interface RequestExecutor<T, E>  {

    /**
     * 执行http请求.
     *
     * @param uri    uri
     * @param data   数据
     * @param dyType 抖音模块类型
     * @return 响应结果
     * @throws DyErrorException 自定义异常
     * @throws IOException      io异常
     */
    T execute(String uri, E data, DyType dyType) throws DyErrorException, IOException;

    /**
     * 执行http请求.
     *
     * @param uri     uri
     * @param data    数据
     * @param handler http响应处理器
     * @param wxType  微信模块类型
     * @throws DyErrorException 自定义异常
     * @throws IOException      io异常
     */
    void execute(String uri, E data, ResponseHandler<T> handler, DyType wxType) throws DyErrorException, IOException;
}

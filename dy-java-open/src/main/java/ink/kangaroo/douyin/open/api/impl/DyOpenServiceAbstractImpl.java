package ink.kangaroo.douyin.open.api.impl;

import ink.kangaroo.douyin.common.enums.DyType;
import ink.kangaroo.douyin.common.error.DyError;
import ink.kangaroo.douyin.common.error.DyErrorException;
import ink.kangaroo.douyin.common.error.DyRuntimeException;
import ink.kangaroo.douyin.common.util.http.RequestExecutor;
import ink.kangaroo.douyin.common.util.http.RequestHttp;
import ink.kangaroo.douyin.open.api.DyOpenComponentService;
import ink.kangaroo.douyin.open.api.DyOpenConfigStorage;
import ink.kangaroo.douyin.open.api.DyOpenService;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @Classname DyOpenService
 * @Description TODO
 * @Date 2021/11/29 5:39
 * @Created by Kangaroo
 */
@Slf4j
public abstract class DyOpenServiceAbstractImpl<H, P> implements DyOpenService, RequestHttp<H, P> {

    private DyOpenComponentService wxOpenComponentService = new DyOpenComponentServiceImpl(this);
    private DyOpenConfigStorage wxOpenConfigStorage;

    /**
     * 初始化 RequestHttp.
     */
    public abstract void initHttp();

    protected <T, E> T execute(RequestExecutor<T, E> executor, String uri, E data) throws DyErrorException {
        try {
            T result = executor.execute(uri, data, DyType.Open);
            log.debug("\n【请求地址】: {}\n【请求参数】：{}\n【响应数据】：{}", uri, data, result);
            return result;
        } catch (DyErrorException e) {
            DyError error = e.getError();
            if (error.getErrorCode() != 0) {
                log.error("\n【请求地址】: {}\n【请求参数】：{}\n【错误信息】：{}", uri, data, error);
                throw new DyErrorException(error, e);
            }
            return null;
        } catch (IOException e) {
            log.error("\n【请求地址】: {}\n【请求参数】：{}\n【异常信息】：{}", uri, data, e.getMessage());
            throw new DyRuntimeException(e);
        }
    }
}

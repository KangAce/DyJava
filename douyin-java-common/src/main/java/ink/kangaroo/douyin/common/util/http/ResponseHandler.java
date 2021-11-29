package ink.kangaroo.douyin.common.util.http;

/**
 * @Classname DyOpenService
 * @Description TODO
 * @Date 2021/11/29 5:39
 * @Created by Kangaroo
 */
public interface ResponseHandler<T> {
    /**
     * 响应结果处理.
     *
     * @param t 要处理的对象
     */
    void handle(T t);
}

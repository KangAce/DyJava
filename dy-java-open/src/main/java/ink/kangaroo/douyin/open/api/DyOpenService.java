package ink.kangaroo.douyin.open.api;

import ink.kangaroo.douyin.common.error.DyErrorException;

/**
 * @Classname DyOpenService
 * @Description TODO
 * @Date 2021/11/29 5:39
 * @Created by Kangaroo
 */
public interface DyOpenService {

    /**
     * Gets wx open component service.
     *
     * @return the wx open component service
     */
    DyOpenComponentService getDyOpenComponentService();

    /**
     * Gets wx open config storage.
     *
     * @return the wx open config storage
     */
    DyOpenConfigStorage getWxOpenConfigStorage();

    /**
     * Sets wx open config storage.
     *
     * @param wxOpenConfigStorage the wx open config storage
     */
    void setWxOpenConfigStorage(DyOpenConfigStorage wxOpenConfigStorage);

    /**
     * 当本Service没有实现某个API的时候，可以用这个，针对所有微信API中的GET请求
     *
     * @param url        the url
     * @param queryParam the query param
     * @return the string
     * @throws DyErrorException the wx error exception
     */
    String get(String url, String queryParam) throws DyErrorException;

    /**
     * 当本Service没有实现某个API的时候，可以用这个，针对所有微信API中的POST请求
     *
     * @param url      the url
     * @param postData the post data
     * @return the string
     * @throws DyErrorException the wx error exception
     */
    String post(String url, String postData) throws DyErrorException;
}

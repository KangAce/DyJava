package ink.kangaroo.spring.starter.dyjava.open.config;

import ink.kangaroo.douyin.open.api.DyOpenComponentService;
import ink.kangaroo.douyin.open.api.DyOpenConfigStorage;
import ink.kangaroo.douyin.open.api.DyOpenService;
import ink.kangaroo.douyin.open.api.impl.DyOpenServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @Classname DyOpenService
 * @Description TODO
 * @Date 2021/11/29 5:39
 * @Created by Kangaroo
 */
public class DyOpenServiceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(DyOpenConfigStorage.class)
    public DyOpenService wxOpenService(DyOpenConfigStorage wxOpenConfigStorage) {
        DyOpenService wxOpenService = new DyOpenServiceImpl();
        wxOpenService.setWxOpenConfigStorage(wxOpenConfigStorage);
        return wxOpenService;
    }

//    @Bean
//    public WxOpenMessageRouter wxOpenMessageRouter(DyOpenService wxOpenService) {
//        return new WxOpenMessageRouter(wxOpenService);
//    }

    @Bean
    public DyOpenComponentService wxOpenComponentService(DyOpenService wxOpenService) {
        return wxOpenService.getDyOpenComponentService();
    }
}

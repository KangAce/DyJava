package ink.kangaroo.douyin.common.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Classname DyOpenService
 * @Description TODO
 * @Date 2021/11/29 5:39
 * @Created by Kangaroo
 */
@Data
public class DyBaseResult<T> {
    @JSONField(name = "message")
    private String message;
    @JSONField(name = "data")
    private T data;
    @JSONField(name = "extra")
    private Extra extra;
}

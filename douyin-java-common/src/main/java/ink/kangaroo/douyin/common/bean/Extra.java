package ink.kangaroo.douyin.common.bean;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class Extra {

    @JSONField(name = "now")
    private Long now;

    @JSONField(name = "logid")
    private String logid;

}

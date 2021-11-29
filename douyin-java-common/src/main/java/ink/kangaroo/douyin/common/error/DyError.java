package ink.kangaroo.douyin.common.error;

import com.alibaba.fastjson.JSON;
import ink.kangaroo.douyin.common.enums.DyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @Classname DyError
 * @Description TODO
 * @Date 2021/11/29 5:39
 * @Created by Kangaroo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DyError implements Serializable {
    private static final long serialVersionUID = 7869786563361406291L;

    /**
     * 抖音错误代码.
     */
    private int errorCode;

    /**
     * 微信错误信息.
     * （如果可以翻译为中文，就为中文）
     */
    private String errorMsg;

    /**
     * 微信接口返回的错误原始信息（英文）.
     */
    private String errorMsgEn;

    private String json;

    public DyError(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public static DyError fromJson(String json) {
        return fromJson(json, null);
    }

    public static DyError fromJson(String json, DyType type) {

        final DyError wxError = JSON.parseObject(json, DyError.class);
        if (wxError.getErrorCode() == 0 || type == null) {
            return wxError;
        }

        if (StringUtils.isNotEmpty(wxError.getErrorMsg())) {
            wxError.setErrorMsgEn(wxError.getErrorMsg());
        }
//
//        switch (type) {
//            case MP: {
//                final String msg = WxMpErrorMsgEnum.findMsgByCode(wxError.getErrorCode());
//                if (msg != null) {
//                    wxError.setErrorMsg(msg);
//                }
//                break;
//            }
//            case CP: {
//                final String msg = WxCpErrorMsgEnum.findMsgByCode(wxError.getErrorCode());
//                if (msg != null) {
//                    wxError.setErrorMsg(msg);
//                }
//                break;
//            }
//            case MiniApp: {
//                final String msg = WxMaErrorMsgEnum.findMsgByCode(wxError.getErrorCode());
//                if (msg != null) {
//                    wxError.setErrorMsg(msg);
//                }
//                break;
//            }
//            default:
//                return wxError;
//        }

        return wxError;
    }

    @Override
    public String toString() {
        if (this.json == null) {
            return "错误代码：" + this.errorCode + ", 错误信息：" + this.errorMsg;
        }

        return "错误代码：" + this.errorCode + ", 错误信息：" + this.errorMsg + "，微信原始报文：" + this.json;
    }

}

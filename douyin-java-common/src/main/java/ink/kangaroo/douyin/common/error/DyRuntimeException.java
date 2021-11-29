package ink.kangaroo.douyin.common.error;

/**
 * @Classname DyOpenService
 * @Description DyJava专用的runtime exception.
 * @Date 2021/11/29 5:39
 * @Created by Kangaroo
 */
public class DyRuntimeException extends RuntimeException {
    private static final long serialVersionUID = 4881698471192264412L;

    public DyRuntimeException(Throwable e) {
        super(e);
    }

    public DyRuntimeException(String msg) {
        super(msg);
    }

    public DyRuntimeException(String msg, Throwable e) {
        super(msg, e);
    }
}

package ink.kangaroo.douyin.common.error;

/**
 * @Classname DyOpenService
 * @Description TODO
 * @Date 2021/11/29 5:39
 * @Created by Kangaroo
 */
public class DyErrorException extends Exception{
        private static final long serialVersionUID = -6357149550353160810L;

        private final DyError error;

        private static final int DEFAULT_ERROR_CODE = -99;

        public DyErrorException(String message) {
            this(DyError.builder().errorCode(DEFAULT_ERROR_CODE).errorMsg(message).build());
        }

        public DyErrorException(DyError error) {
            super(error.toString());
            this.error = error;
        }

        public DyErrorException(DyError error, Throwable cause) {
            super(error.toString(), cause);
            this.error = error;
        }

        public DyErrorException(Throwable cause) {
            super(cause.getMessage(), cause);
            this.error = DyError.builder().errorCode(DEFAULT_ERROR_CODE).errorMsg(cause.getMessage()).build();
        }

        public DyError getError() {
            return this.error;
        }
}

package top.lhlnb.backend.exception;

import top.lhlnb.backend.result.R;
import top.lhlnb.backend.result.SysResult;

/**
 * 服务器异常
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/10/29_0:29
 */
public class ServerException extends RuntimeException {

    protected SysResult expResult = SysResult.SERVER_ERROR;
    protected String code;

    public ServerException(String message, String code) {
        this(message);
        this.code = code;
    }

    public ServerException(String message) {
        super(message);
    }

    public ServerException(Throwable cause, String code) {
        super(cause);
        this.code = code;
    }

    public ServerException(Throwable cause) {
        super(cause);
    }

    public R<?> getResult() {
        Throwable target = this.getCause() != null ? this.getCause() : this;
        return R.error(expResult, code, getFormattedMessage(target));
    }

    /**
     * 获取格式化的异常消息
     *
     * @param e Throwable
     * @return 异常信息 (异常类名)
     */
    public static String getFormattedMessage(Throwable e) {
        return e.getMessage() + " (" + e.getClass().getSimpleName() + ')';
    }
}

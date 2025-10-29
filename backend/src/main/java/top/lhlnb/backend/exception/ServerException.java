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

    protected String code;
    protected String overwriteMsg;

    /**
     * 服务器异常（发消息）
     *
     * @param message 异常消息
     * @param code    业务码，若无则填空字符串
     */
    public ServerException(String message, String code) {
        this(message);
        this.code = code;
    }

    /**
     * 服务器异常（发消息）
     *
     * @param message 异常消息
     */
    public ServerException(String message) {
        super(message);
    }

    /**
     * 服务器异常（发异常）
     *
     * @param msg   异常消息（会覆盖默认的消息前缀）
     * @param cause 被包装的异常
     * @param code  业务码，若无则填空字符串
     */
    public ServerException(String msg, Throwable cause, String code) {
        super(cause);
        overwriteMsg = msg;
        this.code = code;
    }

    /**
     * 服务器异常（发异常）
     *
     * @param msg   异常消息（会覆盖默认的消息前缀）
     * @param cause 被包装的异常
     */
    public ServerException(String msg, Throwable cause) {
        super(cause);
        overwriteMsg = msg;
    }

    /**
     * 服务器异常（发异常）
     *
     * @param cause 被包装的异常
     * @param code  业务码，若无则填空字符串
     */
    public ServerException(Throwable cause, String code) {
        super(cause);
        this.code = code;
    }

    /**
     * 服务器异常（发异常）
     *
     * @param cause 被包装的异常
     */
    public ServerException(Throwable cause) {
        super(cause);
    }

    /**
     * 获取服务器异常对应的 Result 对象
     */
    public R<?> getResult() {
        Throwable target = this.getCause() != null ? this.getCause() : this;
        return R.error(getExpResult(), code, getFormattedMessage(target)).overwriteMsgPrefix(overwriteMsg);
    }

    /**
     * 获取服务器异常对应的异常 Result，可以重写此方法来自定义
     */
    public SysResult getExpResult() {
        return SysResult.INTERNAL_SERVER_ERROR;
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

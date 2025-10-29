package top.lhlnb.backend.exception;

import top.lhlnb.backend.result.R;
import top.lhlnb.backend.result.SysResult;

/**
 * 参数异常
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/10/29_1:07
 */
public class ArgumentException extends ServerException {

    public ArgumentException(String message, String code) {
        super(message, code);
    }

    public ArgumentException(String message) {
        super(message);
    }

    public ArgumentException(String msg, Throwable cause, String code) {
        super(msg, cause, code);
    }

    public ArgumentException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ArgumentException(Throwable cause, String code) {
        super(cause, code);
    }

    public ArgumentException(Throwable cause) {
        super(cause);
    }

    @Override
    public SysResult getExpResult() {
        return SysResult.BAD_REQUEST;
    }

    @Override
    public R<?> getResult() {
        return super.getResult().overwriteMsgPrefix("参数异常").overwriteMsgPrefix(overwriteMsg);
    }
}

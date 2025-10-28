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

    private String overwriteMsg;


    public ArgumentException(String message, String code) {
        super(message, code);
        expResult = SysResult.FAIL;
    }

    public ArgumentException(String message) {
        super(message);
        expResult = SysResult.FAIL;
    }

    public ArgumentException(String msg, Throwable cause, String code) {
        super(cause, code);
        expResult = SysResult.FAIL;
        overwriteMsg = msg;
    }

    public ArgumentException(Throwable cause, String code) {
        super(cause, code);
        expResult = SysResult.FAIL;
    }

    public ArgumentException(Throwable cause) {
        super(cause);
        expResult = SysResult.FAIL;
    }

    @Override
    public R<?> getResult() {
        return super.getResult().overwriteMsgPrefix(overwriteMsg);
    }
}

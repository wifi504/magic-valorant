package top.lhlnb.backend.result;

import lombok.Getter;

/**
 * 系统内置 Result
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/10/28_17:51
 */

@Getter
public enum SysResult {

    SUCCESS(200, "请求成功"),
    FAIL(400, "请求失败"),
    UNAUTHORIZED(401, "无效认证"),
    FORBIDDEN(403, "拒绝访问"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不允许"),
    SERVER_ERROR(500, "服务器异常");

    private final int code;
    private final String msg;

    SysResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

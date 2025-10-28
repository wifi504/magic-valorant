package top.lhlnb.backend.domain.result;

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

    SUCCESS(200, "请求成功");

    private final int code;
    private final String msg;

    SysResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

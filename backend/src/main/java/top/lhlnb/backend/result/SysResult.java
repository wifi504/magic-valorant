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

    BAD_REQUEST(400, "无效请求"),
    UNAUTHORIZED(401, "无效认证"),
    PAYMENT_REQUIRED(402, "需要付费"),
    FORBIDDEN(403, "拒绝访问"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不允许"),
    NOT_ACCEPTABLE(406, "无法返回请求的预期响应"),
    PROXY_AUTHENTICATION_REQUIRED(407, "需要代理认证"),
    REQUEST_TIMEOUT(408, "请求超时"),
    CONFLICT(409, "请求冲突"),
    GONE(410, "资源不再可用"),
    LENGTH_REQUIRED(411, "需指定 Content-Length 请求头"),
    PRECONDITION_FAILED(412, "预处理失败"),
    PAYLOAD_TOO_LARGE(413, "请求体过大"),
    URI_TOO_LONG(414, "URI 过长"),
    UNSUPPORTED_MEDIA_TYPE(415, "不支持的媒体类型"),
    RANGE_NOT_SATISFIABLE(416, "范围无效"),
    EXPECTATION_FAILED(417, "无法满足 Expect 请求头要求"),
    I_AM_A_TEAPOT(418, "我是一个茶壶，愚人节快乐~"),
    MISDIRECTED_REQUEST(421, "请求被错误导向"),
    UNPROCESSABLE_ENTITY(422, "无法处理的实体，请求语义无效"),
    LOCKED(423, "资源被锁定"),
    FAILED_DEPENDENCY(424, "依赖的前序请求失败"),
    TOO_EARLY(425, "过早请求"),
    UPGRADE_REQUIRED(426, "需升级协议"),
    PRECONDITION_REQUIRED(428, "缺失请求头"),
    TOO_MANY_REQUESTS(429, "请求过于频繁"),
    REQUEST_HEADER_FIELDS_TOO_LARGE(431, "请求头过大"),
    UNAVAILABLE_FOR_LEGAL_REASONS(451, "因法律原因不可用"),

    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    NOT_IMPLEMENTED(501, "服务未实现"),
    BAD_GATEWAY(502, "网关错误"),
    SERVICE_UNAVAILABLE(503, "服务暂时不可用"),
    GATEWAY_TIMEOUT(504, "网关超时"),
    HTTP_VERSION_NOT_SUPPORTED(505, "HTTP 版本不支持"),
    VARIANT_ALSO_NEGOTIATES(506, "变体协商循环"),
    INSUFFICIENT_STORAGE(507, "存储不足"),
    LOOP_DETECTED(508, "检测到循环"),
    NOT_EXTENDED(510, "缺失扩展"),
    NETWORK_AUTHENTICATION_REQUIRED(511, "需网络认证");

    private final int code;
    private final String msg;

    SysResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}

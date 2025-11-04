package top.lhlnb.backend.domain.dto.login;

import lombok.Data;

/**
 * 微信登录会话信息
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/11/4_17:33
 */
@Data
public class WXSessionDTO {
    // 会话密钥
    private String session_key;
    // 用户在开放平台的唯一标识符，若当前小程序已绑定到微信开放平台帐号下会返回
    private String unionid;
    // 错误信息，请求失败时返回
    private String errmsg;
    // 用户唯一标识
    private String openid;
    // 错误码，请求失败时返回
    private Integer errcode;
}

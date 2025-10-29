package top.lhlnb.backend.service;

import top.lhlnb.backend.domain.dto.login.EmailLoginDto;
import top.lhlnb.backend.result.R;

/**
 * 登录服务
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/10/29_13:36
 */
public interface LoginService {

    /**
     * 邮箱登录
     *
     * @param emailLoginDto EmailLoginDto
     */
    R<?> doEmailLogin(EmailLoginDto emailLoginDto);


    /**
     * 邮箱注册
     *
     * @param emailLoginDto EmailLoginDto
     */
    R<?> registerByEmail(EmailLoginDto emailLoginDto);
}

package top.lhlnb.backend.controller._public;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import top.lhlnb.backend.domain.dto.login.EmailLoginDto;
import top.lhlnb.backend.result.R;
import top.lhlnb.backend.service.LoginService;

/**
 * 登录控制器（邮箱、微信、验证码等）
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/10/29_9:46
 */

@RestController
@RequestMapping("/api")
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping("/login")
    public R<?> login(@Valid @RequestBody EmailLoginDto emailLoginDto) {
        return loginService.doEmailLogin(emailLoginDto);
    }

    /**
     * 微信登录，返回用户登录令牌
     *
     * @param code 微信登录码
     */
    @GetMapping("/wechatLogin")
    public R<?> wechatLogin(@RequestParam String code) {
        return loginService.doWechatLogin(code);
    }

    @PostMapping("/register")
    public R<?> register(@Valid @RequestBody EmailLoginDto emailLoginDto) {
        return loginService.registerByEmail(emailLoginDto);
    }
}

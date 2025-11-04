package top.lhlnb.backend.service.impl;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import top.lhlnb.backend.domain.dto.login.EmailLoginDto;
import top.lhlnb.backend.domain.entity.TUser;
import top.lhlnb.backend.mapper.TUserMapper;
import top.lhlnb.backend.result.R;
import top.lhlnb.backend.result.SysResult;
import top.lhlnb.backend.service.LoginService;
import top.lhlnb.backend.util.TokenUtil;
import top.lhlnb.backend.util.TxUtil;
import top.lhlnb.backend.util.WXServerUtil;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/10/29_13:37
 */

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private TUserMapper tUserMapper;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Resource
    private TokenUtil tokenUtil;

    @Resource
    private WXServerUtil wxServerUtil;

    @Resource
    private TxUtil txUtil;

    /**
     * 邮箱登录
     *
     * @param emailLoginDto EmailLoginDto
     */
    @Override
    public R<?> doEmailLogin(EmailLoginDto emailLoginDto) {
        // 校验验证码
        if (!tokenUtil.consumeToken(emailLoginDto.getToken())) {
            return R.error(SysResult.BAD_REQUEST, "请完成验证码校验");
        }
        return R.error(SysResult.NOT_IMPLEMENTED);
    }

    /**
     * 邮箱注册
     *
     * @param emailLoginDto EmailLoginDto
     */
    @Override
    public R<?> registerByEmail(EmailLoginDto emailLoginDto) {
        return R.error(SysResult.NOT_IMPLEMENTED);
    }

    /**
     * 微信登录
     *
     * @param code 微信登录码
     */
    @Override
    public R<?> doWechatLogin(String code) {
        // 1. 校验微信登录码
        if (code == null || code.isEmpty()) {
            return R.error(SysResult.BAD_REQUEST, "请提供微信登录码");
        }
        // 2. 获取微信登录信息
        String openId = wxServerUtil.getUserOpenIdByCode(code);
        // 3. 数据库查询此用户是否存在
        long userId = txUtil.runInTx(() -> {
            TUser tUser = tUserMapper.selectByWxOpenId(openId);
            // 3.1 用户存在，直接返回
            if (tUser != null) {
                return tUser.getId();
            }
            // 3.2 用户不存在，创建用户
            return -1L;
        });
        return R.ok(userId);
    }
}

package top.lhlnb.backend.service.impl;

import cn.hutool.core.util.RandomUtil;
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
        // 2.1 获取微信用户OpenId，如果为空则登录失败
        if (openId == null || openId.isEmpty()) {
            return R.error(SysResult.BAD_REQUEST, "微信登录失败，无效的微信登录码");
        }
        // 3. 数据库查询此用户是否存在
        long userId = txUtil.runInTx(() -> {
            TUser tUser = tUserMapper.selectByWxOpenId(openId);
            // 3.1 用户存在，直接返回用户ID
            if (tUser != null) {
                return tUser.getId();
            }
            // 3.2 用户不存在，创建用户
            tUser = TUser.builder()
                    .wxOpenid(openId)
                    .nickname("瓦学弟_" + RandomUtil.randomString(4))
                    .build();
            tUserMapper.insertSelective(tUser);
            // 3.3 返回用户ID
            return tUser.getId();
        });
        // 4. 如果用户ID大于0，则执行用户登录
        if (userId > 0) {
            String loginToken = tokenUtil.createLoginToken(userId);
            // 4.1 返回用户登录令牌
            return R.ok(loginToken);
        }
        return R.error(SysResult.INTERNAL_SERVER_ERROR);
    }
}

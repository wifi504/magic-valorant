package top.lhlnb.backend.service.impl;

import cn.hutool.core.util.RandomUtil;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import top.lhlnb.backend.domain.dto.login.EmailLoginDto;
import top.lhlnb.backend.domain.entity.TUser;
import top.lhlnb.backend.exception.ServerException;
import top.lhlnb.backend.mapper.TUserMapper;
import top.lhlnb.backend.result.R;
import top.lhlnb.backend.result.SysResult;
import top.lhlnb.backend.service.LoginService;
import top.lhlnb.backend.util.TokenUtil;

/**
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/10/29_13:37
 */

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private TUserMapper tUserMapper;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Resource
    private TokenUtil tokenUtil;

    /**
     * 邮箱登录
     *
     * @param emailLoginDto EmailLoginDto
     */
    @Override
    public R<?> doEmailLogin(EmailLoginDto emailLoginDto) {
        return R.error(SysResult.NOT_IMPLEMENTED);
    }

    /**
     * 邮箱注册
     *
     * @param emailLoginDto EmailLoginDto
     */
    @Override
    public R<?> registerByEmail(EmailLoginDto emailLoginDto) {
        TUser tUser = TUser.builder()
                .email(emailLoginDto.getEmail())
                .password(passwordEncoder.encode(emailLoginDto.getPassword()))
                .nickname("瓦学弟_" + RandomUtil.randomString(4))
                .build();
        int i = tUserMapper.insertSelective(tUser);
        if (i != 1) {
            throw new ServerException("用户创建失败");
        }
        // 给用户签发 Token
        String token = tokenUtil.createLoginToken(tUser.getId());
        return R.ok(token);
    }
}

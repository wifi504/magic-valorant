package top.lhlnb.backend.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.JWTValidator;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import org.springframework.stereotype.Component;
import top.lhlnb.backend.config.JwtConfig;
import top.lhlnb.backend.exception.ServerException;

import java.util.Date;

/**
 * JWT 工具类
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/10/28_17:45
 */
@Component
public class JwtUtil {
    private final JwtConfig jwtConfig;

    public JwtUtil(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    /**
     * 签名用户JWT
     *
     * @param userId 用户ID
     */
    public String createToken(Long userId) {
        JWTSigner signer = JWTSignerUtil.hs256(jwtConfig.getSecret().getBytes());
        return JWT.create()
                .setPayload("id", userId)
                .setIssuedAt(new Date())
                .setExpiresAt(new Date(System.currentTimeMillis() + (jwtConfig.getTtl() * 1000L)))
                .sign(signer);
    }

    /**
     * 解析JWT中的用户ID
     *
     * @param token JWT
     * @return Long 用户ID
     */
    public Long getUserId(String token) {
        Object id = JWTUtil.parseToken(token).getPayload("id");
        try {
            return Long.parseLong(id.toString());
        } catch (NumberFormatException e) {
            throw new ServerException("令牌解析异常，请尝试重新登录！");
        }
    }


    /**
     * 验证 Token
     *
     * @param token Token
     */
    public boolean verify(String token) {
        try {
            JWTValidator.of(token)
                    .validateAlgorithm(JWTSignerUtil.hs256(jwtConfig.getSecret().getBytes()))
                    .validateDate(DateUtil.date());
        } catch (ValidateException e) {
            return false;
        }
        return true;
    }
}

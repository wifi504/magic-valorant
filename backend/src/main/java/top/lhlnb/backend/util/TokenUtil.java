package top.lhlnb.backend.util;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.DigestUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import top.lhlnb.backend.config.TokenConfig;
import top.lhlnb.backend.exception.ArgumentException;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Token 工具类
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/10/31_16:09
 */
@Component
public class TokenUtil {

    public static final String USER_PERMISSIONS_PREFIX = "permissions";
    public static final String USER_TOKENS_HOLDER_PREFIX = "user";

    @Resource
    private TokenConfig config;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 创建用户登录令牌
     *
     * @param id 用户ID
     */
    public String createLoginToken(Long id) {
        if (id == null) throw new ArgumentException("创建 token 失败，id不能为空！");
        // 生成新 token
        TokenPair tokenPair = genTokenPair();
        String key = config.getRedisPrefix() + ':' + tokenPair.getHash();
        // key -> id
        stringRedisTemplate.opsForValue().set(
                key,
                id.toString(),
                config.getLoginTtl(),
                TimeUnit.SECONDS);

        // id -> keys
        stringRedisTemplate.opsForList().rightPush(
                config.getRedisPrefix() + ':' + USER_TOKENS_HOLDER_PREFIX + ':' + id,
                key
        );
        return tokenPair.getToken();
    }

    /**
     * 删除指定登录令牌
     *
     * @param token 登录令牌
     */
    public void deleteLoginToken(String token) {
        String key = config.getRedisPrefix() + ':' + token;
        stringRedisTemplate.delete(key);
    }

    /**
     * 删除指定用户的所有登录令牌
     *
     * @param id 用户ID
     */
    public void deleteAllLoginToken(Long id) {
        String key = config.getRedisPrefix() + ':' + USER_TOKENS_HOLDER_PREFIX + ':' + id;
        List<String> list = stringRedisTemplate.opsForList().range(key, 0, -1);
        if (list == null) return;
        list.stream().filter(Objects::nonNull).forEach(token -> stringRedisTemplate.delete(token));
    }

    /**
     * 生成一个 token 哈希对
     */
    private static TokenPair genTokenPair() {
        String uuid = IdUtil.fastSimpleUUID();
        String objId = IdUtil.objectId();
        String token = uuid.substring(0, 30) + objId.substring(4, 24);
        String sha256 = DigestUtil.sha256Hex(token);
        return new TokenPair(token, sha256);
    }

    /**
     * 从 http 请求体中解析 token 的哈希
     *
     * @param request http 请求体
     * @return token hash
     */
    public static String resolveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return DigestUtil.sha256Hex(token.substring(7));
        }
        return null;
    }

    /**
     * Token 哈希对
     */
    @Getter
    @AllArgsConstructor
    private static class TokenPair {
        private String token;
        private String hash;
    }
}

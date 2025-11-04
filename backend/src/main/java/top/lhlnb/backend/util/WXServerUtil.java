package top.lhlnb.backend.util;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.lhlnb.backend.config.MiniAppConfig;
import top.lhlnb.backend.domain.dto.login.WXSessionDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 微信服务端通信工具
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/11/4_16:32
 */

@Slf4j
@Component
public class WXServerUtil {

    private static final String ACCESS_TOKEN_REDIS_KEY = "wx:accessToken";
    private static final String ACCESS_TOKEN_TOTAL_TTL_REDIS_KEY = "wx:accessTokenTotalTTL";
    private static final String GET_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token";
    private static final String CODE_2_SESSION = "https://api.weixin.qq.com/sns/jscode2session";

    @Resource
    private MiniAppConfig miniAppConfig;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 检查并刷新微信令牌
     */
    public void checkAndRefreshToken() {
        // 1. 先尝试获取缓存的微信令牌
        long currentTtl = 0;
        long totalTtl = 0;
        try {
            currentTtl = stringRedisTemplate.getExpire(ACCESS_TOKEN_REDIS_KEY, TimeUnit.SECONDS);
            totalTtl = Long.parseLong(stringRedisTemplate.opsForValue().get(ACCESS_TOKEN_TOTAL_TTL_REDIS_KEY));
        } catch (Exception ignore) {
        }
        // 2. 如果缓存令牌不存在，或者缓存令牌有效期剩余不到 1/10 ，则请求新的微信令牌并保存
        if (totalTtl == 0 || currentTtl == 0 || currentTtl < totalTtl / 10) {
            fetchAccessToken();
        }
        String accessToken = stringRedisTemplate.opsForValue().get(ACCESS_TOKEN_REDIS_KEY);
        String expire = stringRedisTemplate.getExpire(ACCESS_TOKEN_REDIS_KEY, TimeUnit.SECONDS) + "";
        String total = stringRedisTemplate.opsForValue().get(ACCESS_TOKEN_TOTAL_TTL_REDIS_KEY);
        log.info("【微信服务端令牌】有效期：{}/{}秒，当前值：{}", expire, total, accessToken);
    }

    @Scheduled(fixedDelay = 3 * 60 * 1000)
    public void refreshToken() {
        checkAndRefreshToken();
    }

    /**
     * 请求新的微信令牌并保存
     */
    private void fetchAccessToken() {
        // 1. 从配置文件读取小程序的appId和appSecret
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("grant_type", "client_credential");
        paramMap.put("appid", miniAppConfig.getAppId());
        paramMap.put("secret", miniAppConfig.getAppSecret());
        try {
            // 2. 发送请求，获取微信令牌
            String res = HttpUtil.get(GET_ACCESS_TOKEN, paramMap, 5000);
            JSONObject jsonRes = JSONUtil.parseObj(res);
            String accessToken = jsonRes.getStr("access_token");
            int expiresIn = Integer.parseInt(jsonRes.getStr("expires_in"));
            // 3. 保存微信令牌
            stringRedisTemplate.opsForValue().set(ACCESS_TOKEN_REDIS_KEY, accessToken, (int) (expiresIn * 0.9), TimeUnit.SECONDS);
            stringRedisTemplate.opsForValue().set(ACCESS_TOKEN_TOTAL_TTL_REDIS_KEY, String.valueOf((int) (expiresIn * 0.9)));
        } catch (Exception ignore) {
        }
    }

    /**
     * 通过微信登录码获取微信用户OpenId
     *
     * @param code 微信登录码
     * @return 微信用户OpenId
     */
    public String getUserOpenIdByCode(String code) {
        // 1. 构造参数
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("appid", miniAppConfig.getAppId());
        paramMap.put("secret", miniAppConfig.getAppSecret());
        paramMap.put("js_code", code);
        paramMap.put("grant_type", "authorization_code");
        // 2. 发送请求，获取微信用户会话
        String res = HttpUtil.get(CODE_2_SESSION, paramMap, 5000);
        WXSessionDTO wxSessionDTO = JSONUtil.toBean(res, WXSessionDTO.class);
        return wxSessionDTO.getOpenid();
    }
}

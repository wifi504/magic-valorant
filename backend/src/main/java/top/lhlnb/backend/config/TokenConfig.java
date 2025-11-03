package top.lhlnb.backend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/10/31_16:36
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "token")
public class TokenConfig {
    // Token 在Redis中存放的前缀
    private String redisPrefix = "token";
    // 登录令牌有效期（秒）
    private Long loginTtl = 60L;
    // 临时令牌有效期
    private Long tempTtl = 5L;
}

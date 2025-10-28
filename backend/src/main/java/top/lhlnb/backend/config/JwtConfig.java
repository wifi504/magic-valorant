package top.lhlnb.backend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * JWT 配置类
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/10/28_23:45
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    // JWT 密钥
    private String secret = "secret";
    // 有效期（秒）
    private int ttl = 0;
}

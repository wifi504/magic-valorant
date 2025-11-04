package top.lhlnb.backend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 小程序配置
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/11/4_16:17
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "mini-app")
public class MiniAppConfig {
    private String appId;
    private String appSecret;
}

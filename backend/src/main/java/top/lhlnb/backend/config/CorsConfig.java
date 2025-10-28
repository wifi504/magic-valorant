package top.lhlnb.backend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;
import java.util.List;

/**
 * 跨域配置
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/10/28_13:23
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "cors")
public class CorsConfig {
    // 允许哪些源访问
    private List<String> allowedOrigins = Collections.emptyList();
    // 允许哪些方法
    private List<String> allowedMethods = Collections.emptyList();
    // 允许携带哪些头部信息
    private List<String> allowedHeaders = Collections.emptyList();
    // 允许携带 Cookies
    private Boolean allowCredentials = false;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        if (allowedOrigins.contains("*") && Boolean.TRUE.equals(allowCredentials)) {
            throw new IllegalStateException("当 allowedOrigins 为'*'时，无法将 allowCredentials 设置为 true！");
        }

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(allowedOrigins);
        configuration.setAllowedMethods(allowedMethods);
        configuration.setAllowedHeaders(allowedHeaders);
        configuration.setAllowCredentials(allowCredentials);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}

package top.lhlnb.backend.config;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;
import top.lhlnb.backend.security.entrypoint.SendResultAuthenticationEntryPoint;
import top.lhlnb.backend.security.filter.JwtAuthenticationFilter;
import top.lhlnb.backend.security.handler.SendResultAccessDeniedHandler;

/**
 * Spring Security 配置
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/10/28_13:46
 */

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Resource
    private CorsConfigurationSource corsConfigurationSource;
    @Resource
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Resource
    private SendResultAuthenticationEntryPoint sendResultAuthenticationEntryPoint;
    @Resource
    private SendResultAccessDeniedHandler sendResultAccessDeniedHandler;

    // Spring Security 核心配置
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // 禁用跨站请求伪造
                .csrf(AbstractHttpConfigurer::disable)
                // 配置跨域
                .cors(configurer -> configurer.configurationSource(corsConfigurationSource))
                // 禁用 Session
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // HTTP 请求鉴权
                .authorizeHttpRequests(authRegistry -> authRegistry
                        // 放行公开接口（一级路径接口、public接口，如登录等）
                        .requestMatchers("/api/*").permitAll()
                        .requestMatchers("/api/public/**").permitAll()
                        // 其他接口需要认证
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(configurer -> configurer
                        // 认证失败处理器（未登录）
                        .authenticationEntryPoint(sendResultAuthenticationEntryPoint)
                        // 权限不足处理器（拒绝访问）
                        .accessDeniedHandler(sendResultAccessDeniedHandler)
                ).build();
    }
}

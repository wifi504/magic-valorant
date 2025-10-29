package top.lhlnb.backend.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/10/29_13:55
 */
@Configuration
@MapperScan("top.lhlnb.backend.mapper")
public class MyBatisConfig {
}

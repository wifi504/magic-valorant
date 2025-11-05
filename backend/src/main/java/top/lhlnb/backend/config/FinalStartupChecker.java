package top.lhlnb.backend.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 启动检查
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/11/5_20:33
 */

@Slf4j
@Component
public class FinalStartupChecker {

    private final ApplicationContext context;

    public FinalStartupChecker(ApplicationContext context) {
        this.context = context;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onReady() {
        try {
            JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
            StringRedisTemplate redisTemplate = context.getBean(StringRedisTemplate.class);

            log.info("【启动检查】正在检测 MySQL 连接...");
            jdbcTemplate.execute("SELECT 1");
            log.info("【启动检查】MySQL 正常。");

            log.info("【启动检查】正在检测 Redis 连接...");
            redisTemplate.opsForValue().set("startup:test", "ok");
            log.info("RedisTemplate delete the key \"startup:test\": {}", redisTemplate.delete("startup:test"));
            log.info("【启动检查】Redis 正常。");
        } catch (Exception e) {
            log.error("【启动检查】失败：{}", e.getMessage());
            SpringApplication.exit(context, () -> 1);
            System.exit(1);
        }
    }
}

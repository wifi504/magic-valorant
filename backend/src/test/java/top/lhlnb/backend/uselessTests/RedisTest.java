package top.lhlnb.backend.uselessTests;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author lhl
 * @version 1.0
 * Create Time 2025/10/23_16:46
 */
@Slf4j
@SpringBootTest
public class RedisTest {


    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    void testString() {
        redisTemplate.opsForValue().set("name", "114514");
        String name = redisTemplate.opsForValue().get("name");
        log.info(name);
    }
}

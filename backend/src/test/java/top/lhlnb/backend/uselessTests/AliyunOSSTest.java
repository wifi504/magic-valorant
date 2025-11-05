package top.lhlnb.backend.uselessTests;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.lhlnb.backend.util.AliyunOSSUtil;

/**
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/11/5_13:06
 */

@Slf4j
@SpringBootTest
public class AliyunOSSTest {

    @Resource
    private AliyunOSSUtil aliyunOSSUtil;

    @Test
    public void test() {
        log.info(aliyunOSSUtil.generatePresignedUrl("test.png", 20));
    }
}

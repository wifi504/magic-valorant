package top.lhlnb.backend.uselessTests;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.lhlnb.backend.exception.ArgumentException;
import top.lhlnb.backend.exception.ServerException;
import top.lhlnb.backend.result.R;
import top.lhlnb.backend.result.SysResult;

/**
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/10/28_22:05
 */
@Slf4j
@SpringBootTest
public class RTest {

    @Test
    public void test() {
        log.info(R.error(SysResult.FORBIDDEN, "001", "你说得对").toString());
    }

    @Test
    public void testException() {
        log.error(new ArgumentException("哦耶~", "301*").getResult().toString());
        try {
            Integer.parseInt("abc");
        } catch (NumberFormatException e) {
            log.error(new ArgumentException(e).getResult().toString());
        }
    }
}

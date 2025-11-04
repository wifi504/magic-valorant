package top.lhlnb.backend.uselessTests;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.lhlnb.backend.util.WXServerUtil;

/**
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/11/4_16:38
 */
@Slf4j
@SpringBootTest
public class WXServerUtilTest {

    @Resource
    private WXServerUtil wxServerUtil;

    @Test
    public void testGetAccessToken() {
//        String res = wxServerUtil.getAccessToken();
//        JSONObject jsonRes = JSONUtil.parseObj(res);
//        log.info(jsonRes.getStr("access_token"));
//        log.info(jsonRes.getStr("expires_in"));
    }
}

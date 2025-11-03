package top.lhlnb.backend.uselessTests;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.DigestUtil;
import org.junit.jupiter.api.Test;

/**
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/10/31_16:16
 */
//@SpringBootTest
public class TokenTest {

    @Test
    public void testToken() {
        String objId = IdUtil.objectId();
        String uuid = IdUtil.fastSimpleUUID();
        System.out.println("长度：" + objId.length() + "；" + uuid.length());
        System.out.println(objId);
        System.out.println(uuid);
        String token = uuid.substring(0, 30) + objId.substring(4, 24);
        System.out.println(token + "; 长度：" + token.length());
        String sha256 = DigestUtil.sha256Hex(token);
        System.out.println(sha256 + "; 长度：" + sha256.length());
    }
}

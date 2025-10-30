package top.lhlnb.backend.controller._public;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lhlnb.backend.result.R;
import top.lhlnb.backend.result.SysResult;

/**
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/10/30_14:21
 */

@RestController
@RequestMapping("/api")
public class CaptchaController {

    @GetMapping("/genCaptcha")
    public R<?> genCaptcha() {
        return R.error(SysResult.NOT_IMPLEMENTED);
    }
}

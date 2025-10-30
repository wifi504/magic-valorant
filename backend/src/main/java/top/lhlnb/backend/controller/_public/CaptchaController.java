package top.lhlnb.backend.controller._public;

import cloud.tianai.captcha.application.ImageCaptchaApplication;
import cloud.tianai.captcha.common.constant.CaptchaTypeConstant;
import cloud.tianai.captcha.common.response.ApiResponse;
import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

/**
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/10/30_14:21
 */

@RestController
@RequestMapping("/api/public/captcha")
public class CaptchaController {

    @Resource
    private ImageCaptchaApplication application;

    /**
     * 生成验证码
     */
    @PostMapping("/gen")
    public ApiResponse<?> genCaptcha() {
        return application.generateCaptcha(CaptchaTypeConstant.SLIDER);
    }

    /**
     * 验证码校验
     *
     * @param data { id, data }
     */
    @PostMapping("/check")
    public ApiResponse<?> checkCaptcha(@RequestBody Data data) {
        ApiResponse<?> response = application.matching(data.getId(), data.getData());
        if (response.isSuccess()) {
            // 验证码验证成功，此处应该进行自定义业务处理，或者返回验证token进行二次验证等。
            return ApiResponse.ofSuccess(Collections.singletonMap("validToken", data.getId()));
        }
        return response;
    }

    @lombok.Data
    public static class Data {
        // 验证码id
        private String id;
        // 验证码数据
        private ImageCaptchaTrack data;
        // 可以加用户自定义业务参数...
    }
}

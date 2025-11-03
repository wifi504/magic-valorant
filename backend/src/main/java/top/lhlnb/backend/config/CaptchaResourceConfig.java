package top.lhlnb.backend.config;

import cloud.tianai.captcha.common.constant.CaptchaTypeConstant;
import cloud.tianai.captcha.resource.ResourceStore;
import cloud.tianai.captcha.resource.common.model.dto.Resource;
import cloud.tianai.captcha.resource.CrudResourceStore;
import cloud.tianai.captcha.resource.common.model.dto.ResourceMap;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

/**
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/10/30_23:47
 */
@Configuration
@RequiredArgsConstructor
public class CaptchaResourceConfig {

    private final ResourceStore resourceStore;

    @PostConstruct
    public void init() {
        CrudResourceStore store = (CrudResourceStore) resourceStore;

        // 配置滑块底图和滑块的源文件(png)(110x110)
        int totalMaskImages = 41;
        for (int i = 1; i <= totalMaskImages; i++) {
            ResourceMap template = new ResourceMap("default", 4);
            template.put("active.png", new Resource("classpath", "captcha/mask/" + i + ".png", "default"));
            template.put("fixed.png", new Resource("classpath", "captcha/mask/" + i + "_bg.png", "default"));
            store.addTemplate(CaptchaTypeConstant.SLIDER, template);
        }

        // 配置背景图(jpg)(600x360)
        int totalBgImages = 25;
        for (int bgImg = 1; bgImg <= totalBgImages; bgImg++) {
            store.addResource(CaptchaTypeConstant.SLIDER, new Resource("classpath", "captcha/" + bgImg + ".jpg", "default"));
        }
    }
}

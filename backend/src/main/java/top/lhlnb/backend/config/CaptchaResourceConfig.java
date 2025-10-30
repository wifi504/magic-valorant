package top.lhlnb.backend.config;

import cloud.tianai.captcha.common.constant.CaptchaTypeConstant;
import cloud.tianai.captcha.resource.ResourceStore;
import cloud.tianai.captcha.resource.common.model.dto.Resource;
import cloud.tianai.captcha.resource.CrudResourceStore;
import cloud.tianai.captcha.resource.common.model.dto.ResourceMap;
import cloud.tianai.captcha.resource.impl.provider.ClassPathResourceProvider;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import static cloud.tianai.captcha.common.constant.CommonConstant.DEFAULT_SLIDER_IMAGE_TEMPLATE_PATH;

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

        // 滑块验证码 模板 (系统内置) ,这里添加的模板等同于  captcha.init-default-resource=true , 如果配置中设置了加载默认模板，这里模板可不用配置
        ResourceMap template1 = new ResourceMap("default", 4);
        template1.put("active.png", new Resource("classpath", "captcha/mask/active_1.png", "default"));
        template1.put("fixed.png", new Resource("classpath", "captcha/mask/fixed_1.png", "default"));

        // 添加模板
        store.addTemplate(CaptchaTypeConstant.SLIDER, template1);

        // 添加自定义背景图片, resource 的参数1为资源类型(默认支持 classpath/file/url ), resource 的参数2为资源路径, resource 的参数3为标签
        store.addResource(CaptchaTypeConstant.SLIDER, new Resource("classpath", "captcha/1.jpg", "default"));
        store.addResource(CaptchaTypeConstant.SLIDER, new Resource("classpath", "captcha/2.jpg", "default"));
        store.addResource(CaptchaTypeConstant.SLIDER, new Resource("classpath", "captcha/3.jpg", "default"));
        store.addResource(CaptchaTypeConstant.SLIDER, new Resource("classpath", "captcha/4.jpg", "default"));
        store.addResource(CaptchaTypeConstant.SLIDER, new Resource("classpath", "captcha/5.jpg", "default"));
        store.addResource(CaptchaTypeConstant.SLIDER, new Resource("classpath", "captcha/6.jpg", "default"));
        store.addResource(CaptchaTypeConstant.SLIDER, new Resource("classpath", "captcha/7.jpg", "default"));
    }
}

package top.lhlnb.backend.config;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.comm.SignVersion;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里云对象存储配置
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/11/5_11:56
 */

@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliyunOSSConfig {

    private String region;
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;

    @Resource
    ApplicationContext applicationContext;

    @Bean
    public OSS ossClient() {
        log.info("【阿里云 OSS】初始化对象存储客户端");
        // 创建凭证
        DefaultCredentialProvider credentialProvider =
                new DefaultCredentialProvider(getAccessKeyId(), getAccessKeySecret());
        // 配置客户端参数
        ClientBuilderConfiguration configuration = new ClientBuilderConfiguration();
        configuration.setSignatureVersion(SignVersion.V4);
        configuration.setSupportCname(true);

        // 初始化OSS客户端
        return OSSClientBuilder.create()
                .credentialsProvider(credentialProvider)
                .clientConfiguration(configuration)
                .region(getRegion())
                .endpoint(getEndpoint())
                .build();
    }

    @PreDestroy
    public void shutdownClient() {
        log.info("【阿里云 OSS】释放对象存储客户端");
        try {
            OSS ossClient = applicationContext.getBean(OSS.class);
            ossClient.shutdown();
        } catch (Exception e) {
            log.error("【阿里云 OSS】释放失败", e);
        }
    }
}

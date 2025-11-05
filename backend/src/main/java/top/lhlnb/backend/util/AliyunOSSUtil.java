package top.lhlnb.backend.util;

import com.aliyun.oss.*;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.lhlnb.backend.config.AliyunOSSConfig;
import top.lhlnb.backend.exception.ServerException;

import java.net.URL;
import java.util.Date;

/**
 * 阿里云对象存储工具类
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/11/5_13:05
 */

@Slf4j
@Component
public class AliyunOSSUtil {

    @Resource
    private AliyunOSSConfig aliyunOSSConfig;

    @Resource
    OSS ossClient;


    /**
     * 生成预签名链接
     *
     * @param objectUrl 对象访问URL (目录/UUID.拓展名)
     * @param ttlSec    签名链接有效期（秒）
     * @return 预签名链接
     */
    public String generatePresignedUrl(String objectUrl, int ttlSec) {
        try {
            Date expiration = new Date(System.currentTimeMillis() + ttlSec * 1000L);
            GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(
                    aliyunOSSConfig.getBucketName(),
                    objectUrl,
                    HttpMethod.GET
            );
            request.setExpiration(expiration);
            URL presignedUrl = ossClient.generatePresignedUrl(request);
            return presignedUrl.toString();
        } catch (Exception e) {
            throw new ServerException("OSS服务异常", e);
        }
    }
}

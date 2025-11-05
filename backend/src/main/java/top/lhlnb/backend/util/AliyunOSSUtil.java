package top.lhlnb.backend.util;

import cn.hutool.core.util.IdUtil;
import com.aliyun.oss.*;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.lhlnb.backend.config.AliyunOSSConfig;
import top.lhlnb.backend.domain.entity.TFile;
import top.lhlnb.backend.exception.ServerException;

import java.io.InputStream;
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

    /**
     * 上传文件
     *
     * @param uploadFileArgs 上传参数
     */
    public TFile uploadFile(UploadFileArgs uploadFileArgs) {
        // 初始化文件信息
        TFile tFile = TFile.builder()
                .uploadUser(uploadFileArgs.getUserId())
                .objectDirectory(uploadFileArgs.getDirectory())
                .fileExtension(getFileExtension(uploadFileArgs.getOriginalFilename()))
                .permissionCode(uploadFileArgs.getPermissionCode())
                .originName(uploadFileArgs.getOriginalFilename())
                .fileByteSize(uploadFileArgs.getFileByteSize())
                .fileMd5(uploadFileArgs.getMd5())
                .uploadTimestamp(uploadFileArgs.getTimestamp())
                .ttl(uploadFileArgs.getTtl())
                .build();
        tFile.setObjectKey(IdUtil.fastSimpleUUID() + '.' + tFile.getFileExtension());
        tFile.setUrl(tFile.getObjectDirectory() + '/' + tFile.getObjectKey());
        // 上传文件
        try {
            ossClient.putObject(
                    aliyunOSSConfig.getBucketName(),
                    tFile.getUrl(),
                    uploadFileArgs.getFileInputStream()
            );
            return tFile;
        } catch (Exception e) {
            log.error("【阿里云对象存储】上传文件失败：{}", e, e);
            return null;
        }
    }

    @Data
    @AllArgsConstructor
    public static class UploadFileArgs {
        private Long userId; // 用户id
        private String directory; // 文件目录
        private String originalFilename; // 文件原始名称
        private String permissionCode; // 文件权限标识符
        private long fileByteSize; // 文件大小
        private String md5; // 文件md5
        private long timestamp; // 上传时间戳
        private long ttl; // 文件有效期 (ms)
        private InputStream fileInputStream; // 文件输入流
    }

    /**
     * 删除文件
     *
     * @param objectUrl 删除对象访问URL (目录/UUID.拓展名)
     */
    public void deleteFile(String objectUrl) {
        try {
            ossClient.deleteVersion(aliyunOSSConfig.getBucketName(), objectUrl, null);
        } catch (Exception e) {
            log.error("【阿里云对象存储】删除文件失败：{}", e, e);
        }
    }

    /**
     * 获取文件小写扩展名
     *
     * @param originalFilename 文件原始名称
     * @return 文件小写扩展名
     */
    public static String getFileExtension(String originalFilename) {
        if (originalFilename == null) {
            return "";
        }
        int count = originalFilename.lastIndexOf('.');
        String extension;
        if (count > 0) {
            extension = originalFilename.substring(originalFilename.lastIndexOf('.') + 1);
        } else {
            extension = "";
        }
        return extension.toLowerCase();
    }
}

package top.lhlnb.backend.service;

import org.springframework.web.multipart.MultipartFile;
import top.lhlnb.backend.result.R;

/**
 * 文件服务
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/11/5_14:56
 */
public interface FileService {

    /**
     * 上传头像
     *
     * @param file 文件
     */
    R<?> uploadAvatar(MultipartFile file);
}

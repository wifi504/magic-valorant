package top.lhlnb.backend.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.lhlnb.backend.result.R;
import top.lhlnb.backend.service.FileService;

/**
 * 文件服务控制器
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/11/5_14:51
 */

@RestController
@RequestMapping("/api/file")
public class FileController {

    @Resource
    private FileService fileService;

    /**
     * 上传头像文件
     *
     * @param file FormData上传，大小不能超过4MB
     */
    @PostMapping("/uploadAvatar")
    public R<?> uploadAvatar(@RequestPart("file") MultipartFile file) {
        return fileService.uploadAvatar(file);
    }
}

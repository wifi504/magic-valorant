package top.lhlnb.backend.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.lhlnb.backend.domain.entity.TFile;
import top.lhlnb.backend.domain.vo.FileVo;
import top.lhlnb.backend.exception.ServerException;
import top.lhlnb.backend.mapper.TFileMapper;
import top.lhlnb.backend.result.R;
import top.lhlnb.backend.result.SysResult;
import top.lhlnb.backend.service.FileService;
import top.lhlnb.backend.util.AliyunOSSUtil;
import top.lhlnb.backend.util.SecurityUtil;
import top.lhlnb.backend.util.TxUtil;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static top.lhlnb.backend.util.AliyunOSSUtil.getFileExtension;

/**
 * 文件服务实现类
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/11/5_14:56
 */

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    private static final String AVATAR_DIRECTORY = "user-avatar";
    private static final String AVATAR_PERMISSION_CODE = "file:scope:avatar";

    @Resource
    private TxUtil txUtil;

    @Resource
    private TFileMapper tFileMapper;

    @Resource
    private AliyunOSSUtil aliyunOSSUtil;

    /**
     * 上传文件
     *
     * @param file 文件大小不能超过4MB
     */
    @Override
    public R<?> uploadAvatar(MultipartFile file) {
        if (file == null || file.getSize() == 0) {
            return R.error(SysResult.BAD_REQUEST, "上传的文件为空！");
        }
        if (file.getSize() > 4 * 1024 * 1024) {
            return R.error(SysResult.BAD_REQUEST, "上传的文件不能超过4MB！");
        }
        String extension = getFileExtension(file.getOriginalFilename());
        if (!("png".equals(extension) || "jpg".equals(extension) || "jpeg".equals(extension))) {
            return R.error(SysResult.BAD_REQUEST, "上传的文件格式不正确！");
        }
        try {
            // 1. 计算文件的md5
            String md5 = DigestUtil.md5Hex(file.getInputStream());

            // 文件访问URL
            AtomicReference<String> url = new AtomicReference<>();

            // 2. 开启事务上传文件
            boolean hasSuccess = txUtil.runInTx(() -> {
                // 2.1 查询数据库，判断文件是否已存在
                TFile tFile = tFileMapper.selectByMd5(md5);
                if (tFile != null &&
                        tFile.getId() > 0 &&
                        Objects.equals(tFile.getFileExtension(), getFileExtension(file.getOriginalFilename())) &&
                        file.getSize() == tFile.getFileByteSize()) {
                    // 2.2 文件已经存在了
                    return true;
                } else {
                    // 2.3 文件不存在，上传文件
                    try {
                        TFile tFile1 = aliyunOSSUtil.uploadFile(new AliyunOSSUtil.UploadFileArgs(
                                SecurityUtil.getUserId(),
                                AVATAR_DIRECTORY,
                                file.getOriginalFilename(),
                                AVATAR_PERMISSION_CODE,
                                file.getSize(),
                                md5,
                                System.currentTimeMillis(),
                                -1,
                                file.getInputStream()
                        ));
                        if (tFile1 == null) return false;
                        int count = tFileMapper.insertSelective(tFile1);
                        if (count <= 0) return false;
                        url.set(tFile1.getUrl());
                        return true;
                    } catch (IOException e) {
                        log.error("无法获取文件输入流", e);
                        return false;
                    }
                }
            });
            if (!hasSuccess) {
                return R.error(SysResult.INTERNAL_SERVER_ERROR, "文件上传失败！");
            }
            return R.ok(url.get(), "文件上传成功！");
        } catch (Exception e) {
            throw new ServerException("文件上传失败", e);
        }
    }

    /**
     * 获取文件预签名URL
     *
     * @param url 文件URL
     */
    @Override
    public R<?> getFilePresignedUrl(String url) {
        TFile tFile = tFileMapper.selectByUrl(url);
        if (tFile == null) {
            return R.error(SysResult.NOT_FOUND);
        }
        boolean hasAuthority = SecurityUtil.hasAuthority(tFile.getPermissionCode());
        if (!hasAuthority) {
            return R.error(SysResult.FORBIDDEN);
        }
        String presignedUrl = aliyunOSSUtil.generatePresignedUrl(url, 1);
        return R.ok(FileVo.builder()
                .url(presignedUrl)
                .originName(tFile.getOriginName())
                .type(tFile.getFileExtension())
                .size(tFile.getFileByteSize())
                .build());
    }
}

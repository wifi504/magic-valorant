package top.lhlnb.backend.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import jakarta.annotation.Resource;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.lhlnb.backend.domain.entity.TFile;
import top.lhlnb.backend.exception.ServerException;
import top.lhlnb.backend.mapper.TFileMapper;
import top.lhlnb.backend.result.R;
import top.lhlnb.backend.result.SysResult;
import top.lhlnb.backend.service.FileService;
import top.lhlnb.backend.util.TxUtil;

import java.io.IOException;

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

    @Resource
    private TxUtil txUtil;

    @Resource
    private TFileMapper tFileMapper;

    /**
     * 上传文件
     *
     * @param file 文件大小不能超过4MB
     */
    @Override
    public R<?> uploadAvatar(MultipartFile file) {
        try {
            // 1. 基本信息获取
            String originalFilename = file.getOriginalFilename();
            long fileSize = file.getSize();
            String extension = (
                    originalFilename != null ?
                            originalFilename.substring(originalFilename.lastIndexOf('.') + 1) :
                            ""
            ).toLowerCase();

            // 2. 计算文件的md5
            String md5 = DigestUtil.md5Hex(file.getInputStream());

            // TODO 3. 开启事务上传文件
            txUtil.runInTx(() -> {
                // 3.1 查询数据库，判断文件是否已存在
                TFile tFile = tFileMapper.selectByMd5(md5);
                if (tFile != null &&
                        tFile.getId() > 0 &&
                        extension.equals(tFile.getFileExtension()) &&
                        fileSize == tFile.getFileByteSize()) {
                    // 3.2 文件已经存在了
                } else {
                    // 3.3 文件不存在，上传文件
                }
            });
        } catch (Exception e) {
            throw new ServerException("文件上传失败", e);
        }
        return R.error(SysResult.NOT_IMPLEMENTED);
    }

    /**
     * 上传文件
     *
     * @param uploadFileArgs 上传参数
     */
    private void uploadFile(UploadFileArgs uploadFileArgs) {

    }

    @Builder
    @Data
    private static class UploadFileArgs {
        private TFile tFile;
        private MultipartFile multipartFile;
    }
}

package top.lhlnb.backend.domain.entity;

import java.io.Serial;
import java.io.Serializable;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * 文件对象存储表
 * t_file
 */

@Builder
@Data
public class TFile implements Serializable {
    /**
     * 文件ID
     */
    private Long id;

    /**
     * 上传用户ID
     */
    private Long uploadUser;

    /**
     * 对象目录
     */
    @NotNull
    private String objectDirectory;

    /**
     * 对象键(UUID.拓展名)
     */
    @NotNull
    private String objectKey;

    /**
     * 文件拓展名
     */
    private String fileExtension;

    /**
     * 访问URL(目录名/键名)
     */
    private String url;

    /**
     * 访问权限标识符(为空不鉴权)
     */
    private String permissionCode;

    /**
     * 原始文件名(含拓展名)
     */
    private String originName;

    /**
     * 文件大小(字节)
     */
    private Long fileByteSize;

    /**
     * 文件MD5
     */
    private String fileMd5;

    /**
     * 下载量统计
     */
    private Long downloadCount;

    /**
     * 业务引用总次数
     */
    private Long reference;

    /**
     * 上传时间戳(ms)
     */
    @NotNull
    private Long uploadTimestamp;

    /**
     * 有效期(-1=永久有效;单位ms)
     */
    private Long ttl;

    /**
     * 逻辑删除(null=正常;时间戳=删除时间)
     */
    private Long deletedTimestamp;

    @Serial
    private static final long serialVersionUID = 1L;
}
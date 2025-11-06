package top.lhlnb.backend.domain.vo;

import lombok.Builder;
import lombok.Data;

/**
 * 获取文件 VO
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/10/28_17:12
 */

@Builder
@Data
public class FileVo {
    // 预签名链接
    private String url;
    // 原始文件名
    private String originName;
    // 文件类型
    private String type;
    // 文件大小
    private Long size;
}

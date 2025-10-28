package top.lhlnb.backend.domain.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户表
 * t_user
 */
@Data
public class TUser implements Serializable {
    /**
     * 用户ID
     */
    private Long id;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 邮箱(可用于登录)
     */
    private String email;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 微信openid
     */
    private String wxOpenid;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 用户等级
     */
    private Integer level;

    /**
     * 用户状态(0=正常;1=封禁)
     */
    private Byte status;

    /**
     * 逻辑删除(0=正常;1=删除)
     */
    private Byte deleted;

    /**
     * 用户创建时间
     */
    private Date createdTime;

    /**
     * 最后更新时间
     */
    private Date updatedTime;

    @Serial
    private static final long serialVersionUID = 1L;
}
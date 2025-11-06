package top.lhlnb.backend.domain.dto.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/10/29_13:35
 */

@Data
public class EmailLoginDto {

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式错误")
    private String email;

    @Size(min = 6, max = 20, message = "密码长度必须在6-20之间")
    private String password;

    @NotBlank(message = "验证码不能为空")
    private String captcha;
}

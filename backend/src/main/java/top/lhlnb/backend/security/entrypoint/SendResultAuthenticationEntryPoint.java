package top.lhlnb.backend.security.entrypoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import top.lhlnb.backend.result.R;
import top.lhlnb.backend.result.SysResult;

import java.io.IOException;

/**
 * 认证失败处理器（未登录）
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/10/28_17:22
 */

@Component
public class SendResultAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {
        R.writeResponse(response, R.error(SysResult.UNAUTHORIZED));
    }
}

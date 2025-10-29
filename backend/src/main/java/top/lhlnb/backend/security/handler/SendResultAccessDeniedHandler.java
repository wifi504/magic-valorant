package top.lhlnb.backend.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import top.lhlnb.backend.result.R;
import top.lhlnb.backend.result.SysResult;

/**
 * 权限不足处理器（拒绝访问）
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/10/28_17:26
 */

@Component
public class SendResultAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) {
        R.writeResponse(response, R.error(SysResult.FORBIDDEN));
    }
}

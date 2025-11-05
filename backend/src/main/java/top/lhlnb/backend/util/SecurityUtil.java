package top.lhlnb.backend.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Spring Security 工具类
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/11/5_22:34
 */
public class SecurityUtil {
    /**
     * 获取当前登录用户ID
     *
     * @return 用户ID
     */
    public static Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Long id) {
            return id;
        }
        return null;
    }

    /**
     * 获取当前登录用户权限
     *
     * @return 权限
     */
    public static boolean hasAuthority(String permissionCode) {
        if (permissionCode == null || permissionCode.isEmpty()) {
            return true;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getAuthorities() == null) {
            return false;
        }
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(permissionCode::equals);
    }
}

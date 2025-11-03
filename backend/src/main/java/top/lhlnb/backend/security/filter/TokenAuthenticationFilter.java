package top.lhlnb.backend.security.filter;

import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import top.lhlnb.backend.config.TokenConfig;
import top.lhlnb.backend.mapper.TUserMapper;
import top.lhlnb.backend.util.TokenUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Token 认证过滤器
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/10/28_17:13
 */

@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private TokenConfig tokenConfig;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private TUserMapper tUserMapper;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String token = TokenUtil.resolveToken(request);
        if (token != null) {
            String idStr = stringRedisTemplate.opsForValue().get(tokenConfig.getRedisPrefix() + ':' + token);
            if (idStr != null) {
                try {
                    Long id = Long.parseLong(idStr);
                    // 认证成功，添加用户权限
                    String key = tokenConfig.getRedisPrefix() + ':' + TokenUtil.USER_PERMISSIONS_PREFIX + ':' + id;
                    // 获取缓存的权限
                    List<String> permissionList = stringRedisTemplate.opsForList().range(key, 0, -1);
                    if (permissionList == null || permissionList.isEmpty()) {
                        // 缓存权限为空，从数据库查权限
                        permissionList = tUserMapper.selectPermissionsCodeByUserId(id);
                        if (permissionList != null && !permissionList.isEmpty()) {
                            // 查到权限，缓存权限
                            stringRedisTemplate.opsForList().rightPushAll(key, permissionList);
                        }
                    }
                    // 添加权限
                    if (permissionList != null && !permissionList.isEmpty()) {
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(
                                        id,
                                        null,
                                        permissionList
                                                .stream()
                                                .map(SimpleGrantedAuthority::new)
                                                .toList()
                                );
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                } catch (NumberFormatException ignore) {
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}

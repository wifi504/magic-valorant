package top.lhlnb.backend.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Spring Security 用户信息服务（无效）
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/10/29_10:01
 */

@Service
public class DummyUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        throw new UsernameNotFoundException("JWT 模式，不支持此方式认证！");
    }
}

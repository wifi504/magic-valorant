package top.lhlnb.backend.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import top.lhlnb.backend.exception.ArgumentException;
import top.lhlnb.backend.exception.ServerException;
import top.lhlnb.backend.result.R;
import top.lhlnb.backend.result.SysResult;

/**
 * 全局异常处理
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/10/29_0:32
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 401 未登录或无效 Token
    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public R<?> handleAuthError() {
        return R.error(SysResult.UNAUTHORIZED);
    }

    // 403 无权限访问
    @ExceptionHandler(AccessDeniedException.class)
    public R<?> handleAccessDenied() {
        return R.error(SysResult.FORBIDDEN);
    }

    // 404 资源未找到
    @ExceptionHandler(NoResourceFoundException.class)
    public R<?> handleNotFound() {
        return R.error(SysResult.NOT_FOUND);
    }

    // 405 请求方法异常
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R<?> handleMethodNotSupported() {
        return R.error(SysResult.METHOD_NOT_ALLOWED);
    }

    // 请求参数类型不匹配
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public R<?> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
        return new ArgumentException("请求参数类型不匹配", e, "001").getResult();
    }

    // 参数校验失败
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<?> handleValidation(MethodArgumentNotValidException e) {
        return new ArgumentException("参数校验失败", e, "002").getResult();
    }

    // 请求体解析失败（JSON 格式错误或字段类型不匹配）
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public R<?> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        return new ArgumentException("请求体解析失败（JSON 格式错误或字段类型不匹配）", e, "003").getResult();
    }

    // 已定义的服务器异常
    @ExceptionHandler(ServerException.class)
    public R<?> handlerServerException(ServerException e) {
        return e.getResult();
    }

    // 通用异常（兜底）
    @ExceptionHandler(Exception.class)
    public R<?> handleOtherErrors(Exception e) {
        log.error("预料之外的异常", e);
        return R.error(SysResult.SERVER_ERROR, ServerException.getFormattedMessage(e));
    }
}

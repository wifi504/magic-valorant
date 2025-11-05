package top.lhlnb.backend.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import top.lhlnb.backend.exception.ArgumentException;
import top.lhlnb.backend.exception.ServerException;
import top.lhlnb.backend.result.R;
import top.lhlnb.backend.result.SysResult;

import java.sql.SQLException;
import java.util.stream.Collectors;

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
    @ExceptionHandler(AuthenticationException.class)
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
    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
            MultipartException.class
    })
    public R<?> handleMethodArgumentTypeMismatch(Exception e) {
        return new ArgumentException("请求参数类型不匹配", e).getResult();
    }

    // 参数缺失
    @ExceptionHandler(MissingRequestValueException.class)
    public R<?> handleValidation(Exception e) {
        return new ArgumentException("缺少参数", e).getResult();
    }

    // 参数校验异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<?> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> String.format("字段'%s'%s", error.getField(), error.getDefaultMessage()))
                .collect(Collectors.joining("；"));
        return new ArgumentException(msg).getResult();
    }

    // 请求体解析失败（JSON 格式错误或字段类型不匹配）
    @ExceptionHandler(HttpMessageConversionException.class)
    public R<?> handleHttpMessageNotReadable(Exception e) {
        return new ArgumentException("请求体字段类型不匹配", e).getResult();
    }

    // 数据库异常
    @ExceptionHandler({SQLException.class, DataAccessException.class})
    public R<?> handleSqlException(Exception e) {
        Throwable cause = e;
        while (cause.getCause() != null) {
            cause = cause.getCause();
        }
        log.error("数据库异常：{}", e.getMessage());
        return R.error(SysResult.INTERNAL_SERVER_ERROR, ServerException.getFormattedMessage(cause));
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
        return R.error(SysResult.INTERNAL_SERVER_ERROR, ServerException.getFormattedMessage(e));
    }
}

package top.lhlnb.backend.result;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 统一响应类 Result 对象
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/10/28_17:49
 */

@Slf4j
@Data
@Builder
public class R<T> {
    private Integer code;
    private String msg;
    private T data;

    /**
     * 覆写消息前缀
     *
     * @param prefix 前缀
     */
    public R<T> overwriteMsgPrefix(String prefix) {
        if (prefix != null) {
            String old = this.getMsg();
            int index = old.indexOf('：');
            if (index == -1) {
                this.setMsg(prefix + "：" + old);
            } else {
                this.setMsg(prefix + "：" + old.substring(index + 1));
            }
        }
        return this;
    }

    /**
     * 成功响应
     */
    public static R<Object> ok() {
        return ok(null);
    }

    /**
     * 成功响应
     *
     * @param msg 响应消息
     */
    public static R<Object> okMsg(String msg) {
        return R.ok(null, msg);
    }


    /**
     * 成功响应
     *
     * @param data 响应数据
     * @param <T>  数据类型
     */
    public static <T> R<T> ok(T data) {
        return ok(data, SysResult.SUCCESS.getMsg());
    }

    /**
     * 成功响应
     *
     * @param data 响应数据
     * @param msg  响应消息
     * @param <T>  数据类型
     */
    public static <T> R<T> ok(T data, String msg) {
        return R.<T>builder()
                .code(SysResult.SUCCESS.getCode())
                .msg(msg)
                .data(data)
                .build();
    }

    /**
     * 错误业务响应
     *
     * @param sysResult 系统响应
     * @param msg       业务消息
     */
    public static R<Object> error(SysResult sysResult, String msg) {
        return error(sysResult, "", msg, null);
    }

    /**
     * 错误业务响应
     *
     * @param sysResult 系统响应
     * @param code      业务码
     * @param msg       业务消息
     */
    public static R<Object> error(SysResult sysResult, String code, String msg) {
        return error(sysResult, code, msg, null);
    }

    /**
     * 错误业务响应
     *
     * @param sysResult 系统响应
     * @param code      业务码
     * @param msg       业务消息
     * @param data      数据
     * @param <T>       数据类型
     */
    public static <T> R<T> error(SysResult sysResult, String code, String msg, T data) {
        int fullCode = sysResult.getCode();
        try {
            fullCode = Integer.parseInt(sysResult.getCode() + code);
        } catch (NumberFormatException ignore) {
        }
        return R.<T>builder()
                .code(fullCode)
                .msg(sysResult.getMsg() + "：" + msg)
                .data(data)
                .build();
    }

    /**
     * 系统错误响应
     *
     * @param sysResult 系统响应
     */
    public static R<Object> error(SysResult sysResult) {
        return error(sysResult.getCode(), sysResult.getMsg(), null);
    }

    /**
     * 原始错误响应
     *
     * @param code 错误码
     * @param msg  消息
     * @param data 数据
     * @param <T>  数据类型
     */
    public static <T> R<T> error(int code, String msg, T data) {
        return R.<T>builder()
                .code(code)
                .msg(msg)
                .data(data)
                .build();
    }

    /**
     * 在响应流中写入 Result 对象
     *
     * @param response HttpServletResponse
     * @param result   Result
     */
    public static void writeResponse(HttpServletResponse response, R<?> result) {
        try {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(new ObjectMapper().writeValueAsString(result));
        } catch (IOException e) {
            log.error("响应流写入异常", e);
        }
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return super.toString();
        }
    }
}

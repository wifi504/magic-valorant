package top.lhlnb.backend.domain.result;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Data;

import java.io.IOException;

/**
 * 统一响应类 Result 对象
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/10/28_17:49
 */

@Data
@Builder
public class R<T> {
    private Integer code;
    private String msg;
    private T data;

    /**
     * 成功响应
     */
    public static R<String> ok() {
        return ok("");
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
            throw new RuntimeException(e);
        }
    }
}

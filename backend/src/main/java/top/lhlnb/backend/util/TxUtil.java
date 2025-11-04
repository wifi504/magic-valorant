package top.lhlnb.backend.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.function.Supplier;

/**
 * 通用事务执行工具类
 * 支持函数式写法，可返回值，可自动回滚。
 *
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/11/4_17:49
 */

@Slf4j
@Component
public class TxUtil {
    private final TransactionTemplate txTemplate;

    public TxUtil(TransactionTemplate txTemplate) {
        this.txTemplate = txTemplate;
    }

    /**
     * 执行有返回值的事务逻辑
     *
     * @param action 事务内执行逻辑，返回结果
     * @param <T>    返回类型
     * @return action 返回的结果
     * <p>
     * 注意：
     * - 方法内的代码在同一个数据库事务中执行
     * - 若 action 抛出异常或调用 setRollbackOnly()，事务会自动回滚
     * - 当前调用线程会被阻塞直到事务完成（同步执行）
     */
    public <T> T runInTx(Supplier<T> action) {
        return txTemplate.execute(status -> {
            try {
                // 执行用户传入的业务逻辑
                T result = action.get();
                log.debug("事务提交成功: {}", result);
                return result;
            } catch (Exception e) {
                // 捕获异常并标记回滚
                log.error("事务执行异常，准备回滚", e);
                status.setRollbackOnly();
                throw e;
            }
        });
    }

    /**
     * 执行无返回值的事务逻辑
     *
     * @param action 事务内执行逻辑
     *               <p>
     *               示例：
     *               txUtil.runInTx(() -> {
     *                   userMapper.deleteById(id);
     *                   logMapper.insert(log);
     *               });
     */
    public void runInTx(Runnable action) {
        txTemplate.executeWithoutResult((TransactionStatus status) -> {
            try {
                action.run();
                log.debug("事务提交成功（void）");
            } catch (Exception e) {
                log.error("事务执行异常，准备回滚", e);
                status.setRollbackOnly();
                throw e;
            }
        });
    }
}

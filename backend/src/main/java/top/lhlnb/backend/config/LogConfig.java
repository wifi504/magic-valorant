package top.lhlnb.backend.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import ch.qos.logback.core.util.FileSize;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;

/**
 * @author WIFI连接超时
 * @version 1.0
 * Create Time 2025/10/28_21:24
 */

@Data
@Configuration
@ConfigurationProperties(prefix = "logging.custom")
public class LogConfig {

    // 日志文件夹路径
    private String path = "./logs";
    // 单个日志文件最大大小
    private String maxFileSize = "100MB";
    // 保留天数（默认 -1 表示永久保留）
    private int maxHistory = -1;

    // 彩色输出（仅控制台）
    private static final String CONSOLE_PATTERN =
            "[%d{yyyy-MM-dd HH:mm:ss SSS}]%highlight([%-5level])[%15.15t] %cyan(%-45.45logger{40}): %msg%n";

    // 无色输出（用于文件）
    private static final String FILE_PATTERN =
            "[%d{yyyy-MM-dd HH:mm:ss SSS}][%level][%thread] %logger: %msg%n";

    @PostConstruct
    public void init() {
        LoggerContext context = (LoggerContext) org.slf4j.LoggerFactory.getILoggerFactory();
        context.reset();

        // 创建控制台 Appender
        PatternLayoutEncoder consoleEncoder = new PatternLayoutEncoder();
        consoleEncoder.setContext(context);
        consoleEncoder.setCharset(StandardCharsets.UTF_8);
        consoleEncoder.setPattern(CONSOLE_PATTERN);
        consoleEncoder.start();

        ch.qos.logback.core.ConsoleAppender<ILoggingEvent> consoleAppender =
                new ch.qos.logback.core.ConsoleAppender<>();
        consoleAppender.setContext(context);
        consoleAppender.setEncoder(consoleEncoder);
        consoleAppender.start();

        // 文件 Appender（带滚动和压缩）
        RollingFileAppender<ILoggingEvent> fileAppender = new RollingFileAppender<>();
        fileAppender.setContext(context);
        fileAppender.setAppend(true);
        fileAppender.setFile(path + "/latest.log"); // 当前写入的文件

        // 滚动策略：每天一个文件，按大小切分
        TimeBasedRollingPolicy<ILoggingEvent> rollingPolicy = new TimeBasedRollingPolicy<>();
        rollingPolicy.setContext(context);
        rollingPolicy.setParent(fileAppender);

        // 命名样式：log_yyyy_MM_dd_i.log.gz
        String filePattern = String.format("%s/log_%%d{yyyy_MM_dd}_%%i.log.gz", path);
        rollingPolicy.setFileNamePattern(filePattern);

        // 每日 + 按大小触发滚动
        SizeAndTimeBasedFNATP<ILoggingEvent> triggeringPolicy = new SizeAndTimeBasedFNATP<>();
        triggeringPolicy.setMaxFileSize(FileSize.valueOf(maxFileSize));
        rollingPolicy.setTimeBasedFileNamingAndTriggeringPolicy(triggeringPolicy);

        // 日志保留策略
        if (maxHistory > 0) {
            rollingPolicy.setMaxHistory(maxHistory);
        }

        rollingPolicy.start();

        fileAppender.setRollingPolicy(rollingPolicy);

        // 文件编码器
        PatternLayoutEncoder fileEncoder = new PatternLayoutEncoder();
        fileEncoder.setContext(context);
        fileEncoder.setCharset(StandardCharsets.UTF_8);
        fileEncoder.setPattern(FILE_PATTERN);
        fileEncoder.start();

        fileAppender.setEncoder(fileEncoder);
        fileAppender.start();

        // 根日志配置
        ch.qos.logback.classic.Logger rootLogger = context.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(Level.INFO);
        rootLogger.addAppender(consoleAppender);
        rootLogger.addAppender(fileAppender);
    }
}

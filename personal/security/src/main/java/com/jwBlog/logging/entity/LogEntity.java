package com.jwBlog.logging.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;

/**
 * @author jie
 * @date 2018-11-24
 */
@Data
@Getter
@Setter
@NoArgsConstructor
public class LogEntity {
    private Long id;
    /**
     * 操作用户
     */
    private String username;
    /**
     * 描述
     */
    private String description;
    /**
     * 方法名
     */
    private String method;
    /**
     * 参数
     */
    private String params;
    /**
     * 日志类型
     */
    private String logType;
    /**
     * 请求ip
     */
    private String requestIp;
    /**
     * 请求耗时
     */
    private Long time;
    /**
     * 异常详细
     */
    private String exceptionDetail;
    /**
     * 创建日期
     */
    private Timestamp createTime;

    public LogEntity(String logType, Long time) {
        this.logType = logType;
        this.time = time;
    }
}

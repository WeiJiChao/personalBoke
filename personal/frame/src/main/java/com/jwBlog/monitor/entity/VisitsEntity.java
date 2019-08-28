package com.jwBlog.monitor.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * pv 与 ip 统计
 *
 * @author jie
 * @date 2018-12-13
 */
@Getter
@Setter
public class VisitsEntity {
    private Long id;
    private String date;
    private Long pvCounts;
    private Long ipCounts;
    private String createTime;
    private String weekDay;
}

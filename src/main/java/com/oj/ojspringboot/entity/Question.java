package com.oj.ojspringboot.entity;

import lombok.Data;

import java.time.LocalDateTime;
/**
 * @author ld
 * @since 1.8
 * @version 17
 */
@Data
public class Question {
    private Long id;

    private Long createId;

    private int status;//使用状态

    private int power;//权限  0 普通用户  1 admin和VIP

    private String description;

    private String title;

    private LocalDateTime createTime;
}

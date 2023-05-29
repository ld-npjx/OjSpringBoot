package com.oj.ojspringboot.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class QuestionPro {
    private Long id;

    private Long createId;

    private int status;//使用状态

    private int power;//权限  0 普通用户  1 admin和VIP

    private String description;

    private String title;

    private LocalDateTime createTime;

    private String result;//格式："参数1,参数2;结果1,结果2"
}

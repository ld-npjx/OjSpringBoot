package com.oj.ojspringboot.entity;

import lombok.Data;
/**
 * @author ld
 * @since 1.8
 * @version 17
 */
@Data
public class Answer {
    private Long questionId;//根据questionId来找到答案

    private Long timeMs;//运行时间限制

    private Long consumeKb;//内存消耗

    private String inputStr;//输入参数

    private String outputStr;//输出参数
}

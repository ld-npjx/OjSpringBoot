package com.oj.ojspringboot.service;
/**
 * @author ld
 * @since 1.8
 * @version 17
 */
public interface SendService {
    boolean save(String phoneOrEmail,String code);
    //保存到redis

    boolean send(String phoneOrEmail,String code);
    //发送邮箱或者短信

    boolean judge(String phoneOrEmail,String code);
    //判断前后端数据是否相同
}

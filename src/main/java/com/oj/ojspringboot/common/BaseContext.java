package com.oj.ojspringboot.common;

/**
 * @author ld
 * @since 1.8
 * @version 17
 */
public class BaseContext {

    /**
     * 基于ThreadLocal封装的工具类 用户保存和获取当前登录用户id
     */
    private static final ThreadLocal<Long> threadLocal=new ThreadLocal<>();

    /**
     * @param id 存入的线程程id
     */
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    /**
     * @return id 返回该线程的id
     */
    public static Long getCurrentId(){
        return threadLocal.get();
    }
}

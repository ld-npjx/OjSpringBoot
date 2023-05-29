package com.oj.ojspringboot.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
/**
 * @author ld
 * @since 1.8
 * @version 17
 */
@Slf4j
@Data
public class Message<T> {
    private Integer code;//编码  1=success 0=false&error   admin=100 vip=10 common=1

    private String msg;// mistake message

    private T data;

    private Map map=new HashMap();//dynamic data

    public static <T> Message<T> success(T object){
        Message<T> mes=new Message<T>();
        mes.data=object;
        mes.code=1;
        log.info(object.toString());
        return mes;
    }


    public static <T> Message<T> error(T object){
        Message<T> mes=new Message<T>();
        mes.data=object;
        mes.code=0;
        log.info(object.toString());
        return mes;
    }
}

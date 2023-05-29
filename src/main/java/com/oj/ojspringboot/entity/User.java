package com.oj.ojspringboot.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * @author ld
 * @since 1.8
 * @version 17
 */
@Data
public class User implements Serializable {
    private Long id;    //用户自己创建的id

    private int status; //0为正常使用，1为被封禁

    private int power;//0为普通用户，1为vip 2为admin

    private String password;

    private Long numPhone;

    private LocalDateTime createTime;
}

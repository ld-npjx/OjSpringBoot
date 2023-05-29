package com.oj.ojspringboot.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.oj.ojspringboot.entity.User;


/**
 * @author ld
 * @since 1.8
 * @version 17
 */

public interface UserService extends IService<User> {
    String vip(Long id,String vip);
}

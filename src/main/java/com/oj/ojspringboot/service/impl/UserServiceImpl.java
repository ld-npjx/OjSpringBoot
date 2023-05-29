package com.oj.ojspringboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oj.ojspringboot.entity.User;
import com.oj.ojspringboot.mapper.UserMapper;
import com.oj.ojspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 用户服务层
 * @author ld
 * @since 1.8
 * @version 17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserService userService;


    @Override
    public String vip(Long id,String vipId) {
        String pattern="(1[35]\\d{9}VIP([A-Z]|\\d){5})(1[89]\\d{9}GAME([A-Z]|\\d){5})";
        //匹配vipId
        Pattern compile = Pattern.compile(pattern);

        Matcher matcher = compile.matcher(vipId);

        User user = userService.getById(id);
        int status = user.getStatus();
        if(status==0){
            if (matcher.matches()){
            user.setStatus(1);
            boolean b = userService.saveOrUpdate(user);
            if(b) return "恭喜你成为尊贵的vip用户";
            else return "出现了不可预料的错误";
            }else {
                return "vipId验证错误";
            }
        }else if(status==1){
            return "您已经使尊贵的vip用户了";
        }else {
            return "您已经使admin用户了";
        }
    }


}

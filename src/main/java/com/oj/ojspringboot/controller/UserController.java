package com.oj.ojspringboot.controller;

import com.oj.ojspringboot.entity.Message;
import com.oj.ojspringboot.entity.User;
import com.oj.ojspringboot.mapper.UserMapper;
import com.oj.ojspringboot.service.SendService;
import com.oj.ojspringboot.service.UserService;
import com.oj.ojspringboot.util.roundNumUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author ld
 * @since 1.8
 * @version 17
 */
@RestController
@Slf4j
@RequestMapping("/user")

public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SendService sendService;

    /**
     *通过前端传入的消息判断能否登录
     * @param id 前端传入的userId
     * @param password 前端传入的账号参数
     * @return Message<String>  将该对象返回前端，返回是否登录成功结果
     */

    @PostMapping("/login")
    public Message<String> login(HttpServletRequest request, @RequestParam Long id,@RequestParam String password){
        Message<String> strMsg = new Message<>();
        String passwordM5= DigestUtils.md5DigestAsHex(password.getBytes());

        //查询数据库
        User user = userMapper.selectById(id);

        //对比前后端数据
        if(Objects.equals(user.getPassword(), password)) {
            request.getSession().setAttribute("user",id);
            strMsg.setCode(1);
            strMsg.setMsg(user.toString()+"success");
            return strMsg;
        }else
            return Message.error("password||userId error");
    }

    /**
     * 验证通过手机登录
     * @param phone 手机号
     * @param code 验证码
     * @return 判断结果
     */

    @PostMapping("/loginPhone")
    public Message<String> loginPhone(@RequestParam String phone,@RequestParam String code){
        Message<String> strMsg = new Message<>();
        if(sendService.judge(phone, code)) {
            strMsg.setCode(1);
            strMsg.setMsg("success");
        }else{
            strMsg.setCode(0);
            strMsg.setMsg("code error or expiration");
        }
        return strMsg;
    }

    /**
     * 发送验证码  redis保存300s 阿里云接口发送
     * @param phone 手机号
     * @return Message.trueOrFalse
     */

    @PostMapping("/phoneSend")  //验证码发送
    public Message<String> phoneSend(@RequestParam String phone){
        log.info("phoneSend");
        String code = roundNumUtil.generateCode(5);

        boolean send = sendService.send(code, phone);
        boolean save=sendService.save(phone,code);


        if(save&&send){
            return Message.success("success send");
        }else {
            return Message.error("error");
        }
    }


    /**
     * 删除session中的user
     * @return falseOrTrue
     */
    @PostMapping("/logout")
    public Message<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        Message<String> strMsg = new Message<>();
        strMsg.setCode(1);
        return strMsg;
    }

    /**
     * 更新用户（直接替换thisUser
     * @return falseOrTrue
     * @param user 想要更改的用户
     */
    @PutMapping("/update")
    public Message<String> update(@RequestBody User user){
        boolean b = userService.saveOrUpdate(user);
        if(b) return Message.success("user be update");
        else return Message.error("error update");
    }

    /**
     *  保存用户（save
     * @return falseOrTrue
     * @param user 想要保存的用户
     */
    @PostMapping("/save")
    public Message<String> save(@RequestBody User user){
        log.info(user.toString());
        user.setCreateTime(LocalDateTime.now());
        int insert = userMapper.insert(user);
        if(insert==1) return Message.success("user be save");
        else return Message.error("error save");
    }

    /**
     * 用户提权 为了系统安全  只能common-->>VIP
     * @param vipId vipId为vip卡编号  类似于Q币卡
     * @return Str 返回字符串结果给前端
     */
    @PostMapping("/vip")
    public String vip(HttpServletRequest request, @RequestParam String vipId){
        Object user = request.getSession().getAttribute("user");
        Long id=(Long)user;
        return userService.vip(id,vipId);//返回结果
    }
}

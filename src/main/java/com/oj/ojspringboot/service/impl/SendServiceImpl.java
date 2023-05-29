package com.oj.ojspringboot.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.oj.ojspringboot.service.SendService;
import com.oj.ojspringboot.util.redisApiUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
/**
 * 发送服务层
 * @author ld
 * @since 1.8
 * @version 17
 */
@Slf4j
@Service
public class SendServiceImpl implements SendService {

    /**
     * 保存短信码到redis key,value
     * @param phoneOrEmail 邮箱或者机号为key
     * @param code 验证码为value
     * @return 返回结果  true成功  false失败
     */
    @Override
    public boolean save(String phoneOrEmail,String code) {
        log.info("set key,value");
         return redisApiUtil.redisSetKey(phoneOrEmail, code,500);
    }
    @Value("${sms.accessKeyId}")
    private String accessKeyId;
    @Value("${sms.secret}")
    private String secret;
    @Value("${sms.signName}")
    private String signName; // 短信签名
    @Value("${sms.templateCode}")
    private String templateCode;  //短信模板
    @Value("${sms.regionId}")
    private String regionId;
    /**
     * 连接aliyun发送验证码
     * @param phoneOrEmail 短信手机号
     * @param code 验证码
     * @return 返回结果  true成功  false失败
     */
    @Override
    public boolean send(String code, String phoneOrEmail) {
        // 连接阿里云
        // *********注意：AccessKey ID 和 AccessKey Secret 需要改成你自己的**********
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, secret);
        IAcsClient client = new DefaultAcsClient(profile);

        // 构建请求
        CommonRequest request = new CommonRequest();
        log.info(accessKeyId+templateCode);
        // 请求方式
        request.setSysMethod(MethodType.POST);

        // 官方需要的和短信请求相关的信息
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");

        // 生成装有短信验证码的map
        Map<String,Object> messageMap = new HashMap<>();
        messageMap.put("code", code);

        // 填写请求参数
        request.putQueryParameter("PhoneNumbers", phoneOrEmail); // 电话

        log.info(phoneOrEmail);
        // *********注意：签名名称 需要改成你自己的**********
        request.putQueryParameter("SignName", signName); // 签名名称
        // *********注意：模板CODE 需要改成你自己的**********
        request.putQueryParameter("TemplateCode", templateCode); // 模版CODE
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(messageMap)); // 短信模板变量对应的实际值


        try {
            // 发送请求并接受返回值
            CommonResponse response = client.getCommonResponse(request);
            // 把json格式字符串变成Json对象
            JSONObject jsonObject = JSON.parseObject(response.getData());
            // 请求状态码，返回OK代表请求成功，来自官方文档
            String resCode = jsonObject.getString("Code");
            return "OK".equals(resCode);
        } catch (ClientException e) {
            e.printStackTrace();
        }

        return false;

    }

    /**
     * 前后端数据对比
     * @param  phoneOrEmail 前端手机号或者验证码
     * @param code 验证码
     * @return 判断结果
     */
    @Override
    public boolean judge(String phoneOrEmail, String code) {
        String thisCode = redisApiUtil.redisGetKey(phoneOrEmail);
        if(thisCode.equals(code)){
            log.info("judge success");
            return true;
        }
        else {
            log.info("judge false");
            return false;
        }
    }
}

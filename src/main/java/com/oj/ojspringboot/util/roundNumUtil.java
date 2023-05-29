package com.oj.ojspringboot.util;

import java.util.Random;

/**
 * 生成随机数的方法类
 * @author ld
 * @since 1.8
 * @version 17
 */
public class roundNumUtil {
    /**
     * 生成随机验证码
     * @param count 想要生成的验证码的位数
     */
    public static String code(int count){
        StringBuilder code = new StringBuilder();

        for(int i=0;i<count;i++)
            code.append((int)Math.round(Math.random())*9);
        return code.toString();
    }

    /**
     * 生成随机验证码
     * 优化版本
     */
    public static String generateCode(int count) {
        StringBuilder code = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            int digit = random.nextInt(10);
            code.append(digit);
        }

        return code.toString();
    }
}

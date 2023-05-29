package com.oj.ojspringboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
/**
 * springBoot程序入口
 * @author ld
 * @since 1.8
 * @version 17
 */
@ServletComponentScan
@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = {"com.oj.ojspringboot.mapper"})
public class OjSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(OjSpringBootApplication.class, args);
    }
}

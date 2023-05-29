package com.oj.ojspringboot.config;

import com.oj.ojspringboot.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * @author ld
 * @version 18
 * @since 1.7
 */

@Slf4j
@Configuration

public class WebMvcConfig extends WebMvcConfigurationSupport {

    /**
     *保护真实路径
     * @param registry 重载方法
     * 映射静态资源
     * 用户访问/html/**下时  改方法会去资源路径下的/static/路径下去找 classpath是资源路径
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("start static resource mapping");
        registry.addResourceHandler("/html/**").addResourceLocations("classpath:/static/");
    }

    /**
     * @param converters
     * java-->json
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters){
        log.info("扩展消息转换器");
        //创建消息转换器对象
        MappingJackson2HttpMessageConverter messageConverter=new MappingJackson2HttpMessageConverter();

        //设置对象转换器  底层使用Jackson将Java对象转换为json
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        //将上面的转换器添加到MVC框架的转换器集合中  ，把自己的转换器放最前面 
        converters.add(0,messageConverter);
    }
}

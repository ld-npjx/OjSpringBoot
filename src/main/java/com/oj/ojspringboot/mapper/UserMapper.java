package com.oj.ojspringboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oj.ojspringboot.entity.User;
import org.apache.ibatis.annotations.Mapper;
/**
 * @author ld
 * @since 1.8
 * @version 17
 */
@Mapper
public interface UserMapper extends BaseMapper<User>{
    int insert(User user);
}

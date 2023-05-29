package com.oj.ojspringboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oj.ojspringboot.entity.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ld
 * @since 1.8
 * @version 17
 */
@Mapper
public interface QuestionMapper extends BaseMapper<Question> {
//    List<Question> selectAll();
    // 实现增删改查

    //1、增加题目
    int insert(Question question);

    //2、根据id删除题目
    int delete(@Param("id") int id);

    //3、查询所有的题目
    List<Question> selectAll();

    //4、查询一个题目
    Question selectOne(@Param("id") Long id);

    //5、查询题目根据名字、模糊查询
    List<Question> selectByLikeTitle(String likeTitle);

    //6、根据id修改题目信息
    int updateById(@Param("id") int id,
                   @Param("title") String title,
                   @Param("level") String level,
                   @Param("description") String description,
                   @Param("template") String template,
                   @Param("testCode") String testCode);

}

package com.oj.ojspringboot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oj.ojspringboot.common.BaseContext;
import com.oj.ojspringboot.entity.*;
import com.oj.ojspringboot.mapper.QuestionMapper;
import com.oj.ojspringboot.mapper.UserMapper;
import com.oj.ojspringboot.service.QuestionService;
import com.oj.ojspringboot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ld
 * @since 1.8
 * @version 17
 */
@Slf4j
@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @Autowired
    public ObjectMapper objectMapper;
    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    @GetMapping("/all")
    public String getQuestion() throws JsonProcessingException {
        List<Question> questions = questionMapper.selectAll();//全局搜索
//        List<Question> questions = questionMapper.selectAll();
//        return Message.success(objectMapper.writeValueAsString(questions));
//        前端接受json数据，将list-->>json
//        Message<String> strMsg = new Message<>();
//        LambdaQueryWrapper<Question>  queryWrapper= new LambdaQueryWrapper<>();
//        List<Question> questions = questionMapper.selectAll();
//        return questions;

        Long userId=BaseContext.getCurrentId();
        User user = userMapper.selectById(userId);

        if(user.getPower()!=0)
            return objectMapper.writeValueAsString(questions);

        List<Question> newQuestions=new ArrayList<>();

        for(Question q:questions)
            if(q.getPower()==0)
                newQuestions.add(q);

        log.info(questions.toString());
        return objectMapper.writeValueAsString(newQuestions);
    }

    @GetMapping("/id")
    public String getQuesById(@RequestParam Long id) throws JsonProcessingException{
        Question question = questionMapper.selectOne(id);
        if (question==null)
            return "1";
        return objectMapper.writeValueAsString(question);
    }


    /**
     *将code和开发语言发送给云服务器，云服务器实行编译
     * @param result 接收前端的json格式的对象
     * @return 代码执行状态
     */
    @PostMapping("/result")
    public Message<Float> postQuestion(@RequestBody Result result) throws IOException, InterruptedException {
        Long id = BaseContext.getCurrentId();
        return questionService.questionResult(result.getId(), result.getCode(), result.getLanguage());
    }

    /**
     * 添加问题 确定访问权限为管理员
     * @param questionPro 接收问题
     */
    @PostMapping("/add")
    public Message<String> createQuestion(@RequestBody QuestionPro questionPro){
        Long createId = BaseContext.getCurrentId();
        User user = userMapper.selectById(createId);
        if(user.getPower()==2){ //只有管理员可以insert
            questionService.addQuestion(questionPro);
            return Message.success("add success");
        }
        else return Message.error("not admin");
    }


}

package com.oj.ojspringboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oj.ojspringboot.entity.Message;
import com.oj.ojspringboot.entity.Question;
import com.oj.ojspringboot.entity.QuestionPro;

import java.io.IOException;


/**
 * @author ld
 * @since 1.8
 * @version 17
 */
public interface QuestionService extends IService<Question>{
    Message<Float> questionResult(Long id,String code,String language) throws IOException, InterruptedException;

    boolean addQuestion(QuestionPro questionPro);
}

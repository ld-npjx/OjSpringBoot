package com.oj.ojspringboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oj.ojspringboot.common.BaseContext;
import com.oj.ojspringboot.entity.ExecFile;
import com.oj.ojspringboot.entity.Message;
import com.oj.ojspringboot.entity.Question;
import com.oj.ojspringboot.entity.QuestionPro;
import com.oj.ojspringboot.mapper.QuestionMapper;
import com.oj.ojspringboot.service.QuestionService;
import com.oj.ojspringboot.util.redisApiUtil;
import com.oj.ojspringboot.util.strToFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * question服务层
 * @author ld
 * @since 1.8
 * @version 17
 */
@Slf4j
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {
    @Value("${path.filePath}")
    private String pathFile;

    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    CompileServiceImpl compileService;

    private static Integer num=0;

    private static Long questionId=1l;
    /**
     * 编译和运行Java文件
     *
     * @param code     代码块
     * @param id       问题的id
     * @param language code‘language
     */
    @Override
    public Message<Float> questionResult(Long id, String code, String language) throws IOException, InterruptedException {
        Message<Float> Msg = new Message<>();
        String fileName="";

        String path=pathFile + num.toString() + "." + language;
        fileName= num +"."+language;
        //Java只能通过类作为文件名来执行  所以前端代码只能写为Main的主类
        if(Objects.equals(language, "java")) {
            path = pathFile + "Main.java";
            fileName="Main.java";
        }
        boolean result = strToFileUtil.strToFile(code, path);
        //将代码块转为文件

        if(result&& !Objects.equals(language, "java")) num++;
        //查看文件是否创建成功
        if(!result) {
            File file=new File(path);
            if(file.exists())
                file.delete();
            Msg.setMsg("file mkdir false");
            return Msg;
        }
        //compile
        ExecFile compile = compileService.compile(pathFile,fileName);
        //run
        float run = compileService.run(id, compile.getExecFilePath());//测试通过概率

        Msg.setData(run);
        Msg.setMsg("1");
        return Msg;
    }

    /**
    * 增加题目，注意格式判断
     * @param questionPro 题目参数，用来添加题目
    */
    @Override
    public boolean addQuestion(QuestionPro questionPro) {
        //setRedis
        boolean redisSet = redisApiUtil.redisSetKey(questionId.toString(), questionPro.getResult());

        Question question=new Question();
        assert false;
        question.setCreateId(BaseContext.getCurrentId());
        question.setCreateTime(LocalDateTime.now());
        question.setDescription(questionPro.getDescription());
        question.setPower(questionPro.getPower());
        question.setTitle(questionPro.getTitle());
        question.setId(questionId);
        int insert = questionMapper.insert(question);
        if(insert!=0)
            questionId++;
        else
            return false;
        return redisSet;
    }

}

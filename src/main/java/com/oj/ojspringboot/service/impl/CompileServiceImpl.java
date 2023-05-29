package com.oj.ojspringboot.service.impl;

import com.oj.ojspringboot.entity.ExecFile;
import com.oj.ojspringboot.service.CompileService;
import com.oj.ojspringboot.util.redisApiUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 编译代码的实现类
 * 通过Java程序执行Linux终端命令，执行shell脚本编译code
 * @author ld
 * @version 17
 * @since 1.8
 */
@Slf4j
@Service
public class CompileServiceImpl implements CompileService {
    @Value("${path.shellPathRun}")
    private String shellPathRun;//运行程序的脚本

    @Value("${path.shellPathCode}")
    private String shellPathCode;//运行编译命令的脚本

    @Value("${path.filePathCompiled}")
    private String filePath;//编译后的文件路径

    private static Integer num=0;
    /**
     * 执行Linux/shellScript
     * @param pathFile 执行文件的地址
     * @return 返回编译结果
     */
    @Override
    public ExecFile compile(String pathFile,String fileName) throws IOException, InterruptedException {
        StringBuilder str = new StringBuilder();//process输出流
        String[] shells={"bash",shellPathCode,pathFile+fileName,fileName};
        log.info(Arrays.toString(shells));
        //执行服务器脚本代码
        Process exec = Runtime.getRuntime().exec(shells);
        exec.waitFor();

        InputStream in = exec.getInputStream();
        BufferedReader read = new BufferedReader(new InputStreamReader(in));

        String line;
        while((line = read.readLine())!=null){
            assert false;
            str.append(line);
        }
        assert false;
        //设置编译后的文件名
        ExecFile ef=new ExecFile(str.toString(),filePath+"/"+fileName);
        //返回信息值
//        if(Objects.equals(pathFile.split("\\.")[1], "java"))
//            ef.setExecFilePath("Main");
        num++;
        return ef;
    }

    /**
     * @param questionId 通过questionId拿到题目的输入参数和对应的结果
     * @param filePath 可执行文件的路径
     * @return 对比编译的结果，返回正确率 小数0-1
     */
    @Override
    public float run(Long questionId,String filePath) throws IOException, InterruptedException {
        int success=0;
        //去redis中查找参数和结果  value格式为参数1，参数2；结果1，结果2
        StringBuilder str = new StringBuilder(redisApiUtil.redisGetKey(questionId.toString()));
        log.info(str.toString());
        String[] split = str.toString().split(";");
        List<String> inputStr = Arrays.asList(split[0].split(","));
        List<String> resultStr= Arrays.asList(split[1].split(","));


        //执行代码
        for(int i=0;i< inputStr.size();i++){
            str=new StringBuilder();
            String[] shells={"bash",shellPathRun,filePath,inputStr.get(i)};
            log.info(Arrays.toString(shells));
            Process exec = Runtime.getRuntime().exec(shells);
            exec.waitFor();

            InputStream in = exec.getInputStream();
            BufferedReader read = new BufferedReader(new InputStreamReader(in));

            String line;
            while((line = read.readLine())!=null){
                str.append(line);
            }
            if (Objects.equals(str.toString(), resultStr.get(i))) {
                success++;
            }
        }

        return Math.round(success*1.0/inputStr.size());
    }
}

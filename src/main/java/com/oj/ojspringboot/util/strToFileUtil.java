package com.oj.ojspringboot.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * 字符转文件的方法类
 * @author ld
 * @version 17
 * @since 1.8
 */
@Slf4j

public class strToFileUtil {
    /**
     * 将str转换为文件
     * @param code Str
     * @param path 文件的输出路径
     */

    public static boolean strToFile(String code,String path){
        boolean flag = true;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter;
        log.info(code);
        try {
            File distFile = new File(path);
            //判断文件是否存在  根据Java文件编译特性来考虑  其他不够重要
            if(distFile.exists()) {
                distFile.delete();
                distFile.createNewFile();
            }else
                distFile.createNewFile();
//            if (!distFile.getParentFile().exists()) distFile.getParentFile().mkdirs();
            bufferedReader = new BufferedReader(new StringReader(code));
            bufferedWriter = new BufferedWriter(new FileWriter(distFile));
            char buf[] = new char[1024];         //字符缓冲区
            int len;
            while ((len = bufferedReader.read(buf)) != -1) {
                bufferedWriter.write(buf, 0, len);
            }
            bufferedWriter.flush();
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }
}

package com.oj.ojspringboot.service;

import com.oj.ojspringboot.entity.ExecFile;
import com.oj.ojspringboot.entity.Message;

import java.io.IOException;
import java.util.List;

/**
 * @author ld
 * @version 17
 * @since 1.8
 */
public interface CompileService {
    ExecFile compile(String filePath,String fileName) throws IOException, InterruptedException;

    float run(Long questionId,String filePath) throws IOException, InterruptedException;
}

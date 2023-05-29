package com.oj.ojspringboot.entity;

import lombok.Data;

@Data
public class ExecFile {
    private String msg;//执行信息

    private String execFilePath;//可执行文件路径

    public ExecFile(String msg, String execFilePath) {
        this.msg=msg;
        this.execFilePath=execFilePath;
    }
}

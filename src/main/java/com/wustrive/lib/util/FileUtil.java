package com.wustrive.lib.util;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import com.xiaoleilu.hutool.log.Log;
import com.xiaoleilu.hutool.log.LogFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: 文件工具类
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/4/26 15:30
 * @Copyright: 2017-2018 dgztc Inc. All rights reserved.
 */
public class FileUtil {
    protected static final Log log = LogFactory.get(FileUtil.class);

    private String fileName;

    private Charset charset;

    public static final String defalut_mode = "append";

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public FileUtil(String fileName, Charset charset) {
        this.fileName = fileName;
        this.charset = charset;
    }

    /**
     * 写文件
     *
     * @param contents
     * @param mode     文件写入模式
     */
    public void fileWrite(final String contents, final String mode) {
        final File newFile = new File(this.fileName);
        try {
            if ("write".equals(mode)) {
                Files.write((CharSequence) new String(contents + "\n"), newFile, this.charset);
            } else {
                Files.append((CharSequence) new String(contents + "\n"), newFile, this.charset);
            }
        } catch (IOException e) {
            log.error("文件内容写入异常", e);
        }
    }

    /**
     * 读取小文件 一次性读取
     *
     * @return
     * @throws IOException
     */
    public List<String> readSmallFile() throws IOException {
        File testFile = new File(this.fileName);
        List<String> lines = Files.readLines(testFile, this.charset);
        return lines;
    }

    /**
     * 大文件的读取
     *
     * @return
     * @throws IOException
     */
    public List<String> readLargeFile() throws IOException {
        CounterLine counter = new CounterLine();
        Files.readLines(new File(this.fileName), this.charset, counter);
        return counter.getResult();
    }

    static class CounterLine implements LineProcessor<List<String>> {
        private List<String> lines = new ArrayList<String>();

        public boolean processLine(String line) throws IOException {
            lines.add(line);
            return true;
        }

        public List<String> getResult() {
            return lines;
        }
    }

    public static void main(String[] args) throws IOException {
        FileUtil fileUtil = new FileUtil("D:/test.md", Charsets.UTF_8);
        fileUtil.fileWrite("hello md", "append");

        List<String> lines = fileUtil.readSmallFile();
        for (String string : lines) {
            log.error("{}", string);
        }

        lines = fileUtil.readLargeFile();
        for (String string : lines) {
            log.error("{}", string);
        }
    }
}

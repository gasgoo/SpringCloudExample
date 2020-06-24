package com.common.utils;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;

/**
 * @date: 2019-05-16
 */
public class Util {
    /**
     * 根据相对路径获取文件内容
     */
    public static String getTxt(String fileName) {
        Resource resource = new ClassPathResource(fileName);
        try {
            File file = resource.getFile();
            return FileUtils.readFileToString(file, "utf-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 方法首字母小写
     *
     * @param str
     * @return
     */
    public static String toLowerCaseFirstOne(String str) {
        if (Character.isLowerCase(str.charAt(0)))
            return str;
        else
            return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }


    public static String toUpperCaseFirstOne(String str) {
        if (Character.isUpperCase(str.charAt(0)))
            return str;
        else
            return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

}

package com.duguyin.mybatissql.tool;

import com.duguyin.mybatissql.exceptions.ParseException;

import java.util.Objects;

/**
 * @ClassName StringTool
 * @Description
 * @Author LiuYin
 * @Date 2018/9/28 11:36
 */
public class StringTool {


    public static boolean isNullOrEmpty(String str){
        return Objects.isNull(str) || str.isEmpty();
    }

    public static boolean isNotEmpty(String str){
        return !isNullOrEmpty(str);
    }

    public static boolean hasAnyEmpty(String... strings){
        if(Objects.isNull(strings) || strings.length == 0){
            throw new ParseException("string array is null or empty");
        }
        for (String str : strings) {
            if (isNullOrEmpty(str)) {
                return true;
            }
        }
        return false;
    }


}

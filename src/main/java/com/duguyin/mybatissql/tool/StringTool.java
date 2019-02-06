package com.duguyin.mybatissql.tool;

import com.duguyin.mybatissql.exceptions.ParseException;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName StringTool
 * @Description
 * @Author LiuYin
 * @Date 2018/9/28 11:36
 */
public class StringTool {


    public static boolean isNullOrEmpty(String str) {
        return Objects.isNull(str) || str.isEmpty() ;
    }

    public static boolean isNotEmpty(String str) {
        return !isNullOrEmpty(str);
    }

    private static final Pattern NUMBER_PATTERN = Pattern.compile("-?[0-9]+(\\.[0-9]+)?");

    public static boolean hasAnyEmpty(String... strings) {
        if (Objects.isNull(strings) || strings.length == 0) {
            throw new ParseException("string array is null or empty");
        }
        for (String str : strings) {
            if (isNullOrEmpty(str)) {
                return true;
            }
        }
        return false;
    }

    public static String withMybatisFormat(String column) {
        if (isNullOrEmpty(column)) {
            throw new RuntimeException("column is null or empty");
        }
        return "#{" + column + "}";
    }

    /**
     * 匹配是否为数字
     *
     * @param str 可能为中文，也可能是-19162431.1254，不使用BigDecimal的话，变成-1.91624311254E7
     * @return
     * @author yutao
     * @date 2016年11月14日下午7:41:22
     */
    public static boolean isNumeric(String str) {
        // 该正则表达式可以匹配所有的数字 包括负数

        String bigStr;
        try {
            bigStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            return false;//异常 说明包含非数字。
        }

        Matcher isNum = NUMBER_PATTERN.matcher(bigStr); // matcher是全匹配
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 校验圆括号是否对称
     *
     * @param property
     */
    public static void checkBrackets(String property) {
        if (isNullOrEmpty(property)) {
            throw new RuntimeException("property is null or empty");
        }

        final char[] chars = property.toCharArray();
        int halfBrackets = 0;
        for (char aChar : chars) {
            if (aChar == '(') {
                halfBrackets += 1;
            }
            if (aChar == ')') {
                halfBrackets -= 1;
            }
        }
        if (halfBrackets != 0) {
            throw new ParseException("asymmetric '(' or ')'");
        }
    }

    /**
     * 去掉所用的空白
     * @param str
     * @return
     */
    public static String trimAllWhitespace(String str) {
        if (Objects.isNull(str)) {
            return null;
        }

        int len = str.length();
        if (len == 0) {
            return str;
        }
        StringBuilder sb = new StringBuilder(len);

        for (int i = 0; i < len; ++i) {
            char c = str.charAt(i);
            if (!Character.isWhitespace(c)) {
                sb.append(c);
            }
        }

        return sb.toString();
    }


}

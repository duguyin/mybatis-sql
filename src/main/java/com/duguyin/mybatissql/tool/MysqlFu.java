package com.duguyin.mybatissql.tool;

/**
 * @ClassName MysqlFu
 * @Description Mysql 函数表达式
 * @Author LiuYin
 * @Date 2018/12/7 9:41
 */
public class MysqlFu {

    private static final String FU_COUNT = "count";


    public static String count(String property) {
        return FU_COUNT + "(" + property + ")";
    }


}

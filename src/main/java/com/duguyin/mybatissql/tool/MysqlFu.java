package com.duguyin.mybatissql.tool;

/**
 * @ClassName MysqlFu
 * @Description Mysql 函数表达式
 * @Author LiuYin
 * @Date 2018/12/7 9:41
 */
public class MysqlFu {

    /** 函数count*/
    private static final String FU_COUNT = "count";


    /**
     * count表达式拼接
     * @param property 表达式内容
     * @return 表达式
     */
    public static String count(String property) {
        return FU_COUNT + "(" + property + ")";
    }


}

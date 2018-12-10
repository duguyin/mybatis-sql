package com.duguyin.mybatissql.tool;

import com.duguyin.mybatissql.exceptions.ParseException;
import com.duguyin.mybatissql.obj.PropertyColumnMapping;

import java.util.Map;
import java.util.Objects;

/**
 * @ClassName MybatisTool
 * @Description
 * @Author LiuYin
 * @Date 2018/12/10 11:01
 */
public class MybatisTool {

    private static final String CLASS_NAME_SEPARATOR_REGEX = "\\.";
    private static final String DEFAULT_SEPARATOR = "_";
    private static final String MYBATIS_REFRENCE_SYMBOL = "#{";


    public static String getColumn(String property, Map<String, PropertyColumnMapping> mappingMap) {
        if (StringTool.isNullOrEmpty(property)) {
            throw new RuntimeException("property is null or empty");
        }

        if (Objects.requireNonNull(StringTool.trimAllWhitespace(property)).startsWith(MYBATIS_REFRENCE_SYMBOL)) {
            return property;
        }

        if (Objects.isNull(mappingMap)) {
            throw new RuntimeException("mapping map is null");
        }

        final PropertyColumnMapping pcm = mappingMap.get("property");
        return Objects.isNull(pcm) ? property : pcm.getColumn();

    }

    public static String parseResultMapId(Class clazz) {
        Objects.requireNonNull(clazz);

        return parseResultMapId(clazz.getSimpleName());

    }

    public static String parseResultMapId(String className) {
        if (StringTool.isNullOrEmpty(className)) {
            throw new ParseException("class name is null or empty");
        }

//        String replacedString = className.replaceAll(CLASS_NAME_SEPARATOR_REGEX, DEFAULT_SEPARATOR);
        return DEFAULT_SEPARATOR + className + DEFAULT_SEPARATOR;

    }

    /**
     * 判断是不是mysql中的关键字
     * @return
     */
    public static boolean isMysqlKeyWord(String string){

    }


}

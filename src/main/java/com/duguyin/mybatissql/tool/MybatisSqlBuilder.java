package com.duguyin.mybatissql.tool;

import com.duguyin.mybatissql.obj.MybatisDomainSqlPool;
import com.duguyin.mybatissql.obj.MybatisMapping;
import com.duguyin.mybatissql.obj.PropertyColumnMapping;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class MybatisSqlBuilder {

    private static  Configuration CONFIGURATION = null;

    public static void setConfiguration(Configuration configuration){
        CONFIGURATION = configuration;
    }

    private static final String BASE_FRAGMENT = "_BASE_FRAGMENT";

    private static final Map<String, ConcurrentHashMap<String,String>>  SQL_MAP = new ConcurrentHashMap<>();
    private static  final Map<String, MybatisDomainSqlPool>  MAPPING_MAP = new ConcurrentHashMap<>();

    private  static <T> void from(Configuration configuration,Class<T> clazz){
        Objects.requireNonNull(clazz);
        Objects.requireNonNull(configuration);

        // 缓存到map中，以后直接使用
        final MybatisMapping<T> mapping = MybatisMapping.from(clazz);
        final String name = clazz.getName();
        MAPPING_MAP.putIfAbsent(name, new MybatisDomainSqlPool<T>(mapping));

        Map<String, PropertyColumnMapping> defaultMappingMap = mapping.getDefaultMappingMap();
        final Set<Map.Entry<String, PropertyColumnMapping>> set = defaultMappingMap.entrySet();
        List<ResultMapping> resultMappings = new ArrayList<>(set.size());

        for (Map.Entry<String, PropertyColumnMapping> entry : set) {
            final PropertyColumnMapping value = entry.getValue();
            final ResultMapping resultMapping = new ResultMapping.Builder(configuration, value.getProperty(), value.getColumn(), value.getJavaType()).build();
            resultMappings.add(resultMapping);

        }

        String resultMapId = MybatisTool.parseResultMapId(clazz);
        final ResultMap resultMap = new ResultMap.Builder(configuration, resultMapId, clazz, resultMappings).build();
        configuration.addResultMap(resultMap);




    }

    public static String sql(){

        return "";
    }






    public static <T> void from (Configuration configuration, Class<T>... classes){
        Objects.requireNonNull(classes);

        if(Objects.isNull(CONFIGURATION)){
            CONFIGURATION = configuration;
        }

        for (Class aClass : classes) {
            from(configuration,aClass);
        }
    }













    private boolean isEmpty(String s){
        return Objects.nonNull(s) && s.length() > 0 && s.trim().length() > 0;
    }

    private String withBrackets(String s) {
        return "(" + s + ")";
    }







}

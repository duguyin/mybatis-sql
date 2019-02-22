package com.duguyin.mybatissql.tool;

import com.duguyin.mybatissql.obj.MybatisDomainSql;
import com.duguyin.mybatissql.obj.MybatisMapping;
import com.duguyin.mybatissql.obj.PropertyColumnMapping;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.Configuration;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * Mybatis的Sql语句构建器
 */
public class MybatisSqlBuilder {

    private static  Configuration CONFIGURATION = null;

    public static void setConfiguration(Configuration configuration){
        CONFIGURATION = configuration;
    }

    private static final String BASE_FRAGMENT = "_BASE_FRAGMENT";

    private static final Map<String, ConcurrentHashMap<String,String>>  SQL_MAP = new ConcurrentHashMap<>();
    /** 每个domain类Sql的map，key是domain的简单类名*/
    private static  final Map<String, MybatisDomainSql>  MAPPING_MAP = new ConcurrentHashMap<>();

    private  static <T> void from(Configuration configuration,Class<T> clazz){
        // 非空判断
        Objects.requireNonNull(clazz);
        Objects.requireNonNull(configuration);

        // 缓存到map中，以后直接使用
        final MybatisMapping<T> mapping = MybatisMapping.from(clazz);
        final String name = clazz.getName();
        MAPPING_MAP.putIfAbsent(name, new MybatisDomainSql<>(mapping));


        Map<String, PropertyColumnMapping> defaultMappingMap = mapping.getDefaultMappingMap();
        final Set<Map.Entry<String, PropertyColumnMapping>> set = defaultMappingMap.entrySet();
        // 创建resultMapping（列名与属性名的映射关系）
        List<ResultMapping> resultMappings = new ArrayList<>(set.size());
        for (Map.Entry<String, PropertyColumnMapping> entry : set) {
            final PropertyColumnMapping value = entry.getValue();
            final ResultMapping resultMapping = new ResultMapping.Builder(configuration, value.getProperty(), value.getColumn(), value.getJavaType()).build();
            resultMappings.add(resultMapping);
        }

        String resultMapId = MybatisTool.parseResultMapId(clazz);
        // 调用mybatis的类，封装成resultMap映射，默认名称为"_xxx_"
        final ResultMap resultMap = new ResultMap.Builder(configuration, resultMapId, clazz, resultMappings).build();
        configuration.addResultMap(resultMap);
    }

    public static String sql(){

        return "";
    }

    public static class CacheSql{
        /** 类名称*/
        private String className;
        /** 语句提供方法*/
        private Supplier<String> sqlSupplier;
        /** 缓存名称*/
        private String cacheName;

        public String cache(String cacheName){
            Objects.requireNonNull(sqlSupplier);
            Objects.requireNonNull(cacheName);

            final ConcurrentHashMap<String, String> classSqlMap = SQL_MAP.getOrDefault(className, new ConcurrentHashMap<>());
            String sql = classSqlMap.get(cacheName);
            if(StringTool.isNullOrEmpty(sql)){
                sql = sqlSupplier.get();
                if(StringTool.isNullOrEmpty(sql)){
                    throw new RuntimeException("sql from cache or supplier is null or empty");
                }
                classSqlMap.putIfAbsent(cacheName, sql);
                SQL_MAP.putIfAbsent(className,classSqlMap);
            }
            return sql;
        }

        public String cache(){
            return cache(getCacheName());
        }

        public String noCache(){
            Objects.requireNonNull(sqlSupplier);
            Objects.requireNonNull(cacheName);

            return sqlSupplier.get();
        }

        public CacheSql supplier(Supplier<String> sqlSupplier){
            this.sqlSupplier = sqlSupplier;
            return this;
        }

        public CacheSql sql(String str){
            this.sqlSupplier = () -> str;
            return this;
        }

        public CacheSql baseInsertSql(boolean includePrimaryKey){
            this.sqlSupplier = () -> getDomainSql().baseInsertSql(includePrimaryKey);
            this.cacheName = "_BASE_INSERT_SQL_"+String.valueOf(includePrimaryKey).toUpperCase()+"_";
            return this;
        }

        public CacheSql baseInsertSql(){
            this.sqlSupplier = () -> getDomainSql().baseInsertSql();
            this.cacheName = "_BASE_INSERT_SQL_";
            return this;
        }

        public CacheSql baseDeleteSql(String conditionProperty){
            this.sqlSupplier = () -> getDomainSql().baseDeleteSql(conditionProperty);
            this.cacheName = "_BASE_DELETE_SQL_"+conditionProperty.toUpperCase()+"_";
            return this;
        }

        public CacheSql baseDeleteSqlByPrimaryKey(){
            this.sqlSupplier = () -> getDomainSql().baseDeleteSqlByPrimaryKey();
            this.cacheName = "_BASE_DELETE_SQL_BY_PRIMARY_KEY_";
            return this;
        }

        public CacheSql baseUpdateSql(String conditionProperty){
            this.sqlSupplier = () -> getDomainSql().baseUpdateSql(conditionProperty);
            this.cacheName = "_BASE_UPDATE_SQL_"+conditionProperty.toUpperCase()+"_";
            return this;
        }

        public CacheSql baseUpdateSqlByPrimaryKey(){
            this.sqlSupplier = () -> getDomainSql().baseUpdateSqlByPrimaryKey();
            this.cacheName = "_BASE_UPDATE_SQL_BY_PRIMARY_KEY_";
            return this;
        }

        public CacheSql baseCountSql(String countProperty){
            this.sqlSupplier = () -> getDomainSql().baseCountSql(countProperty);
            this.cacheName = "_BASE_COUNT_SQL_"+countProperty.toUpperCase()+"_";
            return this;
        }

        public CacheSql baseCountSqlByPrimaryKey(){
            this.sqlSupplier = () -> getDomainSql().baseCountSqlByPrimaryKey();
            this.cacheName = "_BASE_COUNT_SQL_BY_PRIMARY_KEY_";
            return this;
        }

        public CacheSql baseSelectSqlByLimit(String beginProperty, String offsetProperty){
            this.sqlSupplier = () -> getDomainSql().baseSelectSqlByLimit(beginProperty, offsetProperty);
            this.cacheName = "_BASE_SELECT_SQL_BY_LIMIT_"+beginProperty.toUpperCase()+"_"+offsetProperty.toUpperCase()+"_";
            return this;
        }

        public CacheSql baseSelectSqlByLimit(String offsetProperty){
            this.sqlSupplier = () -> getDomainSql().baseSelectSqlByLimit(offsetProperty);
            this.cacheName = "_BASE_SELECT_SQL_BY_LIMIT_"+offsetProperty.toUpperCase()+"_";
            return this;
        }

        public CacheSql baseSelectSql(String conditionProperty){
            this.sqlSupplier = () -> getDomainSql().baseSelectSql(conditionProperty);
            this.cacheName = "_BASE_SELECT_SQL_"+conditionProperty.toUpperCase()+"_";
            return this;
        }

        public CacheSql baseSelectSqlByPrimaryKey(){
            this.sqlSupplier = () -> getDomainSql().baseSelectSqlByPrimaryKey();
            this.cacheName = "_BASE_SELECT_SQL_BY_PRIMARY_KEY_";
            return this;
        }

        private MybatisDomainSql getDomainSql(){
            final MybatisDomainSql mybatisDomainSql = MAPPING_MAP.get(className);
            Objects.requireNonNull(mybatisDomainSql, "not found domain named \""+className +"\" registered in MybatisSqlBuilder");
            return mybatisDomainSql;
        }


        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }


        public Supplier<String> getSqlSupplier() {
            return sqlSupplier;
        }

        public void setSqlSupplier(Supplier<String> sqlSupplier) {
            this.sqlSupplier = sqlSupplier;
        }

        public String getCacheName() {
            return cacheName;
        }

        public void setCacheName(String cacheName) {
            this.cacheName = cacheName;
        }

        public CacheSql useSql(String sql){
            this.sqlSupplier = () -> sql;
            return this;
        }
    }

    public static CacheSql useDomain(Class clazz){
        Objects.requireNonNull(clazz);
        final CacheSql cacheSql = new CacheSql();
        cacheSql.setClassName(clazz.getName());
        return cacheSql;
    }








    public static  void from (Configuration configuration, Class<?>... classes){
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

    private String withEscape(String s){
        return "`" + s + "`";
    }







}

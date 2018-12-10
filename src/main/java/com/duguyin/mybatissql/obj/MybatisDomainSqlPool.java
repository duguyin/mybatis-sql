package com.duguyin.mybatissql.obj;

import com.duguyin.mybatissql.enums.ComparisonOperator;
import com.duguyin.mybatissql.enums.LogicOperator;
import com.duguyin.mybatissql.exceptions.ParseException;
import com.duguyin.mybatissql.tool.StringTool;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName MybatisDomainSqlPool
 * @Description domain sql 语句池，专门用来放sql
 * @Author LiuYin
 * @Date 2018/9/27 18:37
 */
public class MybatisDomainSqlPool<T> {

    /**
     * Mybatis映射
     */
    private MybatisMapping<T> mapping;

    /**
     * 函数的匹配表达式
     */
    private static final Pattern FUNCTION_PATTERN = Pattern.compile("\\(.*\\)");


    private MybatisDomainSqlPool() {
    }

    public MybatisDomainSqlPool(MybatisMapping<T> mybatisMapping) {
        Objects.requireNonNull(mybatisMapping, "mybatis mapping is null");
        this.mapping = mybatisMapping;

    }

    /**
     * 基本操作
     */
    private enum OP {
        /**
         * 新增
         */
        INSERT("INSERT INTO"),
        /**
         * 删除
         */
        DELETE("DELETE"),
        /**
         * 查询
         */
        SELECT("SELECT"),
        /**
         * 更新
         */
        UPDATE("UPDATE"),
        ;

        /**
         * 操作命令
         */
        String op;

        OP(String op) {
            this.op = op;
        }

        String getOp() {
            return this.op;
        }
    }


    public String insertSql() {
        final MybatisSQL mybatisSQL = new MybatisSQL();
        mybatisSQL.tableName(mapping.getTableName());
        mybatisSQL.OP(OP.INSERT);
        mybatisSQL.properties.add("id");
        mybatisSQL.properties.add("age");
        mybatisSQL.setWhere(new LogicFragment(mapping.getDefaultMappingMap()));

        mybatisSQL.WHERE(new LogicFragment("id").and("age")).AND(new LogicFragment("id").or("age")).OR(new LogicFragment("age").and(new LogicFragment("id").and("age")));
//        return mybatisSQL.where.toSqlFragment();

//        mybatisSQL.getSql2();
        return mybatisSQL.getSql();
    }

    public String selectSql() {
        final MybatisSQL mybatisSQL = new MybatisSQL();
        mybatisSQL.tableName(mapping.getTableName());
        mybatisSQL.OP(OP.SELECT);
        mybatisSQL.properties.add("id");
        mybatisSQL.properties.add("count(beforeResult,count(abs(beforeResult))))");
        mybatisSQL.WHERE(new LogicFragment("id"));
        return mybatisSQL.getSql();
    }

    public String updateSql() {
        final MybatisSQL mybatisSQL = new MybatisSQL();
        mybatisSQL.tableName(mapping.getTableName());
        mybatisSQL.OP(OP.UPDATE);
        mybatisSQL.properties.add("id");
        mybatisSQL.properties.add("age=age+1");
        mybatisSQL.WHERE(new LogicFragment("id"));
        return mybatisSQL.getSql();
    }

    public String deleteSql() {

        final MybatisSQL mybatisSQL = new MybatisSQL();
        mybatisSQL.tableName(mapping.getTableName());
        mybatisSQL.OP(OP.DELETE);
        mybatisSQL.WHERE(new LogicFragment(new CompareFragment().column("beforeResult").operator(ComparisonOperator.IN).value("dog,pig,cat")));
        return mybatisSQL.getSql();
    }


    /**
     * MybatisSql类，用来描述和生成MybatisSql
     */
    public class MybatisSQL {

        /**
         * 表名称
         */
        String tableName;
        /**
         * 操作
         */
        OP op;

        /**
         * 列名称
         */
        List<String> properties = new ArrayList<>();
        /**
         * 批量新增次数
         */
        int insertRepeatCount = 1;

        /**
         * where条件逻辑
         */
        LogicFragment where;

        /**
         * group by 字段
         */
        List<String> groupByProperties = new ArrayList<>();

        /**
         * having 字段
         */
        LogicFragment having;

        /**
         * limit语句
         */
        Integer[] limit = new Integer[2];


        private MybatisSQL(){}

        public void setWhere(LogicFragment where) {
            this.where = where;

        }

        public MybatisSQL GROUP_BY(String properties) {
            if (StringTool.isNullOrEmpty(properties)) {
                throw new ParseException("group by sql is null or empty");
            }
            final String[] split = properties.split(",");

            for (int i = 0; i < split.length; i++) {
                split[i] = StringTool.trimAllWhitespace(split[i]);
                final Map<String, PropertyColumnMapping> defaultMappingMap = mapping.getDefaultMappingMap();
                final PropertyColumnMapping propertyColumnMapping = defaultMappingMap.get(split[i]);
                String column = propertyColumnMapping.getColumn();
                groupByProperties.add(Objects.isNull(column) ? split[i] : column);
            }
            return this;
        }

        public MybatisSQL HAVING(LogicFragment logicFragment) {
            logicFragment.setMappingMap(mapping.getDefaultMappingMap());
            this.having = logicFragment;

            return this;
        }


        public MybatisSQL WHERE(LogicFragment logicFragment) {
            logicFragment.setMappingMap(mapping.getDefaultMappingMap());
//            this.where.addChild(where);
            this.where = logicFragment;

            return this;
        }


        public MybatisSQL AND(LogicFragment logicFragment) {
            this.where.addChild(logicFragment, LogicOperator.AND);
            return this;
        }

        public MybatisSQL OR(LogicFragment logicFragment) {
            this.where.addChild(logicFragment, LogicOperator.OR);
            return this;
        }

        public MybatisSQL tableName(String tableName) {
            this.tableName = tableName;
            return this;
        }

        public MybatisSQL OP(OP op) {
            this.op = op;
            return this;
        }

        public MybatisSQL colums(String... columns) {
            Objects.requireNonNull(columns, "properties is null");
            this.properties.addAll(Arrays.asList(columns));
            return this;
        }

        public MybatisSQL LIMIT(Integer begin, Integer offset) {
            this.limit[0] = begin;
            this.limit[1] = offset;
            return this;
        }

        public MybatisSQL LIMIT(Integer offset) {
            this.limit[0] = offset;
            this.limit[1] = null;
            return this;
        }


        private String getSql() {
            if (StringTool.isNullOrEmpty(tableName)) {
                throw new ParseException("table name is null or empty");
            }
            StringBuilder sql = new StringBuilder();
            sql.append(op.getOp()).append(" ");

            // 新增
            if (OP.INSERT == op) {
                sql.append(tableName);
                sql.append(getInsertColumns(properties)).append(" values ");
                sql.append(getMybatisInsertValueColumns(properties, insertRepeatCount));

                // 更新
            } else if (OP.UPDATE == op) {
                sql.append(tableName).append(" SET ");
                sql.append(getUpdateSqlFragment(properties));

                // 查询
            } else if (OP.SELECT == op) {
                sql.append(getSelectColumns(properties)).append(" from ").append(tableName);

                // 删除
            } else if (OP.DELETE == op) {
                sql.append("from ").append(tableName);
            }


            if (Objects.nonNull(where)) {
                sql.append(" WHERE ");
                sql.append(where.toSqlFragment());
            }

            if (Objects.nonNull(groupByProperties) && !groupByProperties.isEmpty()) {
                sql.append(" GROUP BY ");

                final int size = groupByProperties.size();
                for (int i = 0; i < size; i++) {
                   sql.append(properties.get(i));
                   if(i < size - 1){
                       sql.append(", ");
                   }
                }
            }

            if (Objects.nonNull(having)){
                sql.append(having.toSqlFragment());
            }


            if (this.limit[0] != null) {
                sql.append(" LIMIT ").append(this.limit[0]);
                if (this.limit[1] != null) {
                    sql.append(", ").append(this.limit[1]);
                }
            }

            return sql.toString();
        }

        public String getSql2() {
            List<String> list = new ArrayList<>();
            list.add("id");
            list.add("age");
            list.add("createTime");
            list.add("beforeResult");

            return getUpdateSqlFragment(list);
        }


        private String getUpdateSqlFragment(List<String> properties) {
            Objects.requireNonNull(properties, "update properties is null");
            int size = properties.size();
            if (size == 0) {
                throw new ParseException("update properties is empty");
            }
            for (String property : properties) {
                if (Objects.isNull(property) || property.isEmpty()) {
                    throw new ParseException("update properties has empty value");
                }
            }

            // update user_table set name = #{name}, age = #{age} where id = #{id}
            final Map<String, PropertyColumnMapping> mappingMap = mapping.getDefaultMappingMap();
            if (Objects.isNull(mappingMap) || mappingMap.isEmpty()) {
                throw new ParseException("mapping map is null or empty");
            }
            StringBuilder builder = new StringBuilder();
            builder.append(" ");
            for (int i = 0; i < size; i++) {
                String property = properties.get(i);
                final String[] split = property.split("=");
                for (int j = 0; j < split.length; j++) {
                    split[j] = split[j].trim();
                }

                final PropertyColumnMapping propertyColumnMapping = mappingMap.get(split[0]);
                String column = Objects.isNull(propertyColumnMapping) ? split[0] : propertyColumnMapping.getColumn();
                String mybatisColumn = StringTool.withMybatisFormat(column);
                if (split.length == 1) {
                    builder.append(column).append("=").append(mybatisColumn);
                } else {
                    builder.append(column).append("=").append(split[1].replace(split[0], mybatisColumn));
                }
                if (i < size - 1) {
                    builder.append(", ");
                }

            }
            builder.append(" ");
            return builder.toString();
        }

        private String getInsertColumns(List<String> properties) {
            Objects.requireNonNull(properties, "insert properties is null");
            int size = properties.size();
            if (size == 0) {
                throw new ParseException("insert properties is empty");
            }
            Map<String, PropertyColumnMapping> defaultMappingMap = mapping.getDefaultMappingMap();
            StringBuilder insertColumnsBuilder = new StringBuilder();
            insertColumnsBuilder.append(" (");
            for (int i = 0; i < size; i++) {
                String property = properties.get(i);
                if (Objects.isNull(defaultMappingMap)) {
                    insertColumnsBuilder.append(property);
                } else {
                    PropertyColumnMapping pcm = defaultMappingMap.get(property);
                    insertColumnsBuilder.append(Objects.isNull(pcm) ? property : pcm.getColumn());
                }
                if (i < size - 1) {
                    insertColumnsBuilder.append(", ");
                }
            }
            insertColumnsBuilder.append(" ) ");
            return insertColumnsBuilder.toString();
        }

        private String getSelectColumns(List<String> properties) {
            Objects.requireNonNull(properties, "select properties is null");
            int size = properties.size();
            if (size == 0) {
                throw new ParseException("select properties is empty");
            }
            Map<String, PropertyColumnMapping> defaultMappingMap = mapping.getDefaultMappingMap();
            StringBuilder selectColumnsBuilder = new StringBuilder();

            for (int i = 0; i < size; i++) {
                String property = properties.get(i);
                if (Objects.isNull(defaultMappingMap)) {
                    selectColumnsBuilder.append(property);
                } else {
                    selectColumnsBuilder.append(parseFunction(property, defaultMappingMap));
                }
                if (i < size - 1) {
                    selectColumnsBuilder.append(", ");
                }
            }

            return selectColumnsBuilder.toString();

        }

        private String parseFunction(String property, Map<String, PropertyColumnMapping> defaultMappingMap) {

            StringTool.checkBrackets(property);

            final Matcher matcher = FUNCTION_PATTERN.matcher(property);
            StringBuilder builder = new StringBuilder();

            if (matcher.find()) {
                builder.append(property.split("\\(")[0]).append("(");
                String group = matcher.group(0).replaceFirst("\\(", "");
                group = group.substring(0, group.lastIndexOf(")"));

                final String[] split = group.split(",");
                for (int i = 0; i < split.length; i++) {
                    System.out.println(split[i]);
                    String child = parseFunction(split[i], defaultMappingMap);
                    builder.append(child);
                    if (i < split.length - 1) {
                        builder.append(", ");
                    }
                }
                builder.append(")");
            } else {
                if (StringTool.isNumeric(property)) {
                    builder.append(property);
                } else {
                    final PropertyColumnMapping propertyColumnMapping = defaultMappingMap.get(property);
                    builder.append(Objects.isNull(propertyColumnMapping) ? property : propertyColumnMapping.getColumn());
                }

            }

            return builder.toString();
        }

        private String getMybatisInsertValueColumns(List<String> properties, int repeatCount) {
            Objects.requireNonNull(properties, "insert properties is null");
            int size = properties.size();
            if (size == 0) {
                throw new ParseException("insert properties is empty");
            }
            if (repeatCount < 1) {
                throw new ParseException("repeat count must gte 1");
            }
            StringBuilder values = new StringBuilder();
            for (int i = 0; i < repeatCount; i++) {
                values.append(" ( ");
                for (int j = 0; j < size; j++) {
                    values.append("#{").append(properties.get(j)).append("}");
                    if (j < size - 1) {
                        values.append(", ");
                    }
                }
                values.append(" ) ");
                if (i < repeatCount - 1) {
                    values.append(", ");
                }
            }
            return values.toString();
        }
    }

    public  String sql(String sqlSugar){

        if(StringTool.isNullOrEmpty(sqlSugar)){
            throw new ParseException("sql sugar is null or empty");
        }

        final String[] fragments = sqlSugar.split("_");
        String tableName = mapping.getTableName();

        final MybatisSQL mybatisSQL = new MybatisSQL().tableName(tableName);

        // 转换成大写
        for (int i = 0; i < fragments.length; i++) {
            fragments[i] = StringTool.trimAllWhitespace(fragments[i]).toUpperCase();
        }


        return null;




    }




}

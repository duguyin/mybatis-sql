package com.duguyin.mybatissql.obj;

import com.duguyin.mybatissql.enums.LogicOperator;
import com.duguyin.mybatissql.exceptions.ParseException;
import com.duguyin.mybatissql.tool.StringTool;

import java.nio.MappedByteBuffer;
import java.util.*;

/**
 * @ClassName MybatisDomainSqlPool
 * @Description  domain sql 语句池，专门用来放sql
 * @Author LiuYin
 * @Date 2018/9/27 18:37
 */
public class MybatisDomainSqlPool<T> {

    public MybatisMapping<T> mapping;


    private MybatisDomainSqlPool() {
    }

    public MybatisDomainSqlPool(MybatisMapping<T> mybatisMapping) {
        Objects.requireNonNull(mybatisMapping);
        this.mapping = mybatisMapping;
    }


    private enum OP {
        INSERT("INSERT INTO"),
        DELETE("DELETE"),
        SELECT("SELECT"),
        UPDATE("UPDATE"),
        ;

        String op;

        OP(String op) {
            this.op = op;
        }

        String getOp() {
            return this.op;
        }


    }

    public String sql() {
        final MybatisSQL mybatisSQL = new MybatisSQL();
        mybatisSQL.tableName(mapping.getTableName());
        mybatisSQL.OP(OP.INSERT);
        mybatisSQL.setLogicFragment(new LogicFragment(mapping.getDefaultMappingMap()));

        mybatisSQL.WHERE(new LogicFragment("id").and("age")).AND(new LogicFragment("id").or("age")).OR(new LogicFragment("age").and(new LogicFragment("id").and("age")));
//        return mybatisSQL.logicFragment.toSqlFragment();

//        mybatisSQL.getSql2();
        return mybatisSQL.getSql();
    }


    public class MybatisSQL {

        String tableName;
        OP op;

        List<String> columns = new ArrayList<>();
        int insertRepeatCount = 1;


        List<String> values;
        List<String> countColumns = new ArrayList<>();

        LogicFragment logicFragment;

        int[] limit = new int[2];

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public void setLogicFragment(LogicFragment logicFragment) {
            this.logicFragment = logicFragment;
        }

        public MybatisSQL WHERE(LogicFragment logicFragment) {
            logicFragment.setMappingMap(mapping.getDefaultMappingMap());
//            this.logicFragment.addChild(logicFragment);
            this.logicFragment = logicFragment;

            return this;
        }




        public MybatisSQL AND(LogicFragment logicFragment) {
            this.logicFragment.addChild(logicFragment, LogicOperator.AND);
            return this;
        }

        public MybatisSQL OR(LogicFragment logicFragment) {
            this.logicFragment.addChild(logicFragment, LogicOperator.OR);
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
            Objects.requireNonNull(columns, "columns is null");
            this.columns.addAll(Arrays.asList(columns));
            return this;
        }

        public MybatisSQL LIMIT(int begin, int offset) {
            this.limit[0] = begin;
            this.limit[1] = offset;
            return this;
        }

        public MybatisSQL COUNT(String property) {
            Objects.requireNonNull(property, "count property in null");
            this.countColumns.add(property);
            return this;
        }

        public String getSql() {
            if (StringTool.isNullOrEmpty(tableName)) {
                throw new ParseException("table name is null or empty");
            }
            StringBuilder sql = new StringBuilder();
            sql.append(op.getOp()).append(" ");
            if (OP.INSERT == op) {
                sql.append(tableName);
                columns.add("age");
                columns.add("createTime");
                sql.append(getInsertColumns(columns)).append(" values ").append(getMybatisInsertValueColumns(columns, insertRepeatCount));

            } else if (OP.UPDATE == op) {
                sql.append(tableName).append(" SET ");
                sql.append(getUpdateSqlFragment(columns));
            }

            if(Objects.nonNull(logicFragment)){
                sql.append(" WHERE ");
                sql.append(logicFragment.toSqlFragment());
            }

            return sql.toString();
        }

        public String getSql2(){
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
                final PropertyColumnMapping propertyColumnMapping = mappingMap.get(property);
                String column = Objects.isNull(propertyColumnMapping) ? property : propertyColumnMapping.getColumn();
                builder.append(property).append("=").append(StringTool.withMybatisFormat(column));
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


}

package com.duguyin.mybatissql.obj;

import com.duguyin.mybatissql.enums.LogicOperator;
import com.duguyin.mybatissql.exceptions.ParseException;
import com.duguyin.mybatissql.tool.StringTool;

import java.util.*;

/**
 * @ClassName MybatisSqlPool
 * @Description sql 语句池，专门用来放sql
 * @Author LiuYin
 * @Date 2018/9/27 18:37
 */
public class MybatisSqlPool<T> {

    public  MybatisMapping<T> mapping;


    private MybatisSqlPool(){}

    public MybatisSqlPool(MybatisMapping<T> mybatisMapping){
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

        OP(String op){
            this.op = op;
        }

        String getOp(){
            return this.op;
        }


    }

    public String sql(){
        final MybatisSQL mybatisSQL = new MybatisSQL();
        mybatisSQL.setLogicFragment(new LogicFragment(mapping.getDefaultMappingMap()));

        mybatisSQL.WHERE(new LogicFragment("id").and("age")).OR(new LogicFragment("id").and("age"));
        return mybatisSQL.logicFragment.toSqlFragment();

    }


    public class MybatisSQL{

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

        public MybatisSQL WHERE(LogicFragment logicFragment){
            logicFragment.setMappingMap(mapping.getDefaultMappingMap());
            this.logicFragment.addChild(logicFragment);
            return this;
        }

        public MybatisSQL AND(LogicFragment logicFragment){
            this.logicFragment.addChild(logicFragment, LogicOperator.AND);
            return this;
        }

        public MybatisSQL OR(LogicFragment logicFragment){
            this.logicFragment.addChild(logicFragment, LogicOperator.OR);
            return this;
        }

        public MybatisSQL tableName(String tableName){
            this.tableName = tableName;
            return this;
        }

        public MybatisSQL OP(OP op){
            this.op = op;
            return this;
        }

        public MybatisSQL colums(String... columns){
            Objects.requireNonNull(columns, "columns is null");
            this.columns.addAll(Arrays.asList(columns));
            return this;
        }

        public MybatisSQL LIMIT(int begin, int offset){
            this.limit[0] = begin;
            this.limit[1] = offset;
            return this;
        }

        public MybatisSQL COUNT(String property){
            Objects.requireNonNull(property, "count property in null");
            this.countColumns.add(property);
            return this;
        }

        public String getSql(){
            if(StringTool.isNullOrEmpty(tableName)){
                throw new ParseException("table name is null or empty");
            }
            StringBuilder sql = new StringBuilder();
            sql.append(op.getOp() + " ");
            if(OP.INSERT == op){
                sql.append(tableName);
                sql.append(getInsertColumns(columns)).append(" values ").append(getMybatisInsertValueColumns(columns, insertRepeatCount));
            }
            else if(OP.UPDATE == op){
                sql.append(tableName).append(" SET ");
                sql.append(getUpdateSqlFragment(columns));
            }



            return sql.toString();
        }

        private String getUpdateSqlFragment(List<String> properties) {
            Objects.requireNonNull(properties, "update properties is null");
            int size = properties.size();
            if(size == 0){
                throw  new ParseException("update properties is empty");
            }
            // TODO


            return null;
        }

        private String getInsertColumns(List<String> properties){
            Objects.requireNonNull(properties, "insert properties is null");
            int size = properties.size();
            if(size == 0){
                throw  new ParseException("insert properties is empty");
            }
            Map<String, PropertyColumnMapping> defaultMappingMap = mapping.getDefaultMappingMap();
            StringBuilder insertColumnsBuilder = new StringBuilder();
            insertColumnsBuilder.append(" (");
            for(int i = 0; i< size; i++){
                String property = properties.get(i);
                if(Objects.isNull(defaultMappingMap)){
                    insertColumnsBuilder.append(property);
                }else{
                    PropertyColumnMapping pcm = defaultMappingMap.get(property);
                    insertColumnsBuilder.append(Objects.isNull(pcm) ? property : pcm.getColumn());
                }
                if(i < size -1){
                    insertColumnsBuilder.append(") ");
                }
            }
            return insertColumnsBuilder.toString();
        }


        private String getMybatisInsertValueColumns(List<String> properties, int repeatCount){
            Objects.requireNonNull(properties, "insert properties is null");
            int size = properties.size();
            if(size == 0){
                throw new ParseException("insert properties is empty");
            }
            if(repeatCount < 1){
                throw new ParseException("repeat count must gte 1");
            }
            StringBuilder values = new StringBuilder();
            for(int i = 0; i < repeatCount; i ++){
                values.append(" ( ");
                for(int j = 0; j < size; j ++){
                    values.append("#{").append(properties.get(j)).append("}");
                    if(j < size -1){
                        values.append(", ");
                    }
                }
                values.append(" ) ");
                if(i < repeatCount - 1){
                    values.append(", ");
                }
            }
            return values.toString();
        }

    }




}

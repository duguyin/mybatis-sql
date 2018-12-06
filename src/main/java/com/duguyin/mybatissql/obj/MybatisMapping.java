package com.duguyin.mybatissql.obj;

import com.duguyin.mybatissql.annotations.Column;
import com.duguyin.mybatissql.annotations.Table;
import com.duguyin.mybatissql.exceptions.ParseException;
import org.apache.ibatis.type.JdbcType;

import java.lang.reflect.Field;
import java.util.*;

public class MybatisMapping<T> {

    private Class<T> type;

    private String primaryKeyName;

    private MybatisMapping(){}

    private String tableName;

    /**
     * 本map在解析对应类的时候自动生成，之后不能更改。
     * key: property
     */
    private  Map<String, PropertyColumnMapping> DEFAULT_PROPERTY_MAPPING_MAP = new HashMap<>();

    public static <T> MybatisMapping<T> from(Class<T> type){
        MybatisMapping<T> mybatisMapping = new MybatisMapping<>();
        mybatisMapping.type = type;
        mybatisMapping.parse(type);
        return mybatisMapping;
    }

    private void addDefaultMapping(PropertyColumnMapping mapping){
        Objects.requireNonNull(mapping, "mapping is null");
        if(isNullOrEmpty(mapping.getProperty())){
            throw new RuntimeException("getProperty result is null or empty for PropertyColumnMapping");
        }
        if(isNullOrEmpty(mapping.getColumn())){
            throw new RuntimeException("getColumn result is null or empty for PropertyColumnMapping");
        }

        DEFAULT_PROPERTY_MAPPING_MAP.putIfAbsent(mapping.getProperty(), mapping);
    }

//    public void addDefaultMappings(PropertyColumnMapping... mappings){
//        addDefaultMappings(Arrays.stream(mappings).collect(Collectors.toList()));
//    }
//
//    public void addDefaultMappings(List<PropertyColumnMapping> mappings){
//        mappings.forEach(this::addDefaultMapping);
//    }

    /**
     * 将type中需要的字段解析成映射并保存
     * @param type 需要解析的类
     */
    private void parse(Class<T> type){
        Objects.requireNonNull(type, "type is null");
        String name  = type.getName();

        // 必须有Table注解
        Table tableAnnotation = type.getAnnotation(Table.class);
        Objects.requireNonNull(tableAnnotation, "no annotation named 'Table' on this type: " + name);
        //表名，默认是类的名称
        this.tableName = isNullOrEmpty(tableAnnotation.value()) ?  type.getSimpleName() : tableAnnotation.value();
        //是否开启自动扫描
        boolean autoScan = tableAnnotation.autoScan();

        Field[] fields = type.getDeclaredFields();
        Objects.requireNonNull(fields, "not any field in this type: " + name);

        if(fields.length <= 0){
            throw new ParseException("not any field found in this type: " + name);
        }

        for (Field field : fields) {
            Column columnAnnotation = field.getAnnotation(Column.class);
            if (autoScan || Objects.nonNull(columnAnnotation)) {
                addDefaultMapping(createPCMapping(field, columnAnnotation));
            }
        }
        // 设置为不可变
        DEFAULT_PROPERTY_MAPPING_MAP = Collections.unmodifiableMap(DEFAULT_PROPERTY_MAPPING_MAP);
    }

    /**
     * 根据字段信息及其注解来构造映射
     * @param field 字段信息
     * @param columnAnnotation 注解
     * @return
     */
    private PropertyColumnMapping createPCMapping(Field field, Column columnAnnotation) {

        Objects.requireNonNull(field, "field is null");

        PropertyColumnMapping mapping = new PropertyColumnMapping();
        String fieldName = field.getName();
        mapping.setProperty(fieldName);
        mapping.setJavaType(field.getType());

        if(Objects.nonNull(columnAnnotation)){
            // 设置列明
            mapping.setColumn(columnAnnotation.value());
            // 判断主键并设置
            boolean isPrimaryKey = columnAnnotation.primaryKey();
            mapping.setPrimaryKey(isPrimaryKey);
            if(isPrimaryKey){
                // 主键唯一性判定
                if(Objects.nonNull(primaryKeyName)){
                    throw new ParseException("repeated primary key:, already has " + primaryKeyName +" , but another new named " + fieldName);
                }
                this.primaryKeyName = fieldName;
            }
            // 设置jdbcType
            JdbcType jdbcType = columnAnnotation.jdbcType();
            if(JdbcType.UNDEFINED != jdbcType ){
                mapping.setJdbcType(jdbcType);
            }
        }else{
            // 如果没有注解，列名默认和字段名一直
            mapping.setColumn(fieldName);
        }
        return mapping;
    }

    private  boolean isNullOrEmpty(String s){
        return null == s || s.length() == 0 || s.trim().length() == 0;
    }

    public Class<T> getType() {
        return type;
    }

    public String getPrimaryKeyName() {
        return primaryKeyName;
    }

    public Map<String, PropertyColumnMapping> getDefaultMappingMap() {
        return DEFAULT_PROPERTY_MAPPING_MAP;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}

package com.duguyin.mybatissql.configration;

import com.duguyin.mybatissql.UserTable;
import com.duguyin.mybatissql.tool.MybatisSqlBuilder;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@MapperScan("com.duguyin.mybatissql.mapper")
public class MybatisConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @PostConstruct
    public void initUtil(){
        org.apache.ibatis.session.Configuration configuration = sqlSessionFactory.getConfiguration();
        MybatisSqlBuilder.setConfiguration(configuration);
        System.out.println(MybatisSqlBuilder.getConfiguration());
        org.apache.ibatis.session.Configuration cfg = MybatisSqlBuilder.getConfiguration();
        ResultMapping resultMapping = new ResultMapping.Builder(cfg,"userAction", "user_action", String.class).build();
        List<ResultMapping>  list  = new ArrayList<>();
        list.add(resultMapping);

        ResultMap resultMap = new ResultMap.Builder(cfg,"liuyinrm", UserTable.class, list).build();

        cfg.addResultMap(resultMap);
        System.out.println(cfg);


    }
}

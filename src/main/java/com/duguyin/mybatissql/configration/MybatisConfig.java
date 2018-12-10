package com.duguyin.mybatissql.configration;

import com.duguyin.mybatissql.UserAction;
import com.duguyin.mybatissql.UserTable;
import com.duguyin.mybatissql.tool.MybatisSqlBuilder;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@MapperScan("com.duguyin.mybatissql.mapper")
public class MybatisConfig {


    @Bean
    public MybatisSqlBuilder mybatisSqlBuilder(SqlSessionFactory sqlSessionFactory) {

        org.apache.ibatis.session.Configuration configuration = sqlSessionFactory.getConfiguration();
        MybatisSqlBuilder.from(configuration, UserAction.class);

        System.out.println(configuration);
        return new MybatisSqlBuilder();
    }

}
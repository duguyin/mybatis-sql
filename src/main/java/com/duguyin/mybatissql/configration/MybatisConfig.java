package com.duguyin.mybatissql.configration;

import com.duguyin.mybatissql.tool.MybatisSqlBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.duguyin.mybatissql.mapper")
public class MybatisConfig {


    @Bean
    public MybatisSqlBuilder mybatisSqlBuilder(SqlSessionFactory sqlSessionFactory) {

        org.apache.ibatis.session.Configuration configuration = sqlSessionFactory.getConfiguration();
        MybatisSqlBuilder.from(configuration, UserAction.class, UserTable.class);

        System.out.println(configuration);
        return new MybatisSqlBuilder();
    }

}
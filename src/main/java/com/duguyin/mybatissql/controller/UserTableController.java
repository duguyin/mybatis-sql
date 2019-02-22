package com.duguyin.mybatissql.controller;


import com.duguyin.mybatissql.obj.MybatisDomainSql;
import com.duguyin.mybatissql.obj.MybatisMapping;
import com.duguyin.mybatissql.service.UserTableService;
import com.duguyin.mybatissql.tool.MybatisSqlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/tt")
public class UserTableController {

    @Value("${ttt.ok}")
    private String tttOK;

    @Autowired
    private UserTableService service;

    @GetMapping("/tt")
    public String get(){
        return service.countAll()+"";
    }

    @GetMapping("/user")
    public String user(){
        final MybatisMapping<UserAction> mapping = MybatisMapping.from(UserAction.class);
        final MybatisDomainSql mybatisSql = new MybatisDomainSql<>(mapping);

//        System.out.println(mybatisSql.baseInsertSql(false));
//        System.out.println(mybatisSql.baseInsertSql(true));
//        System.out.println(mybatisSql.baseInsertSql());
//        System.out.println(mybatisSql.baseUpdateSql("age"));
//        System.out.println(mybatisSql.baseUpdateSqlByPrimaryKey());
//        System.out.println(mybatisSql.baseDeleteSql("age"));
//        System.out.println(mybatisSql.baseDeleteSqlByPrimaryKey());
        System.out.println(mybatisSql.baseCountSql("id"));
        System.out.println(mybatisSql.baseSelectSql("id"));
        System.out.println(mybatisSql.baseSelectSqlByLimit("pageSize"));
        System.out.println(MybatisSqlBuilder.useDomain(UserAction.class).baseSelectSql("age").cache());
        return "ok";


    }

    @GetMapping("/p")
    public String p(){
        Pattern FUCTION_PATTERN = Pattern.compile("\\(.*\\)");
        String s  = "df(adff,234,34,count(23,45))";
        final Matcher matcher = FUCTION_PATTERN.matcher(s);
        boolean find = matcher.find();
        if(find){
            System.out.println(matcher.group());
            System.out.println(matcher.group(0));
//            System.out.println(matcher.group(1));

        }

        return String.valueOf(find);
    }

}

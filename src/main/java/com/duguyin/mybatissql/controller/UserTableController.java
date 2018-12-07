package com.duguyin.mybatissql.controller;


import com.duguyin.mybatissql.UserAction;
import com.duguyin.mybatissql.obj.MybatisDomainSqlPool;
import com.duguyin.mybatissql.obj.MybatisMapping;
import com.duguyin.mybatissql.service.UserTableService;
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
        final MybatisDomainSqlPool mybatisDomainSqlPool = new MybatisDomainSqlPool<>(mapping);

        return mybatisDomainSqlPool.deleteSql();


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

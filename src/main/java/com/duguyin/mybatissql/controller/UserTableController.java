package com.duguyin.mybatissql.controller;


import com.duguyin.mybatissql.UserAction;
import com.duguyin.mybatissql.obj.MybatisMapping;
import com.duguyin.mybatissql.obj.MybatisSqlPool;
import com.duguyin.mybatissql.service.UserTableService;
import com.duguyin.mybatissql.service.impl.UserTableServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        final MybatisSqlPool mybatisSqlPool = new MybatisSqlPool<>(mapping);
        System.out.println(mybatisSqlPool.sql());
        return mybatisSqlPool.sql();


    }

}

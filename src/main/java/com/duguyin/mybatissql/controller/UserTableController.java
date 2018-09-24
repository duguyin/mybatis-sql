package com.duguyin.mybatissql.controller;


import com.duguyin.mybatissql.service.UserTableService;
import com.duguyin.mybatissql.service.impl.UserTableServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tt")
public class UserTableController {

    @Autowired
    private UserTableService service;

    @GetMapping("/tt")
    public String get(){
        return service.countAll()+"";
    }

}

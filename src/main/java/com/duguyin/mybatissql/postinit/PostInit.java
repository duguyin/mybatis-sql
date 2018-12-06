package com.duguyin.mybatissql.postinit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.StandardServletEnvironment;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;

//@Component
public class PostInit implements ApplicationRunner {

    @Autowired
    private ConfigurableEnvironment configurableEnvironment;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        Class<? extends ConfigurableEnvironment> aClass = configurableEnvironment.getClass();
        Class<?> superclass = aClass.getSuperclass();
        Field[] declaredFields = superclass.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            System.out.println(declaredFields[i].getName());
        }



        System.out.println(configurableEnvironment);
        Map<String, Object> systemProperties = configurableEnvironment.getSystemProperties();
        Map<String, Object> systemEnvironment = configurableEnvironment.getSystemEnvironment();
        MutablePropertySources propertySources = configurableEnvironment.getPropertySources();
        Iterator<PropertySource<?>> iterator = propertySources.iterator();
        while (iterator.hasNext()){
            PropertySource<?> next = iterator.next();
            Object source = next.getSource();
            String name = source.getClass().getName();
            System.out.println(name);
        }

        StandardServletEnvironment standardServletEnvironment = (StandardServletEnvironment) configurableEnvironment;
        System.out.println(standardServletEnvironment);

    }
}

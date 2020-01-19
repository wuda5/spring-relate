package com.luban.ann;


import com.luban.spring.LubanRegistart;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/***模拟mybatis的 mapperScan 注解的作用*/
@Retention(RetentionPolicy.RUNTIME)
@Import(LubanRegistart.class)
public @interface MineMapperScan {

    String value()default "com.luban.dao";


}

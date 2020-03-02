package com.luban.aspects.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)// 默认是.class是运行时无法获得所以的改
@Target(ElementType.METHOD)//不指定标签的作用范围，那么标签适用于所有范围。
public @interface TimeAccord {
}

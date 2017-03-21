package com.example.test.testtask.Tools;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(value= ElementType.TYPE)
@Retention(value= RetentionPolicy.RUNTIME)
public @interface BaseUrl {
    /**
     * Базовый URL Для сервисе
     */
    String value() default "";

    /**
     * Параметры, добавляемые к каждому запросу сервиса
     */
    String[] params() default {};
}

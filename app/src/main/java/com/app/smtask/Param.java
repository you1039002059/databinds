package com.app.smtask;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhuangsj
 * @create 2019/5/13
 * @desc
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Param {
    int id() default -1;

    Class<? extends BaseViewModel> clazz() default BaseViewModel.class;

    int variableId() default 0;

}
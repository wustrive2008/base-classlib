package org.wustrive.java.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * Description: 
 * 
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/5/18 10:49
 * @Copyright: 2017-2018 dgztc Inc. All rights reserved.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Token {

    boolean save() default false;

    boolean remove() default false;
}
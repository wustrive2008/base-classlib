package org.wustrive.java.core.reqlimit.annotation;

import java.lang.annotation.*;

/**
 * Description: 基于AOP实现IP限流
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2017/11/4 12:07
 * @Copyright: 2017-2018 dgztc Inc. All rights reserved.
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestLimit {

    /**
     * 允许访问的次数，默认值MAX_VALUE
     */
    int count() default 60;

    /**
     * 时间段，单位为秒，默认值一分钟
     */
    int time() default 60;
}

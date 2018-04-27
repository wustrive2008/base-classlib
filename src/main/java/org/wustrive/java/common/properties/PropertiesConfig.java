package org.wustrive.java.common.properties;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

/**
 *
 * Description: properties文件读取
 *
 * @author: wubaoguo
 * @email: wustrive2008@gmail.com
 * @date: 2018/2/23 11:54
 * @Copyright: 2017-2018 dgztc Inc. All rights reserved.
 */
public class PropertiesConfig {
    private static final Logger log = LoggerFactory.getLogger(PropertiesConfig.class);
    private static Properties properties;

    /**
     * 获取Properties
     *
     * @return
     * @Title: getProperties
     */
    public static Properties getProperties() {
        // 生成输入流
        InputStream ins = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties");
        // 生成properties对象
        properties = new Properties();
        try {
            properties.load(ins);
        } catch (Exception e) {
            log.info("ExceptionContent:::", e);
        } finally {
            IOUtils.closeQuietly(ins);
        }
        return properties;
    }

    public static String getProperty(String key) {
        if (properties == null) {
            properties = getProperties();
        }
        return properties.getProperty(key);
    }
}

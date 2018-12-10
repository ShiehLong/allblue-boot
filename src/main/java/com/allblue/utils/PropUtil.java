package com.allblue.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Description:从配置文件内读取配置信息
 * @Author Xone
 * @Date 9:58 2018/10/11
 **/
public class PropUtil {
    private Logger logger = LoggerFactory.getLogger(PropUtil.class);
    private static Properties properties = null;

    public PropUtil(String path) {
        this.getProperties(path);
    }

    public void getProperties(String path) {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(path);
        if (is == null) {
            logger.error("文件为空！");
            return;
        }
        properties = new Properties();
        String fileType = path.substring(path.lastIndexOf(".") + 1).toLowerCase();
        try {
            if ("xml".equals(fileType)) {
                properties.loadFromXML(is);
            } else {
                properties.load(is);
            }
        } catch (IOException e) {
            logger.error("加载文件异常" + e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                logger.error("关闭文件异常" + e);
            }
        }
    }

    /**
     * @Description:根据key从文件取值
     * @Author Xone
     * @Date 10:36 2018/10/11
     **/
    public String get(String key) {
        String keyValue = null;
        if (properties.containsKey(key)) {
            keyValue = properties.getProperty(key);
        }
        return keyValue;
    }

}

package com.ubuntuvim.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.junit.Assert.*;

public class PropertiesUtil {
	
	private static String propertyFilePath = "/config.properties";
	
	//根据Key读取Value
    public static String GetValueByKey(String key) {
        Properties pps = new Properties();
        try {
            //InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertyFilePath);  
        	InputStream in = PropertiesUtil.class.getClass().getResourceAsStream(propertyFilePath);  
            assertNotNull("加载数据库配置文件错误。", in);
            pps.load(in);
            return pps.getProperty(key);
            
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

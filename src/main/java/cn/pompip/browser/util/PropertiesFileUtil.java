package cn.pompip.browser.util;

import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

public class PropertiesFileUtil {
    private static Properties properties;

    static {
        try {
            properties = PropertiesLoaderUtils.loadAllProperties("configs.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String getValue(String name) {
        return properties.getProperty(name);
    }


}

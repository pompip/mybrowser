package cn.pompip.browser.util;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;

public class PropertiesFileUtil {

    public static String getValue(String name) {
        try {
            return PropertiesLoaderUtils.loadAllProperties("configs.properties").getProperty(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return name;
    }


}

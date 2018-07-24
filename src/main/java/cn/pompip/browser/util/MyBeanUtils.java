package cn.pompip.browser.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

public class MyBeanUtils {
    public static <M> void merge(M target, M destination) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(target.getClass());

        for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {

            if (descriptor.getWriteMethod() != null) {
                Object originalValue = descriptor.getReadMethod()
                        .invoke(target);

                if (originalValue == null) {
                    Object defaultValue = descriptor.getReadMethod().invoke(
                            destination);
                    descriptor.getWriteMethod().invoke(target, defaultValue);
                }

            }
        }
    }

    public static void mergeNotNullProperty(Object source ,Object target){
       BeanUtils.copyProperties(source,target,findNotNullPropertyName(source));
    }

    public static String[] findNotNullPropertyName(Object source) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor p : propertyDescriptors) {
            String name = p.getName();
            if ("class".equals(name)) continue;
            Object propertyValue = beanWrapper.getPropertyValue(name);
            if (propertyValue == null) {
                emptyNames.add(p.getName());
            }
        }
        return emptyNames.toArray(new String[0]);
    }
}

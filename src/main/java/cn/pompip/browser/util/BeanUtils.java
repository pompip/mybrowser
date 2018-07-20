package cn.pompip.browser.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

public class BeanUtils {
    public static <M> void merge(M target, M destination) throws Exception{
        BeanInfo beanInfo = Introspector.getBeanInfo(target.getClass());

        // Iterate over all the attributes
        for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {

            // Only copy writable attributes
            if (descriptor.getWriteMethod() != null) {
                Object originalValue = descriptor.getReadMethod()
                        .invoke(target);

                // Only copy values values where the destination values is null
                if (originalValue == null) {
                    Object defaultValue = descriptor.getReadMethod().invoke(
                            destination);
                    descriptor.getWriteMethod().invoke(target, defaultValue);
                }

            }
        }
    }
}

/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package infos;

import java.awt.Component;
import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

public class ComponentBeanInfo extends SimpleBeanInfo {
    public BeanDescriptor getBeanDescriptor() {
        BeanDescriptor bd = new BeanDescriptor(Component.class);
        // set a value to ensure that it's unique
        bd.setValue("test", Boolean.TRUE);
        return bd;
    }

    // Only an array of 1 Property Descriptor
    public PropertyDescriptor[] getPropertyDescriptors() {
        try {
            return new PropertyDescriptor[] {
                    new PropertyDescriptor("name", Component.class)
            };
        } catch (IntrospectionException exception) {
            throw new Error("unexpected exception", exception);
        }
    }
}

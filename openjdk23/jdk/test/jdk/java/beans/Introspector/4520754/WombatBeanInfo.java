/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

import java.beans.BeanDescriptor;
import java.beans.SimpleBeanInfo;

public class WombatBeanInfo extends SimpleBeanInfo {
    public BeanDescriptor getBeanDescriptor() {
        BeanDescriptor bd = new BeanDescriptor(Wombat.class);
        // set a value to ensure that it's unique
        bd.setValue("test", Boolean.TRUE);
        return bd;
    }
}

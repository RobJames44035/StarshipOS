/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

/**
 * @test
 * @bug 8132566 8131939
 * @summary Check if the derived class inherits the property info
 *          from the base class.
 * @author a.stepanov
 */

public class InheritPropertyInfoTest {

    public static class C extends CBase {}

    public static void main(String[] args) throws Exception {

        BeanInfo i = Introspector.getBeanInfo(C.class, Object.class);
        PropertyDescriptor[] pds = i.getPropertyDescriptors();

        Checker.checkEq("number of properties", pds.length, 1);
        PropertyDescriptor p = pds[0];

        Checker.checkEq("property description", p.getShortDescription(), "BASE");

        Checker.checkEq("isBound",  p.isBound(),  true);
        Checker.checkEq("isExpert", p.isExpert(), true);
        Checker.checkEq("isHidden", p.isHidden(), true);
        Checker.checkEq("isPreferred", p.isPreferred(), true);
        Checker.checkEq("required", p.getValue("required"), true);
        Checker.checkEq("visualUpdate", p.getValue("visualUpdate"), true);

        Checker.checkEnumEq("enumerationValues", p.getValue("enumerationValues"),
            new Object[]{"TOP", 1, "javax.swing.SwingConstants.TOP"});
    }
}

/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

package com.foo;

import java.util.ResourceBundle;
import java.util.spi.ResourceBundleControlProvider;

public class UserControlProvider implements ResourceBundleControlProvider {
    static final ResourceBundle.Control XMLCONTROL = new UserXMLControl();

    public ResourceBundle.Control getControl(String baseName) {
        System.out.println(getClass().getName()+".getControl called for " + baseName);

        // Throws a NPE if baseName is null.
        if (baseName.startsWith("com.foo.Xml")) {
            System.out.println("\treturns " + XMLCONTROL);
            return XMLCONTROL;
        }
        System.out.println("\treturns null");
        return null;
    }
}

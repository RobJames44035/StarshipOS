/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Main {
    static final String MAIN_BUNDLES_RESOURCE = "jdk.test.resources.MyResources";
    public static void main(String[] args) {
        if (args.length == 1 && args[0].equals("cache")) {
            // first load resource bundle in the cache
            jdk.test.util.Bundles.getBundle();

            // fail to load resource bundle that is present in the cache
            try {
                Module mainbundles = jdk.test.util.Bundles.class.getModule();
                ResourceBundle rb = ResourceBundle.getBundle(MAIN_BUNDLES_RESOURCE, mainbundles);
                throw new RuntimeException("ERROR: test module loads " + rb);
            } catch (MissingResourceException e) {
                System.out.println("Expected: " + e.getMessage());
            }
        } else {
            // fail to load resource bundle; NON_EXISTENT_BUNDLE in the cache
            try {
                Module mainbundles = jdk.test.util.Bundles.class.getModule();
                ResourceBundle rb = ResourceBundle.getBundle(MAIN_BUNDLES_RESOURCE, mainbundles);
                throw new RuntimeException("ERROR: test module loads " + rb);
            } catch (MissingResourceException e) {
                System.out.println("Expected: " + e.getMessage());
            }

            // successfully load the resource bundle
            jdk.test.util.Bundles.getBundle();
        }
    }
}

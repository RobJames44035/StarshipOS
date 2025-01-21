/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class TestWithUnnamedModuleArg {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("Usage: java ... basename should-be-loaded-flag");
            System.out.println("  ex. java ... jdk.test.resources.classes.MyResources false");
            return;
        }

        String basename = args[0];
        boolean shouldBeLoaded = "true".equals(args[1]);

        int errors = 0;

        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        if (cl == null) {
            cl = ClassLoader.getSystemClassLoader();
        }

        try {
            // Set the default Locale to Locale.ROOT to avoid any confusions related to fallback
            Locale.setDefault(Locale.ROOT);
            ResourceBundle rb = ResourceBundle.getBundle(basename,
                                                         cl.getUnnamedModule());
            if (shouldBeLoaded) {
                System.out.println("Passed: got resource bundle:");
            } else {
                System.out.println("Failed: no MissingResourceException thrown");
                errors++;
            }
            System.out.println("            bundle = " + rb);
        } catch (MissingResourceException e) {
            if (!shouldBeLoaded) {
                System.out.println("Passed: got expected " + e);
            } else {
                System.out.println("Failed: got unexpected " + e);
                errors++;
            }
            System.out.println("            cause = " + e.getCause());
        } catch (Throwable t) {
            System.out.println("Failed: unexpected throwable: " + t);
            errors++;
        }

        if (errors > 0) {
            throw new RuntimeException(errors + " errors");
        }
    }
}

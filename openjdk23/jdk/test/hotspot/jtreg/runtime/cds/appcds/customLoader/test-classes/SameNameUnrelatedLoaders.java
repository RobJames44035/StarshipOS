/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import jdk.test.whitebox.WhiteBox;

public class SameNameUnrelatedLoaders {
    public static void main(String args[]) throws Exception {
        String path = args[0];
        String testCase = args[1];
        URL url = new File(path).toURI().toURL();
        URL[] urls = new URL[] {url};

        ClassLoader appLoader = SameNameUnrelatedLoaders.class.getClassLoader();
        URLClassLoader ldr01 = null;
        URLClassLoader ldr02 = null;

        switch (testCase) {
            case "FpBoth":
                ldr01 = new URLClassLoader(urls);
                ldr02 = new URLClassLoader(urls);
            break;

            default:
                throw new IllegalArgumentException("Invalid testCase ID");
        }


        Class class01 = ldr01.loadClass("CustomLoadee");
        Class class02  = ldr02.loadClass("CustomLoadee");

        System.out.println("class01 = " + class01);
        System.out.println("class02 = " + class02);

        if (class01.getClassLoader() != ldr01) {
            throw new RuntimeException("class01 loaded by wrong loader");
        }
        if (class02.getClassLoader() != ldr02) {
            throw new RuntimeException("class02 loaded by wrong loader");
        }

        if (true) {
            if (class01.isAssignableFrom(class02)) {
                throw new RuntimeException("assignable condition failed");
            }

            Object obj01 = class01.newInstance();
            Object obj02 = class02.newInstance();

            if (class01.isInstance(obj02)) {
                throw new RuntimeException("instance relationship condition 01 failed");
            }
            if (class02.isInstance(obj01)) {
                throw new RuntimeException("instance relationship condition 02 failed");
            }
        }

        WhiteBox wb = WhiteBox.getWhiteBox();
        if (wb.isSharedClass(SameNameUnrelatedLoaders.class)) {
            boolean class1Shared = wb.isSharedClass(class01);
            boolean class2Shared = wb.isSharedClass(class02);

            if (testCase.equals("FpBoth")) {
                if (!class1Shared) {
                    throw new RuntimeException("first class is not shared");
                }

                if (class2Shared) {
                    throw new RuntimeException("second class is shared, " +
                    "and it should not be - first come first serve violation");
                }
            } else {
                if (! (class1Shared && class2Shared) )
                    throw new RuntimeException("both classes expected to be shared, but are not");
            }
        }
    }
}

/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

import jdk.test.whitebox.WhiteBox;

/**
 * This is a generic test app for testing if classes are loaded from the CDS archive
 * or not (without having to parse -Xlog:class+load, or writing your own WhiteBox apps).
 * Usage:
 *     [1] Create an archive with WhiteBox enabled.
 *     [2] Run this app with arguments such as
 *             "assertShared:java.lang.Object"
 *             "assertNotShared:NotSharedClassName"
 *
 * We can probably add other kinds of simple tests as well ....
 *
 * FIXME: enhance WB API to check if a particular archive has failed. So you can say
 *        assertShared:0,java.lang.Object
 * to assert that java.lang.Object is shared from archive #0 (i.e., base archive).
 */
public class GenericTestApp {
    private static final WhiteBox wb = WhiteBox.getWhiteBox();

    public static void main(String args[]) throws Exception {
        System.out.println("GenericTestApp started. WhiteBox = " + wb);
        System.out.println("cdsMemoryMappingFailed() = " + cdsMemoryMappingFailed());

        for (String s : args) {
            Class c;
            if ((c = getClass(s, "assertShared:")) != null) {
                assertShared(c);
            }
            else if ((c = getClass(s, "assertNotShared:")) != null) {
                assertNotShared(c);
            }
            else {
                throw new RuntimeException("Unknown option: " + s);
            }
            System.out.println("passed: " + s);
        }
    }

    private static Class getClass(String s, String prefix) throws Exception {
        if (s.startsWith(prefix)) {
            return Class.forName(s.substring(prefix.length()));
        } else {
            return null;
        }
    }

    private static boolean cdsMemoryMappingFailed() {
        return wb.cdsMemoryMappingFailed();
    }

    private static void assertShared(Class klass) {
        if (!cdsMemoryMappingFailed()) {
            if (!wb.isSharedClass(klass)) {
                throw new RuntimeException("Class should be shared but is not: " + klass);
            }
        } else {
            // FIXME -- need to throw jtreg.SkippedException
            System.out.println("Cannot test for wb.isSharedClass(" + klass + ") because CDS mapping has failed");
        }
    }

    private static void assertNotShared(Class klass) {
        if (wb.isSharedClass(klass)) {
            throw new RuntimeException("Class should be shared but is not: " + klass);
        }
    }
}

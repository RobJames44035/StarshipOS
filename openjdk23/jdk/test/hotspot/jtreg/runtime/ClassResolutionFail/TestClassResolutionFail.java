/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test TestClassResolutionFail
 * @bug 8023697
 * @summary This tests that failed class resolution doesn't report different class name in detail message for the first and subsequent times
 */

import java.io.File;

public class TestClassResolutionFail {
    static String message;
    public static void test1() throws RuntimeException {
        try {
            Property p = new Property();
        } catch (LinkageError e) {
            message = e.getMessage();
        }
        try {
            Property p = new Property();
        } catch (LinkageError e) {
            System.out.println(e.getMessage());
            if (!e.getMessage().equals(message)) {
                throw new RuntimeException("Wrong message: " + message + " != " + e.getMessage());
            }
        }
    }
    public static void main(java.lang.String[] unused) throws Exception {
        // Remove PropertySuper class
        String testClasses = System.getProperty("test.classes", ".");
        File f = new File(testClasses + File.separator + "PropertySuper.class");
        f.delete();
        test1();
    }
}


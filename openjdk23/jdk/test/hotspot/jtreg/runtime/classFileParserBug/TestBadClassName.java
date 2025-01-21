/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8158297 8218939
 * @summary Constant pool utf8 entry for class name cannot have empty qualified name '//'
 * @compile p1/BadInterface1.jcod
 * @compile p1/BadInterface2.jcod
 * @compile UseBadInterface1.jcod
 * @compile UseBadInterface2.jcod
 * @run main/othervm -Xverify:all TestBadClassName
 */

public class TestBadClassName {
    public static void main(String args[]) throws Throwable {

        System.out.println("Regression test for bug 8042660");

        // Test class name with p1//BadInterface1
        String expected = "Illegal class name \"p1//BadInterface1\" in class file UseBadInterface1";
        try {
            Class newClass = Class.forName("UseBadInterface1");
            throw new RuntimeException("Expected ClassFormatError exception not thrown");
        } catch (java.lang.ClassFormatError e) {
            check(e, expected);
            System.out.println("Test UseBadInterface1 passed test case with illegal class name");
        }

        // Test class name with p1/BadInterface2/
        expected = "Illegal class name \"p1/BadInterface2/\" in class file UseBadInterface2";
        try {
            Class newClass = Class.forName("UseBadInterface2");
            throw new RuntimeException("Expected ClassFormatError exception not thrown");
        } catch (java.lang.ClassFormatError e) {
            check(e, expected);
            System.out.println("Test UseBadInterface2 passed test case with illegal class name");
        }
    }

    static void check(ClassFormatError c, String expected) {
        if (!c.getMessage().equals(expected)) {
            throw new RuntimeException("Wrong ClassFormatError - expected: \"" +
                                       expected + "\", got \"" +
                                       c.getMessage() + "\"");
        }
    }
}

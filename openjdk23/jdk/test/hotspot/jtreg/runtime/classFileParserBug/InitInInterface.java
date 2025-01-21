/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8139069
 * @summary Check that any method named <init> in an interface causes ClassFormatError
 * @compile nonvoidinit.jasm voidinit.jasm
 * @run main InitInInterface
 */

// Test that an <init> method is not allowed in interfaces.
public class InitInInterface {
    public static void main(String args[]) throws Throwable {

        System.out.println("Regression test for bug 8130183");
        try {
            Class newClass = Class.forName("nonvoidinit");
            throw new RuntimeException(
                 "ClassFormatError not thrown for non-void <init> in an interface");
        } catch (java.lang.ClassFormatError e) {
            if (!e.getMessage().contains("Interface cannot have a method named <init>")) {
                throw new RuntimeException("Unexpected exception nonvoidint: " + e.getMessage());
            }
        }
        try {
            Class newClass = Class.forName("voidinit");
            throw new RuntimeException(
                 "ClassFormatError not thrown for void <init> in an interface");
        } catch (java.lang.ClassFormatError e) {
            if (!e.getMessage().contains("Interface cannot have a method named <init>")) {
                throw new RuntimeException("Unexpected exception voidint: " + e.getMessage());
            }
        }
    }
}

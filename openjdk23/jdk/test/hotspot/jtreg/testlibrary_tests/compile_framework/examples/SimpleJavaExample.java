/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @summary Example test to use the Compile Framework.
 * @modules java.base/jdk.internal.misc
 * @library /test/lib /
 * @run driver compile_framework.examples.SimpleJavaExample
 */

package compile_framework.examples;

import compiler.lib.compile_framework.*;

/**
 * This test shows a simple compilation of java source code, and its invocation.
 */
public class SimpleJavaExample {

    // Generate a source java file as String
    public static String generate() {
        return """
               public class XYZ {
                   public static int test(int i) {
                       System.out.println("Hello from XYZ.test: " + i);
                       return i * 2;
                   }
               }
               """;
    }

    public static void main(String[] args) {
        // Create a new CompileFramework instance.
        CompileFramework comp = new CompileFramework();

        // Add a java source file.
        comp.addJavaSourceCode("XYZ", generate());

        // Compile the source file.
        comp.compile();

        // Object ret = XYZ.test(5);
        Object ret = comp.invoke("XYZ", "test", new Object[] {5});

        // Extract return value of invocation, verify its value.
        int i = (int)ret;
        System.out.println("Result of call: " + i);
        if (i != 10) {
            throw new RuntimeException("wrong value: " + i);
        }
    }
}

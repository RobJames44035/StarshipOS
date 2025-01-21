/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package compiler.jvmci.common.testcases;

import java.util.HashMap;
import java.util.Map;

public interface MultipleImplementersInterface {

    int INT_CONSTANT = Integer.MAX_VALUE;
    long LONG_CONSTANT = Long.MAX_VALUE;
    float FLOAT_CONSTANT = Float.MAX_VALUE;
    double DOUBLE_CONSTANT = Double.MAX_VALUE;
    String STRING_CONSTANT = "Hello";
    Object OBJECT_CONSTANT = new Object();

    default void defaultMethod() {
        // empty
    }

    void testMethod();

    @SuppressWarnings("removal")
    default void finalize() throws Throwable {
        // empty
    }

    default void lambdaUsingMethod() {
        Thread t = new Thread(this::defaultMethod);
        t.start();
    }

    default void printFields() {
        System.out.println(OBJECT_CONSTANT);
        String s = "";
        System.out.println(s);
    }

    static void staticMethod() {
        System.getProperties(); // calling some static method
        Map map = new HashMap(); // calling some constructor
        map.put(OBJECT_CONSTANT, OBJECT_CONSTANT); // calling some interface method
        map.remove(OBJECT_CONSTANT); // calling some default interface method
    }

    default void instanceMethod() {
        toString(); // calling some virtual method
    }

    default void anonClassMethod() {
        new Runnable() {
            @Override
            public void run() {
                System.out.println("Running");
            }
        }.run();
    }
}

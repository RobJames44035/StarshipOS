/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package jdk.test;

public class Test {
    public static void main(String[] args) {
        System.out.println(Test.class + " ...");
        for (String arg: args) {
            System.out.println(arg);
        }

        ClassLoader scl = ClassLoader.getSystemClassLoader();
        ClassLoader cl1 = Test.class.getClassLoader();
        Module testModule = Test.class.getModule();
        ClassLoader cl2 = ModuleLayer.boot().findLoader(testModule.getName());

        if (cl1 != scl)
            throw new RuntimeException("Not loaded by system class loader");
        if (cl2 != scl)
            throw new RuntimeException("Not associated with system class loader");

    }
}

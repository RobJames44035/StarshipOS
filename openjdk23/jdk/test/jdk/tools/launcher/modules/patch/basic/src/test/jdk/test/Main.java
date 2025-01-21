/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/**
 * Used with --patch-module to exercise the replacement or addition of classes
 * in modules that are linked into the runtime image.
 */

package jdk.test;

public class Main {

    public static void main(String[] args) throws Exception {

        for (String moduleAndClass : args[0].split(",")) {
            String mn = moduleAndClass.split("/")[0];
            String cn = moduleAndClass.split("/")[1];

            // load class
            Class<?> c = Class.forName(cn);

            // check in expected module
            Module m = c.getModule();
            assertEquals(m.getName(), mn);

            // instantiate object
            Main.class.getModule().addReads(m);
            Object obj = c.newInstance();

            // check that the expected version of the class is loaded
            System.out.print(moduleAndClass);
            String s = obj.toString();
            System.out.println(" says " + s);
            assertEquals(s, "hi");

            // check Module getResourceAsStream
            String rn = cn.replace('.', '/') + ".class";
            assertNotNull(m.getResourceAsStream(rn));
        }
    }


    static void assertEquals(Object o1, Object o2) {
        if (!o1.equals(o2))
            throw new RuntimeException("assertion failed");
    }

    static void assertNotNull(Object o) {
        if (o == null)
            throw new RuntimeException("unexpected null");
    }
}

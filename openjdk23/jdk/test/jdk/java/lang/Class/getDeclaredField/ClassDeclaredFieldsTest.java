/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

import java.lang.reflect.Field;

/**
 * @test
 * @bug 8065552
 * @summary test that all fields returned by getDeclaredFields() can be
 *          set accessible if package java.lang is open to unnamed module;
 *          this test also verifies that Class.classLoader final private field
 *          is hidden from reflection access.
 * @modules java.base/java.lang:open
 * @run main/othervm ClassDeclaredFieldsTest
 *
 * @author danielfuchs
 */
public class ClassDeclaredFieldsTest {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        System.out.println(System.getProperty("java.version"));
        for (Field f : Class.class.getDeclaredFields()) {
            f.setAccessible(true);
            System.out.println("Field "+f.getName()+" is now accessible.");
            if (f.getName().equals("classLoader")) {
                throw new RuntimeException("Found "+f.getName()+" field!");
            }
        }
        try {
            Class.class.getDeclaredField("classLoader");
            throw new RuntimeException("Expected NoSuchFieldException for"
                    + " 'classLoader' field not raised");
        } catch(NoSuchFieldException x) {
            System.out.println("Got expected exception: " + x);
        }
        System.out.println("Passed");
    }
}

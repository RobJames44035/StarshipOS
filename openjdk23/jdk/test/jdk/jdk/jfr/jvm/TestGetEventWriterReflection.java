/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package jdk.jfr.jvm;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;
import jdk.jfr.Event;
import jdk.jfr.Registered;
/**
 * @test Tests that reflective access works as (normally) expected
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 *
 * @run main/othervm jdk.jfr.jvm.TestGetEventWriterReflection
 */
public class TestGetEventWriterReflection {

    @Registered(false)
    static class InitializeEvent extends Event {
    }

    public static void main(String... args) throws Throwable {
        testReflectionGetConstructor();
        testReflectionGetDeclaredConstructor();
        testReflectionGetDeclaredConstructorSetAccessible();
        testReflectionGetDeclaredFieldSetAccessible();
    }

    // getConstructor() only return public members.
    private static void testReflectionGetConstructor() throws Exception {
        try {
            Class<?> c = Class.forName("jdk.jfr.internal.event.EventWriter");
            Constructor<?> constructor = c.getConstructor(new Class[0]);
            throw new RuntimeException("Should not reach here " + constructor);
        } catch (NoSuchMethodException nsme) {
            // OK, as expected. The constructor is private.
        }
    }

    // getDeclaredConstructor() return also a private constructor.
    // Invoking a private constructor is an instance of IllegalAccess.
    private static void testReflectionGetDeclaredConstructor() throws Exception {
        try {
            Class<?> c = Class.forName("jdk.jfr.internal.event.EventWriter");
            Constructor<?> constructor = c.getDeclaredConstructor(new Class[0]);
            constructor.newInstance();
            throw new RuntimeException("Should not reach here " + constructor);
        } catch (IllegalAccessException iae) {
            if (iae.getMessage().contains("""
                cannot access a member of class jdk.jfr.internal.event.EventWriter
                (in module jdk.jfr) with modifiers \"private\"
                                         """)) {
                // OK, as expected. Private protection in effect.
            }
        }
    }

    // getDeclaredConstructor() return also a private constructor.
    // setAccessible(true) attempts to make the private constructor public for external access.
    // With JEP 403: Strongly Encapsulate JDK Internals, the module and package must first
    // be explicitly opened for setAccessible(true) to succeed.
    private static void testReflectionGetDeclaredConstructorSetAccessible() throws Exception {
        try {
            Class<?> c = Class.forName("jdk.jfr.internal.event.EventWriter");
            Constructor<?> constructor = c.getDeclaredConstructor(new Class[0]);
            constructor.setAccessible(true);
            throw new RuntimeException("Should not reach here " + constructor);
        } catch (InaccessibleObjectException ioe) {
            if (ioe.getMessage().contains("module jdk.jfr does not \"opens jdk.jfr.internal.event")) {
                // OK, as expected. Even when using setAccessible(true), by default, the jdk.jfr module
                // is not open for reflective access to private members.
            }
        }
    }

    // getDeclaredField() return also a private field.
    // setAccessible(true) attempts to make the private field public for external access.
    // With JEP 403: Strongly Encapsulate JDK Internals, the module and package must first
    // be explicitly opened for setAccessible(true) to succeed.
    private static void testReflectionGetDeclaredFieldSetAccessible() throws Exception {
        try {
            Class<?> c = Class.forName("jdk.jfr.internal.event.EventWriter");
            Field field = c.getDeclaredField("unsafe");
            field.setAccessible(true);
            throw new RuntimeException("Should not reach here " + field);
        } catch (InaccessibleObjectException ioe) {
            if (ioe.getMessage().contains("module jdk.jfr does not \"opens jdk.jfr.internal.event")) {
                // OK, as expected. Even when using setAccessible(true), by default, the jdk.jfr module
                // is not open for reflective access to private members.
            }
        }
    }
}

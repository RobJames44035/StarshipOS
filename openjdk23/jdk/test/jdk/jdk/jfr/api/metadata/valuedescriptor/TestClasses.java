/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.api.metadata.valuedescriptor;

import java.util.HashMap;
import java.util.Map;

import jdk.jfr.ValueDescriptor;
import jdk.test.lib.Asserts;
import jdk.test.lib.jfr.CommonHelper;
import jdk.test.lib.jfr.VoidFunction;

/**
 * @test
 * @summary Test ValueDescriptor.getAnnotations()
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.metadata.valuedescriptor.TestClasses
 */
public class TestClasses {

    public static void main(String[] args) throws Throwable {
        @SuppressWarnings("rawtypes")
        Map<String, Class> valid = new HashMap<>();
        valid.put("byte", byte.class);
        valid.put("short", short.class);
        valid.put("int", int.class);
        valid.put("char", char.class);
        valid.put("float", float.class);
        valid.put("double", double.class);
        valid.put("boolean", boolean.class);
        valid.put("double", double.class);
        valid.put("long", long.class);
        valid.put("java.lang.String", String.class);
        valid.put("java.lang.Class", Class.class);
        valid.put("java.lang.Thread", Thread.class);

        for (String name : valid.keySet()) {
            Class<?> t = valid.get(name);
            System.out.println(t.getName());
            ValueDescriptor d = new ValueDescriptor(t, "dummy");
            String typeName = d.getTypeName() + (d.isArray() ? "[]" : "");
            System.out.printf("%s -> typeName %s%n", name, typeName);
            Asserts.assertEquals(name, typeName, "Wrong type name");
        }

        // Test some illegal classes
        verifyIllegalArg(()->{new ValueDescriptor(Float.class, "ids");}, "Arrays of non-primitives should give Exception");
        verifyIllegalArg(()->{new ValueDescriptor(Integer[].class, "ids");}, "Arrays of non-primitives should give Exception");
        verifyIllegalArg(()->{new ValueDescriptor(Class[].class, "ids");}, "Arrays of non-primitives should give Exception");
        verifyIllegalArg(()->{new ValueDescriptor(MyClass.class, "MyClass");}, "MyClass should give Exception");
    }

    private static class MyClass {
        @SuppressWarnings("unused")
        int id;
    }

    private static void verifyIllegalArg(VoidFunction f, String msg) throws Throwable {
        CommonHelper.verifyException(f, msg, IllegalArgumentException.class);
    }
}

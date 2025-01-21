/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.jvm;

import static jdk.test.lib.Asserts.assertGreaterThan;
import static jdk.test.lib.Asserts.assertNE;

import jdk.jfr.internal.JVM;
import jdk.jfr.internal.JVMSupport;
import jdk.jfr.internal.Type;

/**
 * @test TestClassId
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @modules jdk.jfr/jdk.jfr.internal
 * @run main/othervm jdk.jfr.jvm.TestClassId
 */
public class TestClassId {

    public static void main(String... args) {
        assertClassIds();
        JVMSupport.createJFR();
        assertClassIds();
        JVMSupport.destroyJFR();
    }

    private static void assertClassIds() {
        long doubleClassId = Type.getTypeId(Double.class);
        assertGreaterThan(doubleClassId, 0L, "Class id must be greater than 0");

        long floatClassId = Type.getTypeId(Float.class);
        assertNE(doubleClassId, floatClassId, "Different classes must have different class ids");
    }
}

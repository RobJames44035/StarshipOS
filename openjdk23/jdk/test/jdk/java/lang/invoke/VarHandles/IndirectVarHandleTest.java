/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/* @test
 * @bug 8307508
 * @run junit IndirectVarHandleTest
 * @summary Test VarHandle::isAccessModeSupported on indirect VarHandle
 *          produced by MethodHandles.filterCoordinates
 */
import org.junit.jupiter.api.Test;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.VarHandle;
import java.util.function.IntUnaryOperator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IndirectVarHandleTest {
    @Test
    public void testIsAccessModeTypeSupported() throws Throwable {
        var lookup = MethodHandles.lookup();
        var intArrayVh = MethodHandles.arrayElementVarHandle(int[].class);
        var addOne = lookup.bind((IntUnaryOperator) a -> a + 1, "applyAsInt",
                MethodType.methodType(int.class, int.class));
        var offsetIntArrayVh = MethodHandles.filterCoordinates(intArrayVh, 1, addOne);

        for (var mode : VarHandle.AccessMode.values()) {
            assertEquals(intArrayVh.isAccessModeSupported(mode),
                    offsetIntArrayVh.isAccessModeSupported(mode), mode.toString());
        }

        var stringArrayVh = MethodHandles.arrayElementVarHandle(String[].class);
        var offsetStringArrayVh = MethodHandles.filterCoordinates(stringArrayVh, 1, addOne);

        for (var mode : VarHandle.AccessMode.values()) {
            assertEquals(stringArrayVh.isAccessModeSupported(mode),
                    offsetStringArrayVh.isAccessModeSupported(mode), mode.toString());
        }
    }
}

/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

import java.lang.StringBuilder;

import java.lang.invoke.*;
import java.lang.management.ManagementFactory;

/**
 * @test
 * @summary Test whether the hidden class unloading of StringConcatFactory works
 *
 * @requires vm.flagless
 * @run main/othervm -Xmx8M -Xms8M -Xverify:all HiddenClassUnloading
 * @run main/othervm -Xmx8M -Xms8M -Xverify:all -XX:-CompactStrings HiddenClassUnloading
 */
public class HiddenClassUnloading {
    public static void main(String[] args) throws Throwable {
        var lookup = MethodHandles.lookup();
        var types  = new Class<?>[] {
                int.class, long.class, double.class, float.class, char.class, boolean.class, String.class,
        };

        long initUnloadedClassCount = ManagementFactory.getClassLoadingMXBean().getUnloadedClassCount();

        for (int i = 0; i < 12000; i++) {
            int radix = types.length;
            String str = Integer.toString(i, radix);
            int length = str.length();
            var ptypes = new Class[length];
            for (int j = 0; j < length; j++) {
                int index = Integer.parseInt(str.substring(j, j + 1), radix);
                ptypes[j] = types[index];
            }
            StringConcatFactory.makeConcatWithConstants(
                    lookup,
                    "concat",
                    MethodType.methodType(String.class, ptypes),
                    "\1".repeat(length), // recipe
                    new Object[0]
            );
        }

        long unloadedClassCount = ManagementFactory.getClassLoadingMXBean().getUnloadedClassCount();
        if (initUnloadedClassCount == unloadedClassCount) {
            throw new RuntimeException("unloadedClassCount is zero");
        }
    }
}

/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

package gc.g1;

import java.util.LinkedHashMap;

/* @test
 * @bug 8313212
 * @summary Finalizing objects may create new concurrent marking work during reference processing.
 * If the marking work overflows the global mark stack, we should resize the global mark stack
 * until MarkStackSizeMax if possible.
 * @requires vm.gc.G1
 * @run main/othervm -XX:ActiveProcessorCount=2 -XX:MarkStackSize=1 -Xmx250m gc.g1.TestMarkStackOverflow
 */

public class TestMarkStackOverflow {
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            Finalizable holder1 = new Finalizable();
            System.out.printf("Used mem %.2f MB\n", getUsedMem());
        }
    }

    private static double getUsedMem() {
        return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (double) (1024 * 1024);
    }

    private static class Finalizable {
        public static final int NUM_OBJECTS = 200_000;
        private final LinkedHashMap<Object, Object> list = new LinkedHashMap<>();

        public Finalizable() {
            for (int i = 0; i < NUM_OBJECTS; i++) {
                Object entry = new Object();
                list.put(entry, entry);
            }
        }

        @SuppressWarnings("removal")
        protected void finalize() {
            System.out.print("");
        }
    }
}

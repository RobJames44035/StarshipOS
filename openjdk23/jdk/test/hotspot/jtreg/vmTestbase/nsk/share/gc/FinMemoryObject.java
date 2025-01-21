/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package nsk.share.gc;

import java.io.PrintStream;

/**
 * An object that occupies approximately given number of bytes in memory
 * and also records number of allocated and finalized instances.
 */
public class FinMemoryObject extends FinMemoryObject1 {
        private static int finalizedCount;

        public FinMemoryObject(int size) {
                super(size);
        }

        protected void finalize() {
                synchronized (FinMemoryObject.class) {
                        ++finalizedCount;
                }
        }

        /**
         * Returns the number of finalized FinMemoryObjects.
         */
        public static int getFinalizedCount() {
                return finalizedCount;
        }

        /**
         * Returns the number of live FinMemoryObjects (allocated but not finalized).
         */
        public static int getLiveCount() {
                return allocatedCount - finalizedCount;
        }

        public static void dumpStatistics(PrintStream out) {
                Algorithms.tryToPrintln(out, "Object count: " + getLiveCount());
        }

        public static void dumpStatistics() {
                dumpStatistics(System.out);
        }

        public static boolean isAllFinalized() {
                return getLiveCount() == 0;
        }
}

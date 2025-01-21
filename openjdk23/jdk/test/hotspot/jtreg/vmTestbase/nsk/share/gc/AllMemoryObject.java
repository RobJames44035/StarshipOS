/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package nsk.share.gc;

import java.io.PrintStream;

/**
 * An object that occupies approximately given number of bytes in memory
 * and also records number of allocated instances.
 */
public class AllMemoryObject extends MemoryObject {
        private static int allocatedCount;

        public AllMemoryObject(int size) {
                super(size);
                synchronized (AllMemoryObject.class) {
                        ++allocatedCount;
                }
        }

        /**
         * Returns the number of allocated FinMemoryObjects.
         */
        public static int getAllocatedCount() {
                return allocatedCount;
        }

        public static void dumpStatistics(PrintStream out) {
                out.println("Object count: " + getAllocatedCount());
        }

        public static void dumpStatistics() {
                dumpStatistics(System.out);
        }
}

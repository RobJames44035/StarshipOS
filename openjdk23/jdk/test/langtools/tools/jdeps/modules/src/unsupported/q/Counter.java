/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package q;

import jdk.internal.perf.PerfCounter;

public class Counter {
     public static void create(String name) {
         PerfCounter.newPerfCounter(name);
     }
}

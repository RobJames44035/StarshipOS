/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package java.lang.invoke;

import java.lang.invoke.MethodHandles.Lookup;

public class LookupHelper {
     public static Lookup newLookup(Class<?> c) {
         return new Lookup(c);
     }
}

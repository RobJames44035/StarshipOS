/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package org.indirect;

// indirectly depend on sun.misc.Unsafe
public class UnsafeRef {
     public static org.unsafe.UseUnsafe get() {
         return new org.unsafe.UseUnsafe();
     }
}

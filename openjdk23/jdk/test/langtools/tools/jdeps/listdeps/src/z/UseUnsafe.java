/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package z;

import sun.misc.Unsafe;
import jdk.internal.misc.VM;

public class UseUnsafe {
    private static Unsafe unsafe = Unsafe.getUnsafe();

    private static boolean booted = VM.isBooted();
}

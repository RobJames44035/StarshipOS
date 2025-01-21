/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package org.openjdk.foreigntest;

import java.lang.foreign.*;
import java.lang.foreign.Arena;
import java.lang.invoke.*;

public class PanamaMainInvoke {
    public static void main(String[] args) throws Throwable {
       testInvokenativeLinker();
       testInvokeMemorySegment();
    }

    public static void testInvokenativeLinker() throws Throwable {
        Linker linker = Linker.nativeLinker();
        System.out.println("Trying to obtain a downcall handle");
        var mh = MethodHandles.lookup().findVirtual(Linker.class, "downcallHandle",
                MethodType.methodType(MethodHandle.class, FunctionDescriptor.class, Linker.Option[].class));
        var handle = (MethodHandle)mh.invokeExact(linker, FunctionDescriptor.ofVoid(), new Linker.Option[0]);
        System.out.println("Got downcall handle");
    }

    public static void testInvokeMemorySegment() throws Throwable {
        System.out.println("Trying to get MemorySegment");
        var mh = MethodHandles.lookup().findVirtual(MemorySegment.class, "reinterpret",
                MethodType.methodType(MemorySegment.class, long.class));
        var seg = (MemorySegment)mh.invokeExact(MemorySegment.NULL, 10L);
        System.out.println("Got MemorySegment");
    }
}

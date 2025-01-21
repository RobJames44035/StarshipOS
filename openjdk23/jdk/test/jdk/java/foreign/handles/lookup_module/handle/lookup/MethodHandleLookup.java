/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package handle.lookup;

import java.lang.foreign.Arena;
import java.lang.foreign.Linker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;

import java.nio.file.Path;
import java.util.function.Consumer;

import org.testng.annotations.*;

public class MethodHandleLookup {

    @Test(dataProvider = "restrictedMethods")
    public void testRestrictedHandles(MethodHandle handle, String testName) throws Throwable {
        new handle.invoker.MethodHandleInvoker().call(handle);
    }

    @DataProvider(name = "restrictedMethods")
    static Object[][] restrictedMethods() {
        try {
            return new Object[][]{
                    { MethodHandles.lookup().findVirtual(Linker.class, "downcallHandle",
                            MethodType.methodType(MethodHandle.class, FunctionDescriptor.class, Linker.Option[].class)), "Linker::downcallHandle/1" },
                    { MethodHandles.lookup().findVirtual(Linker.class, "downcallHandle",
                            MethodType.methodType(MethodHandle.class, MemorySegment.class, FunctionDescriptor.class, Linker.Option[].class)), "Linker::downcallHandle/2" },
                    { MethodHandles.lookup().findVirtual(Linker.class, "upcallStub",
                            MethodType.methodType(MemorySegment.class, MethodHandle.class, FunctionDescriptor.class, Arena.class, Linker.Option[].class)), "Linker::upcallStub" },
                    { MethodHandles.lookup().findVirtual(MemorySegment.class, "reinterpret",
                            MethodType.methodType(MemorySegment.class, long.class)),
                            "MemorySegment::reinterpret/1" },
                    { MethodHandles.lookup().findVirtual(MemorySegment.class, "reinterpret",
                            MethodType.methodType(MemorySegment.class, Arena.class, Consumer.class)),
                            "MemorySegment::reinterpret/2" },
                    { MethodHandles.lookup().findVirtual(MemorySegment.class, "reinterpret",
                            MethodType.methodType(MemorySegment.class, long.class, Arena.class, Consumer.class)),
                            "MemorySegment::reinterpret/3" },
                    { MethodHandles.lookup().findStatic(SymbolLookup.class, "libraryLookup",
                            MethodType.methodType(SymbolLookup.class, String.class, Arena.class)),
                            "SymbolLookup::libraryLookup(String)" },
                    { MethodHandles.lookup().findStatic(SymbolLookup.class, "libraryLookup",
                            MethodType.methodType(SymbolLookup.class, Path.class, Arena.class)),
                            "SymbolLookup::libraryLookup(Path)" },
                    { MethodHandles.lookup().findStatic(System.class, "load",
                            MethodType.methodType(void.class, String.class)),
                            "System::load" },
                    { MethodHandles.lookup().findStatic(System.class, "loadLibrary",
                            MethodType.methodType(void.class, String.class)),
                            "System::loadLibrary" },
                    { MethodHandles.lookup().findVirtual(Runtime.class, "load",
                            MethodType.methodType(void.class, String.class)),
                            "Runtime::load" },
                    { MethodHandles.lookup().findVirtual(Runtime.class, "loadLibrary",
                            MethodType.methodType(void.class, String.class)),
                            "Runtime::loadLibrary" }
            };
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError((ex));
        }
    }
}

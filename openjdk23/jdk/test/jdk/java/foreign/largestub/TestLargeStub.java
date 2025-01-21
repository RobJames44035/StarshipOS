/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @library ../
 * @modules java.base/jdk.internal.foreign
 * @run junit/othervm --enable-native-access=ALL-UNNAMED TestLargeStub
 */

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestLargeStub extends NativeTestHelper {

    private static final int DOWNCALL_AVAILABLE_SLOTS = 248;
    private static final int UPCALL_AVAILABLE_SLOTS = 250;

    MemoryLayout STRUCT_LL = MemoryLayout.structLayout(
        C_LONG_LONG,
        C_LONG_LONG
    ); // 16 byte struct triggers return buffer usage on SysV

    @ParameterizedTest
    @MethodSource("layouts")
    public void testDowncall(ValueLayout layout, int numSlots) {
        // Link a handle with a large number of arguments, to try and overflow the code buffer
        Linker.nativeLinker().downcallHandle(
                FunctionDescriptor.of(STRUCT_LL,
                        Stream.generate(() -> layout).limit(DOWNCALL_AVAILABLE_SLOTS / numSlots).toArray(MemoryLayout[]::new)),
                Linker.Option.captureCallState("errno"));
    }

    @Test
    public void testDowncallAllowHeap() {
        // Link a handle with a large number of address arguments, to try and overflow the code buffer
        // Using 83 parameters should get us 255 parameter slots in total:
        // 83 oops + 166 for offsets + 2 for the target address + 2 for return buffer + MH recv. + NEP
        Linker.nativeLinker().downcallHandle(
                FunctionDescriptor.of(STRUCT_LL,
                        Stream.generate(() -> C_POINTER).limit(83).toArray(MemoryLayout[]::new)),
                Linker.Option.critical(true));
    }

    @ParameterizedTest
    @MethodSource("layouts")
    public void testUpcall(ValueLayout layout, int numSlots) {
        // Link a handle with a large number of arguments, to try and overflow the code buffer
        try (Arena arena = Arena.ofConfined()) {
            Linker.nativeLinker().upcallStub(
                    MethodHandles.empty(MethodType.methodType(MemorySegment.class,
                            Stream.generate(() -> layout).limit(UPCALL_AVAILABLE_SLOTS / numSlots)
                                    .map(ValueLayout::carrier)
                                    .toArray(Class<?>[]::new))),
                    FunctionDescriptor.of(STRUCT_LL,
                            Stream.generate(() -> layout).limit(UPCALL_AVAILABLE_SLOTS / numSlots)
                                    .toArray(MemoryLayout[]::new)),
                    arena);
        }
    }

    private static Stream<Arguments> layouts() {
        return Stream.of(
            arguments(C_INT, 1),
            arguments(C_LONG_LONG, 2),
            arguments(C_FLOAT, 1),
            arguments(C_DOUBLE, 2)
        );
    }
}

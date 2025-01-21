/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @modules java.base/jdk.internal.foreign
 * @build NativeTestHelper CallGeneratorHelper TestDowncallBase
 *
 * @run testng/othervm -Xcheck:jni -XX:+IgnoreUnrecognizedVMOptions -XX:-VerifyDependencies
 *   --enable-native-access=ALL-UNNAMED -Dgenerator.sample.factor=17
 *   TestDowncallScope
 *
 * @run testng/othervm -Xint -XX:+IgnoreUnrecognizedVMOptions -XX:-VerifyDependencies
 *   --enable-native-access=ALL-UNNAMED -Dgenerator.sample.factor=100000
 *   TestDowncallScope
 */

import org.testng.annotations.Test;

import java.lang.foreign.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.testng.Assert.assertEquals;

public class TestDowncallScope extends TestDowncallBase {

    static {
        System.loadLibrary("TestDowncall");
    }

    @Test(dataProvider="functions", dataProviderClass=CallGeneratorHelper.class)
    public void testDowncall(int count, String fName, CallGeneratorHelper.Ret ret,
                             List<CallGeneratorHelper.ParamType> paramTypes,
                             List<CallGeneratorHelper.StructFieldType> fields) throws Throwable {
        List<Consumer<Object>> checks = new ArrayList<>();
        MemorySegment addr = findNativeOrThrow(fName);
        FunctionDescriptor descriptor = function(ret, paramTypes, fields);
        try (Arena arena = Arena.ofShared()) {
            Object[] args = makeArgs(arena, descriptor, checks);
            boolean needsScope = descriptor.returnLayout().map(GroupLayout.class::isInstance).orElse(false);
            SegmentAllocator allocator = needsScope ?
                    arena :
                    THROWING_ALLOCATOR;
            Object res = doCall(addr, allocator, descriptor, args);
            if (ret == CallGeneratorHelper.Ret.NON_VOID) {
                checks.forEach(c -> c.accept(res));
                if (needsScope) {
                    // check that return struct has indeed been allocated in the native scope
                    assertEquals(((MemorySegment)res).scope(), arena.scope());
                }
            }
        }
    }

    static FunctionDescriptor function(Ret ret, List<ParamType> params, List<StructFieldType> fields) {
        return function(ret, params, fields, List.of());
    }

    static Object[] makeArgs(Arena arena, FunctionDescriptor descriptor, List<Consumer<Object>> checks) {
        return makeArgs(arena, descriptor, checks, 0);
    }

}

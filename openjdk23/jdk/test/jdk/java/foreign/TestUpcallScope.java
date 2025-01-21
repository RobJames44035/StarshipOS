/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @modules java.base/jdk.internal.foreign
 * @build NativeTestHelper CallGeneratorHelper TestUpcallBase
 *
 * @run testng/othervm -Xcheck:jni -XX:+IgnoreUnrecognizedVMOptions -XX:-VerifyDependencies
 *   --enable-native-access=ALL-UNNAMED -Dgenerator.sample.factor=17
 *   TestUpcallScope
 */

import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.MemorySegment;

import org.testng.annotations.Test;

import java.lang.invoke.MethodHandle;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class TestUpcallScope extends TestUpcallBase {

    static {
        System.loadLibrary("TestUpcall");
    }

    @Test(dataProvider="functions", dataProviderClass=CallGeneratorHelper.class)
    public void testUpcalls(int count, String fName, Ret ret, List<ParamType> paramTypes, List<StructFieldType> fields) throws Throwable {
        List<Consumer<Object>> returnChecks = new ArrayList<>();
        List<Consumer<Object>> argChecks = new ArrayList<>();
        MemorySegment addr = findNativeOrThrow(fName);
        try (Arena arena = Arena.ofConfined()) {
            FunctionDescriptor descriptor = function(ret, paramTypes, fields);
            MethodHandle mh = downcallHandle(LINKER, addr, arena, descriptor);
            AtomicReference<Object[]> capturedArgs = new AtomicReference<>();
            Object[] args = makeArgs(capturedArgs, arena, descriptor, returnChecks, argChecks, 0);

            Object res = mh.invokeWithArguments(args);

            if (ret == Ret.NON_VOID) {
                returnChecks.forEach(c -> c.accept(res));
            }

            Object[] capturedArgsArr = capturedArgs.get();
            for (int i = 0; i < capturedArgsArr.length; i++) {
                argChecks.get(i).accept(capturedArgsArr[i]);
            }
        }
    }

}

/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @modules java.base/jdk.internal.foreign
 * @build NativeTestHelper CallGeneratorHelper TestUpcallHighArity
 *
 * @run testng/othervm/native
 *   --enable-native-access=ALL-UNNAMED
 *   TestUpcallHighArity
 */

import java.lang.foreign.*;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class TestUpcallHighArity extends CallGeneratorHelper {
    static final MethodHandle MH_do_upcall;

    static {
        System.loadLibrary("TestUpcallHighArity");
        MH_do_upcall = LINKER.downcallHandle(
                findNativeOrThrow("do_upcall"),
                FunctionDescriptor.ofVoid(C_POINTER,
                S_PDI_LAYOUT, C_INT, C_DOUBLE, C_POINTER,
                S_PDI_LAYOUT, C_INT, C_DOUBLE, C_POINTER,
                S_PDI_LAYOUT, C_INT, C_DOUBLE, C_POINTER,
                S_PDI_LAYOUT, C_INT, C_DOUBLE, C_POINTER)
        );
    }

    @Test(dataProvider = "args")
    public void testUpcall(MethodHandle downcall, MethodType upcallType,
                           FunctionDescriptor upcallDescriptor) throws Throwable {
        AtomicReference<Object[]> capturedArgs = new AtomicReference<>();
        try (Arena arena = Arena.ofConfined()) {
            Object[] args = new Object[upcallType.parameterCount() + 1];
            args[0] = makeArgSaverCB(upcallDescriptor, arena, capturedArgs, -1);
            List<MemoryLayout> argLayouts = upcallDescriptor.argumentLayouts();
            List<Consumer<Object>> checks = new ArrayList<>();
            for (int i = 1; i < args.length; i++) {
                TestValue testValue = genTestValue(argLayouts.get(i - 1), arena);
                args[i] = testValue.value();
                checks.add(testValue.check());
            }

            downcall.invokeWithArguments(args);

            Object[] capturedArgsArr = capturedArgs.get();
            for (int i = 0; i < capturedArgsArr.length; i++) {
                checks.get(i).accept(capturedArgsArr[i]);
            }
        }
    }

    @DataProvider
    public static Object[][] args() {
        return new Object[][]{
            { MH_do_upcall,
                MethodType.methodType(void.class,
                    MemorySegment.class, int.class, double.class, MemorySegment.class,
                    MemorySegment.class, int.class, double.class, MemorySegment.class,
                    MemorySegment.class, int.class, double.class, MemorySegment.class,
                    MemorySegment.class, int.class, double.class, MemorySegment.class),
                FunctionDescriptor.ofVoid(
                    S_PDI_LAYOUT, C_INT, C_DOUBLE, C_POINTER,
                    S_PDI_LAYOUT, C_INT, C_DOUBLE, C_POINTER,
                    S_PDI_LAYOUT, C_INT, C_DOUBLE, C_POINTER,
                    S_PDI_LAYOUT, C_INT, C_DOUBLE, C_POINTER)
            }
        };
    }

}

/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @library ../ /test/lib
 * @modules java.base/jdk.internal.ref java.base/jdk.internal.foreign
 * @run testng/othervm --enable-native-access=ALL-UNNAMED TestDontRelease
 */

import jdk.internal.foreign.MemorySessionImpl;
import org.testng.annotations.Test;

import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.MemorySegment;
import java.lang.invoke.MethodHandle;

import static java.lang.foreign.ValueLayout.ADDRESS;
import static java.lang.foreign.ValueLayout.JAVA_INT;
import static org.testng.Assert.assertTrue;

public class TestDontRelease extends NativeTestHelper  {

    static {
        System.loadLibrary("DontRelease");
    }

    @Test
    public void testDontRelease() {
        MethodHandle handle = downcallHandle("test_ptr", FunctionDescriptor.ofVoid(ADDRESS));
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment segment = arena.allocate(JAVA_INT);
            ((MemorySessionImpl)arena.scope()).whileAlive(() -> {
                Thread t = new Thread(() -> {
                    try {
                        // acquire of the segment should fail here,
                        // due to wrong thread
                        handle.invokeExact(segment);
                    } catch (Throwable e) {
                        // catch the exception.
                        assertTrue(e instanceof WrongThreadException);
                        assertTrue(e.getMessage().matches(".*Attempted access outside owning thread.*"));
                    }
                });
                t.start();
                try {
                    t.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                // the downcall above should not have called release on the session
                // so doing it here should succeed without error
            });
        }
    }
}

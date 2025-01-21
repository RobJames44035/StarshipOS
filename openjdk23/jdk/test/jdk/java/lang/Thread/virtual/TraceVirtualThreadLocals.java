/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/**
 * @test
 * @summary Test diagnostic option for detecting a virtual thread using thread locals
 * @requires vm.continuations
 * @library /test/lib
 * @run junit/othervm -Djdk.traceVirtualThreadLocals TraceVirtualThreadLocals
 * @run junit/othervm -Djdk.traceVirtualThreadLocals=true TraceVirtualThreadLocals
 */

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import jdk.test.lib.thread.VThreadRunner;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TraceVirtualThreadLocals {

    @Test
    void testInitialValue() throws Exception {
        String output = run(() -> {
            ThreadLocal<String> name = new ThreadLocal<>() {
                @Override
                protected String initialValue() {
                    return "<unnamed>";
                }
            };
            name.get();
        });
        assertContains(output, "java.lang.ThreadLocal.setInitialValue");
    }

    @Test
    void testSet() throws Exception {
        String output = run(() -> {
            ThreadLocal<String> name = new ThreadLocal<>();
            name.set("duke");
        });
        assertContains(output, "java.lang.ThreadLocal.set");
    }

    /**
     * Run a task in a virtual thread, returning a String with any output printed
     * to standard output.
     */
    private static String run(Runnable task) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(baos, true));
        try {
            VThreadRunner.run(task::run);
        } finally {
            System.setOut(original);
        }
        String output = new String(baos.toByteArray());
        System.out.println(output);
        return output;
    }

    /**
     * Tests that s1 contains s2.
     */
    private static void assertContains(String s1, String s2) {
        assertTrue(s1.contains(s2), s2 + " not found!!!");
    }
}

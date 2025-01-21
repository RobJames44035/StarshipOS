/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/**
 * @test
 * @bug 8330998
 * @summary Verify that even if the stdout is redirected java.io.Console will
 *          use it for writing.
 * @modules jdk.internal.le
 * @library /test/lib
 * @run main RedirectedStdOut runRedirectAllTest
 * @run main/othervm --enable-native-access=ALL-UNNAMED RedirectedStdOut runRedirectOutOnly
 */

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class RedirectedStdOut {
    private static final String OUTPUT = "Hello!";

    public static void main(String... args) throws Throwable {
        RedirectedStdOut.class.getDeclaredMethod(args[0])
                              .invoke(new RedirectedStdOut());
    }

    //verify the case where neither stdin/out/err is attached to a terminal,
    //this test is weaker, but more reliable:
    void runRedirectAllTest() throws Exception {
        ProcessBuilder builder =
                ProcessTools.createTestJavaProcessBuilder(ConsoleTest.class.getName());
        OutputAnalyzer output = ProcessTools.executeProcess(builder);

        output.waitFor();

        if (output.getExitValue() != 0) {
            throw new AssertionError("Unexpected return value: " + output.getExitValue() +
                                     ", actualOut: " + output.getStdout() +
                                     ", actualErr: " + output.getStderr());
        }

        String expectedOut = OUTPUT;
        String actualOut = output.getStdout();

        if (!Objects.equals(expectedOut, actualOut)) {
            throw new AssertionError("Unexpected stdout content. " +
                                     "Expected: '" + expectedOut + "'" +
                                     ", got: '" + actualOut + "'");
        }

        String expectedErr = "";
        String actualErr = output.getStderr();

        if (!Objects.equals(expectedErr, actualErr)) {
            throw new AssertionError("Unexpected stderr content. " +
                                     "Expected: '" + expectedErr + "'" +
                                     ", got: '" + actualErr + "'");
        }
    }

    //verify the case where stdin is attached to a terminal,
    //this test allocates pty, and it might be skipped, if the appropriate
    //native functions cannot be found
    //it also leaves the VM in a broken state (with a pty attached), and so
    //should run in a separate VM instance
    void runRedirectOutOnly() throws Throwable {
        Path stdout = Path.of(".", "stdout.txt").toAbsolutePath();

        Files.deleteIfExists(stdout);

        Linker linker = Linker.nativeLinker();
        SymbolLookup stdlib = linker.defaultLookup();
        MemorySegment parent = Arena.global().allocate(ValueLayout.ADDRESS);
        MemorySegment child = Arena.global().allocate(ValueLayout.ADDRESS);
        Optional<MemorySegment> openptyAddress = stdlib.find("openpty");

        if (openptyAddress.isEmpty()) {
            System.out.println("Cannot lookup openpty.");
            //does not have forkpty, ignore
            return ;
        }

        Optional<MemorySegment> loginttyAddress = stdlib.find("login_tty");

        if (loginttyAddress.isEmpty()) {
            System.out.println("Cannot lookup login_tty.");
            //does not have forkpty, ignore
            return ;
        }

        FunctionDescriptor openttyDescriptor =
                FunctionDescriptor.of(ValueLayout.JAVA_INT,
                                      ValueLayout.ADDRESS,
                                      ValueLayout.ADDRESS,
                                      ValueLayout.ADDRESS,
                                      ValueLayout.ADDRESS,
                                      ValueLayout.ADDRESS);
        MethodHandle forkpty = linker.downcallHandle(openptyAddress.get(),
                                                     openttyDescriptor);
        int res = (int) forkpty.invoke(parent,
                                       child,
                                       MemorySegment.NULL,
                                       MemorySegment.NULL,
                                       MemorySegment.NULL);

        if (res != 0) {
            throw new AssertionError();
        }

        //set the current VM's in/out to the terminal:
        FunctionDescriptor loginttyDescriptor =
                FunctionDescriptor.of(ValueLayout.JAVA_INT,
                                      ValueLayout.JAVA_INT);
        MethodHandle logintty = linker.downcallHandle(loginttyAddress.get(),
                                                      loginttyDescriptor);
        logintty.invoke(child.get(ValueLayout.JAVA_INT, 0));

        //createTestJavaProcessBuilder logs to (current process') System.out, but
        //that may not work since the redirect. Setting System.out to a scratch value:
        System.setOut(new PrintStream(new ByteArrayOutputStream()));

        ProcessBuilder builder =
            ProcessTools.createTestJavaProcessBuilder(ConsoleTest.class.getName());

        builder.inheritIO();
        builder.redirectOutput(stdout.toFile());

        OutputAnalyzer output = ProcessTools.executeProcess(builder);

        output.waitFor();

        String expectedOut = OUTPUT;
        String actualOut = Files.readString(stdout);

        if (!Objects.equals(expectedOut, actualOut)) {
            throw new AssertionError("Unexpected stdout content. " +
                                     "Expected: '" + expectedOut + "'" +
                                     ", got: '" + actualOut + "'");
        }
    }

    public static class ConsoleTest {
        public static void main(String... args) {
            System.console().printf(OUTPUT);
            System.exit(0);
        }
    }
}

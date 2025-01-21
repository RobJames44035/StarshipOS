/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/* @test
 * @bug 8341666
 * @summary Test of FileInputStream reading from stdin and a named pipe
 * @requires os.family != "windows"
 * @library .. /test/lib
 * @build jdk.test.lib.Platform
 * @run junit/othervm --enable-native-access=ALL-UNNAMED PseudoDevice
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;
import jdk.test.lib.Platform;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import static org.junit.jupiter.api.Assertions.*;

public class PseudoDevice {

    private static final String PIPE = "pipe";
    private static final File PIPE_FILE = new File(PIPE);
    private static final String SENTENCE =
        "Rien n'est permis mais tout est possible";

    private static class mkfifo {
        public static final FunctionDescriptor DESC = FunctionDescriptor.of(
            ValueLayout.JAVA_INT,
            ValueLayout.ADDRESS,
            ValueLayout.JAVA_SHORT
        );

        public static final MemorySegment ADDR;
        static {
            Linker linker = Linker.nativeLinker();
            SymbolLookup stdlib = linker.defaultLookup();
            ADDR = stdlib.find("mkfifo").orElseThrow();
        }

        public static final MethodHandle HANDLE =
            Linker.nativeLinker().downcallHandle(ADDR, DESC);
    }

    public static int mkfifo(MemorySegment x0, short x1) {
        var mh$ = mkfifo.HANDLE;
        try {
            return (int)mh$.invokeExact(x0, x1);
        } catch (Throwable ex$) {
           throw new AssertionError("should not reach here", ex$);
        }
    }

    private static Thread createWriteThread() {
        Thread t = new Thread(
            new Runnable() {
                public void run() {
                    try (FileOutputStream fos = new FileOutputStream(PIPE);) {
                        fos.write(SENTENCE.getBytes());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        );
        t.start();
        return t;
    }

    @BeforeAll
    static void before() throws InterruptedException, IOException {
        if (Platform.isWindows())
            return;

        PIPE_FILE.delete();
        try (var newArena = Arena.ofConfined()) {
            var addr = newArena.allocateFrom(PIPE);
            short mode = 0666;
            assertEquals(0, mkfifo(addr, mode));
        }
        if (!PIPE_FILE.exists())
            throw new RuntimeException("Failed to create " + PIPE);
    }

    @AfterAll
    static void after() throws IOException {
        if (Platform.isWindows())
            return;

        PIPE_FILE.delete();
    }

    /**
     * Tests that new FileInputStream(File).available() does not throw
     */
    @Test
    @DisabledOnOs(OS.WINDOWS)
    void availableStdin() throws IOException {
        File stdin = new File("/dev", "stdin");
        if (stdin.exists()) {
            try (InputStream s = new FileInputStream(stdin);) {
                s.available();
            }
        }
    }

    /**
     * Tests that new FileInputStream(File).skip(0) does not throw
     */
    @Test
    @DisabledOnOs(OS.WINDOWS)
    void skipStdin() throws IOException {
        File stdin = new File("/dev", "stdin");
        if (stdin.exists()) {
            try (InputStream s = new FileInputStream(stdin);) {
                s.skip(0);
            }
        }
    }

    /**
     * Tests new FileInputStream(File).readAllBytes().
     */
    @Test
    @DisabledOnOs(OS.WINDOWS)
    void readAllBytes() throws InterruptedException, IOException {
        Thread t = createWriteThread();
        try (InputStream in = new FileInputStream(PIPE)) {
            String s = new String(in.readAllBytes());
            System.out.println(s);
            assertEquals(SENTENCE, s);
        } finally {
            t.join();
        }
    }

    /**
     * Tests new FileInputStream(File).readNBytes(byte[],int,int).
     */
    @Test
    @DisabledOnOs(OS.WINDOWS)
    void readNBytesNoOverride() throws InterruptedException, IOException {
        Thread t = createWriteThread();
        try (InputStream in = new FileInputStream(PIPE)) {
            final int offset = 11;
            final int length = 17;
            assert length <= SENTENCE.length();
            byte[] b = new byte[offset + length];
            int n = in.readNBytes(b, offset, length);
            String s = new String(b, offset, length);
            System.out.println(s);
            assertEquals(SENTENCE.substring(0, length), s);
        } finally {
            t.join();
        }
    }

    /**
     * Tests new FileInputStream(File).readNBytes(int).
     */
    @Test
    @DisabledOnOs(OS.WINDOWS)
    void readNBytesOverride() throws InterruptedException, IOException {
        Thread t = createWriteThread();
        try (InputStream in = new FileInputStream(PIPE)) {
            final int length = 17;
            assert length <= SENTENCE.length();
            byte[] b = in.readNBytes(length);
            String s = new String(b);
            System.out.println(s);
            assertEquals(SENTENCE.substring(0, length), s);
        } finally {
            t.join();
        }
    }
}

/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/**
 * @test
 * @bug 8307944
 * @library /test/lib
 * @build DumpMethodHandleInternals
 * @run main DumpMethodHandleInternals

 * @summary Test startup with -Djdk.invoke.MethodHandle.dumpMethodHandleInternals
 *          to work properly
 */

import java.nio.file.Files;
import java.nio.file.Path;

import jdk.test.lib.process.ProcessTools;

public class DumpMethodHandleInternals {

    private static final Path DUMP_DIR = Path.of("DUMP_METHOD_HANDLE_INTERNALS");

    public static void main(String[] args) throws Exception {
        if (ProcessTools.executeTestJava("-Djdk.invoke.MethodHandle.dumpMethodHandleInternals",
                                         "-version")
                .outputTo(System.out)
                .errorTo(System.out)
                .getExitValue() != 0)
            throw new RuntimeException("Test failed - see output");

        if (Files.notExists(DUMP_DIR))
            throw new RuntimeException(DUMP_DIR + " not created");
    }

}

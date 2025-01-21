/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package jdk.jfr.tool;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import jdk.test.lib.Utils;
import jdk.test.lib.process.OutputAnalyzer;

/**
 * @test
 * @summary Test jfr print
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @run main/othervm jdk.jfr.tool.TestPrint
 */
public class TestPrint {

    public static void main(String[] args) throws Throwable {

        OutputAnalyzer output = ExecuteHelper.jfr("print");
        output.shouldContain("missing file");

        output = ExecuteHelper.jfr("print", "missing.jfr");
        output.shouldContain("could not open file ");

        Path file = Utils.createTempFile("faked-print-file",  ".jfr");
        FileWriter fw = new FileWriter(file.toFile());
        fw.write('d');
        fw.close();
        output = ExecuteHelper.jfr("print", "--wrongOption", file.toAbsolutePath().toString());
        output.shouldContain("unknown option");
        Files.delete(file);
    }
}

/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import jdk.test.lib.Asserts;

/**
 * @test
 * @bug 8141690
 * @summary MakeJavaSecurity.java functions
 * @library /test/lib /test/jdk
 * @run main MakeJavaSecurityTest
 */
public class MakeJavaSecurityTest {

    private static final String TEST_SRC = System.getProperty("test.src", ".");

    public static void main(String[] args) throws Exception {
        Path toolPath = getMakeJavaSecPath();

        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(
                toolPath.toString(),
                TEST_SRC + "/raw_java_security",
                "outfile",
                "solaris",
                "sparc",
                "somepolicy",
                TEST_SRC + "/more_restricted");

        OutputAnalyzer output = new OutputAnalyzer(pb.start());
        output.shouldHaveExitValue(0);

        verifyOutputFile();
    }

    private static Path getMakeJavaSecPath() {
        String testRoot = System.getProperty("test.root", ".");
        Path toolPath = Paths.get(testRoot).getParent().getParent();
        toolPath = toolPath.resolve("make/jdk/src/classes/build/tools" +
                "/makejavasecurity/MakeJavaSecurity.java");

        Asserts.assertTrue(Files.exists(toolPath),
                String.format("Cannot find %s. Maybe not all code repos are available",
                        toolPath));
        return toolPath;
    }

    private static void verifyOutputFile() throws IOException {
        Path actualFile = Path.of("./outfile");
        Path expectedFile = Path.of(TEST_SRC + "/final_java_security");

        List<String> list1 = Files.readAllLines(actualFile);
        List<String> list2 = Files.readAllLines(expectedFile);
        list1 = removeEmptyLines(list1);
        list2 = removeEmptyLines(list2);

        String errorMessage = String.format("""
                Expected file content:
                 %s\s
                not equal to actual:
                 %s\s
                """, list2, list1);

        Asserts.assertTrue(list1.equals(list2), errorMessage);
    }

    private static List<String> removeEmptyLines(List<String> list) {
        return list.stream()
                .filter(item -> !item.isBlank())
                .collect(Collectors.toList());
    }
}

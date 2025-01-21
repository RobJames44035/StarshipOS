/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug      4979486
 * @summary  Make sure tool parses CR line separators properly.
 * @library  ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build    javadoc.tester.*
 * @run main TestCRLineSeparator
 */

import java.io.*;
import java.util.*;

import javadoc.tester.JavadocTester;

public class TestCRLineSeparator extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new TestCRLineSeparator();
        tester.runTests();
    }

    @Test
    public void test() throws IOException {
        initFiles(new File(testSrc), new File("src"), "pkg");
        javadoc("-d", "out",
                "-sourcepath", "src",
                "pkg");
        checkExit(Exit.OK);

        checkOutput("pkg/MyClass.html", true,
                "Line 1\n"
                + " Line 2");
    }

    // recursively copy files from fromDir to toDir, replacing newlines
    // with \r
    void initFiles(File fromDir, File toDir, String f) throws IOException {
        File from_f = new File(fromDir, f);
        File to_f = new File(toDir, f);
        if (from_f.isDirectory()) {
            to_f.mkdirs();
            for (String child: from_f.list()) {
                initFiles(from_f, to_f, child);
            }
        } else {
            List<String> lines = new ArrayList<>();
            try (BufferedReader in = new BufferedReader(new FileReader(from_f))) {
                String line;
                while ((line = in.readLine()) != null)
                    lines.add(line);
            }
            try (BufferedWriter out = new BufferedWriter(new FileWriter(to_f))) {
                for (String line: lines) {
                    out.write(line);
                    out.write("\r");
                }
            }
        }
    }
}

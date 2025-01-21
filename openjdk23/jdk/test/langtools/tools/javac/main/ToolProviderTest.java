/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8159855
 * @summary test javac's ToolProvider
 * @library /tools/lib
 * @build toolbox.TestRunner toolbox.ToolBox
 * @run main ToolProviderTest
 */

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.spi.ToolProvider;

import toolbox.TestRunner;
import toolbox.ToolBox;

public class ToolProviderTest extends TestRunner {
    public static void main(String... args) throws Exception {
        new ToolProviderTest().runTests();
    }

    ToolBox tb = new ToolBox();
    ToolProvider javac;

    ToolProviderTest() {
        super(System.err);
        javac = ToolProvider.findFirst("javac").get();
    }

    @Test
    public void testProviders() throws Exception {
        Map<String, ToolProvider> providers = new LinkedHashMap<>();
        for (ToolProvider tp : ServiceLoader.load(ToolProvider.class,
                ClassLoader.getSystemClassLoader())) {
            System.out.println("Provider: " + tp.name() + ": " + tp.getClass().getName());
            providers.put(tp.name(), tp);
        }
        if (!providers.containsKey("javac")) {
            error("javac ToolProvider not found");
        }
    }

    @Test
    public void testOneStream() throws Exception {
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            int rc = javac.run(pw, pw, "-help");
            if (rc != 0) {
                error("unexpected exit code: " + rc);
            }
        }
        String out = sw.toString();
        if (!out.contains("Usage:")) {
            error("expected output not found");
        }
    }

    @Test
    public void testTwoStreamsOut() throws Exception {
        StringWriter swOut = new StringWriter();
        StringWriter swErr = new StringWriter();
        try (PrintWriter pwOut = new PrintWriter(swOut);
                PrintWriter pwErr = new PrintWriter(swErr)) {
            int rc = javac.run(pwOut, pwErr, "-help");
            if (rc != 0) {
                error("unexpected exit code: " + rc);
            }
        }
        String out = swOut.toString();
        String err = swErr.toString();
        if (!out.contains("Usage:")) {
            error("stdout: expected output not found");
        }
        if (!err.isEmpty()) {
            error("stderr: unexpected output");
        }
    }

    @Test
    public void testTwoStreamsErr() throws Exception {
        Path src = Paths.get("src");
        Path classes = Paths.get("classes");
        tb.writeJavaFiles(src,
            "import java.util.*; class C { # }");

        StringWriter swOut = new StringWriter();
        StringWriter swErr = new StringWriter();
        try (PrintWriter pwOut = new PrintWriter(swOut);
                PrintWriter pwErr = new PrintWriter(swErr)) {
            int rc = javac.run(pwOut, pwErr,
                "-d", classes.toString(),
                src.resolve("C.java").toString());
            if (rc != 1) {
                error("unexpected exit code: " + rc);
            }
        }

        String out = swOut.toString();
        String err = swErr.toString();
        if (!out.isEmpty()) {
            error("stdout: unexpected output");
        }
        if (!err.contains("illegal character")) {
            error("stderr: expected output not found");
        }
    }
}

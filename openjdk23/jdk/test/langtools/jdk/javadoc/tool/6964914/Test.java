/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 6964914 8182765
 * @summary javadoc does not output number of warnings using user written doclet
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 */

import java.io.*;

public class Test {
    public static void main(String... args) throws Exception {
        new Test().run();
    }

    public void run() throws Exception {
        javadoc("Error.java", "1 error");
        javadoc("JavacWarning.java", "1 warning");
        javadoc("JavadocWarning.java", "1 warning");
        if (errors > 0)
            throw new Exception(errors + " errors found");
    }

    void javadoc(String path, String expect) {
        File testSrc = new File(System.getProperty("test.src"));
        String[] args = {
            "-Xdoclint:none",
            "-source", "8",
            "-classpath", ".",
            "-package",
            new File(testSrc, path).getPath()
        };

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        int rc = jdk.javadoc.internal.tool.Main.execute(args, pw);
        pw.close();
        String out = sw.toString();
        if (!out.isEmpty())
            System.err.println(out);
        System.err.println("javadoc exit: rc=" + rc);

        if (!out.contains(expect))
            error("expected text not found: " + expect);
    }

    void error(String msg) {
        System.err.println("Error: " + msg);
        errors++;
    }

    int errors;
}

/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8025693
 * @summary javadoc should ignore <clinit> methods found in classes on classpath
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 */

import java.io.*;

public class Test {
    public static void main(String[] args) throws Exception {
        new Test().run();
    }

    final File baseFile = new File("src/Base.java");
    final String baseText =
        """
            package p;
            public class Base { static { } }
            """;

    final File srcFile = new File("src/C.java");
    final String srcText =
        """
            package p;
            /** comment */
            public abstract class C extends Base { }
            """;

    void run() throws Exception {
        File classesDir = new File("classes");
        classesDir.mkdirs();
        writeFile(baseFile, baseText);
        String[] javacArgs = {
            "-d", classesDir.getPath(),
            baseFile.getPath()
        };
        com.sun.tools.javac.Main.compile(javacArgs);

        writeFile(srcFile, srcText);
        String[] args = {
            "-d", "api",
            "-classpath", classesDir.getPath(),
            "-package", "p",
            srcFile.getPath()
        };

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream prev = System.err;
        System.setErr(ps);
        try {
            int rc = jdk.javadoc.internal.tool.Main.execute(args);
        } finally {
            System.err.flush();
            System.setErr(prev);
        }
        String out = baos.toString();
        System.out.println(out);

        String errorMessage = "java.lang.IllegalArgumentException: <clinit>";
        if (out.contains(errorMessage))
            throw new Exception("error message found: " + errorMessage);
    }

    void writeFile(File file, String body) throws IOException {
        file.getParentFile().mkdirs();
        try (FileWriter out = new FileWriter(file)) {
            out.write(body);
        }
    }
}


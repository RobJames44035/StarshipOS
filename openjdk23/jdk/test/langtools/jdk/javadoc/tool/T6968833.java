/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 6968833
 * @summary javadoc reports error but still returns 0
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 */

import java.io.*;

public class T6968833 {
    public static void main(String... args) throws IOException {
        new T6968833().run();
    }

    void run() throws IOException {
        File srcDir = new File("src");
        // following file causes error: No public or protected classes found to document.
        File f = writeFile(srcDir, "Foo.java", "class Foo { }");
        String[] args = { f.getPath() };
        int rc = jdk.javadoc.internal.tool.Main.execute(args);
        if (rc == 0)
            throw new Error("Unexpected exit from javadoc: " + rc);
    }

    File writeFile(File dir, String path, String s) throws IOException {
        File f = new File(dir, path);
        f.getParentFile().mkdirs();
        try (Writer out = new FileWriter(f)) {
            out.write(s);
        }
        return f;
    }
}


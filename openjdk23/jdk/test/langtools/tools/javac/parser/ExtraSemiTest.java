/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @bug 6921495
 * @summary spurious semicolons in class def cause empty NOPOS blocks
 * @modules jdk.compiler
 */

import java.io.*;
import java.net.*;
import java.util.*;
import javax.tools.*;
import com.sun.source.util.*;

public class ExtraSemiTest {

    static class JavaSource extends SimpleJavaFileObject {

        final static String source =
                        "class C {\n" +
                        "    int x;;\n" +
                        "    class X { int i;; };\n" +
                        "}";

        JavaSource() {
            super(URI.create("myfo:/C.java"), JavaFileObject.Kind.SOURCE);
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return source;
        }
    }

    public static void main(String... args) throws IOException {
        new ExtraSemiTest().run();
    }

    void run() throws IOException {
        File destDir = new File("classes"); destDir.mkdir();
        final JavaCompiler tool = ToolProvider.getSystemJavaCompiler();
        JavaSource source = new JavaSource();
        JavacTask ct = (JavacTask)tool.getTask(null, null, null,
                Arrays.asList("-d", destDir.getPath(), "-XD-printsource"),
                null,
                Arrays.asList(source));
        Boolean ok = ct.call();
        if (!ok) throw new AssertionError("compilation failed");

        String text = readFile(new File(destDir, "C.java"));
        System.out.println(text);

        // compress/canonicalize all whitespace
        String canon = text.replaceAll("\\s+", " ");
        System.out.println("canon: " + canon);

        // There are no empty blocks in the original text.
        // C will be given a default constructor "C() { super(); }" which
        // does not have any empty blocks.
        // The bug is that spurious semicolons in the class defn are parsed
        // into redundant empty blocks in the tree, so verify there are
        // no empty blocks in the -printsource output

        if (canon.contains("{ }"))
            throw new AssertionError("unexpected empty block found");
    }

    String readFile(File f) throws IOException {
        int len = (int) f.length();
        byte[] data = new byte[len];
        try (DataInputStream in = new DataInputStream(new FileInputStream(f))) {
            in.readFully(data);
            return new String(data);
        }
    }
}

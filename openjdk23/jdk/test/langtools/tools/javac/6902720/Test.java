/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

import java.io.*;
import java.net.*;
import javax.tools.*;
import java.util.*;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.util.JavacTask;
import com.sun.tools.javac.api.JavacTool;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.Pretty;

/**
 * @test
 * @bug 6902720
 * @summary javac pretty printer does not handle enums correctly
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.file
 *          jdk.compiler/com.sun.tools.javac.tree
 */

public class Test {

    public static void main(String[] args) throws Exception {
        Test t = new Test();
        t.run("E1.java", "E2.java");
    }

    void run(String... args) throws Exception {
        File testSrcDir = new File(System.getProperty("test.src"));
        for (String arg: args) {
            test(new File(testSrcDir, arg));
        }
    }

    void test(File test) throws Exception {
        JavacTool tool1 = JavacTool.create();
        try (StandardJavaFileManager fm = tool1.getStandardFileManager(null, null, null)) {
            Iterable<? extends JavaFileObject> files = fm.getJavaFileObjects(test);

            // parse test file into a tree, and write it out to a stringbuffer using Pretty
            JavacTask t1 = tool1.getTask(null, fm, null, null, null, files);
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            Iterable<? extends CompilationUnitTree> trees = t1.parse();
            for (CompilationUnitTree tree: trees) {
                new Pretty(pw, true).printExpr((JCTree) tree);
            }
            pw.close();

            final String out = sw.toString();
            System.err.println("generated code:\n" + out + "\n");

            // verify the generated code is valid Java by compiling it
            JavacTool tool2 = JavacTool.create();
            JavaFileObject fo =
                    SimpleJavaFileObject.forSource(URI.create("output"),
                                                   out);
            JavacTask t2 = tool2.getTask(null, fm, null, null, null, Collections.singleton(fo));
            boolean ok = t2.call();
            if (!ok)
                throw new Exception("compilation of generated code failed");

            File expectedClass = new File(test.getName().replace(".java", ".class"));
            if (!expectedClass.exists())
                throw new Exception(expectedClass + " not found");
        }
    }
}


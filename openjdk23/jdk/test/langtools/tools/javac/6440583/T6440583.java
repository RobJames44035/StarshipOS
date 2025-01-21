/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6440583
 * @summary better error recovery
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.file
 *          jdk.compiler/com.sun.tools.javac.tree
 *          jdk.compiler/com.sun.tools.javac.util
 */

import java.io.*;
import java.util.*;
import javax.tools.*;
import com.sun.source.tree.*;
import com.sun.source.util.*;
import com.sun.tools.javac.api.*;
import com.sun.tools.javac.tree.JCTree.*;

public class T6440583 {
    public static void main(String... args) throws Exception {
        String testSrc = System.getProperty("test.src", ".");
        String testClasses = System.getProperty("test.classes", ".");
        JavacTool tool = JavacTool.create();
        try (StandardJavaFileManager fm = tool.getStandardFileManager(null, null, null)) {
            Iterable<? extends JavaFileObject> files =
                fm.getJavaFileObjectsFromFiles(Arrays.asList(new File(testSrc, "A.java")));
            JavacTask task = tool.getTask(null, fm, null, null, null, files);

            Iterable<? extends Tree> trees = task.parse();

            TreeScanner<Boolean,Void> checker = new TreeScanner<Boolean,Void>() {
                public Boolean visitErroneous(ErroneousTree tree, Void ignore) {
                    JCErroneous etree = (JCErroneous) tree;
                    List<? extends Tree> errs = etree.getErrorTrees();
                    System.err.println("errs: " + errs);
                    if (errs == null || errs.size() == 0)
                        throw new AssertionError("no error trees found");
                    found = true;
                    return true;
                }
            };

            for (Tree tree: trees)
                checker.scan(tree, null);

            if (!found)
                throw new AssertionError("no ErroneousTree nodes found");
        }
    }

    private static boolean found;
}

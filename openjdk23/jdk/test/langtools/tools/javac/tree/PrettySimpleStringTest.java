/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8006033
 * @summary bug in Pretty.toSimpleString
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.file
 *          jdk.compiler/com.sun.tools.javac.tree
 */

import java.io.File;

import javax.tools.StandardJavaFileManager;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.util.JavacTask;
import com.sun.tools.javac.api.JavacTool;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.Pretty;

public class PrettySimpleStringTest {
    public static void main(String... args) throws Exception {
        new PrettySimpleStringTest().run();
    }

    void run() throws Exception {
        File testSrc = new File(System.getProperty("test.src"));
        File thisFile = new File(testSrc, getClass().getName() + ".java");
        JavacTool tool = JavacTool.create();
        try (StandardJavaFileManager fm = tool.getStandardFileManager(null, null, null)) {
            JavacTask task = tool.getTask(null, fm, null, null, null,
                    fm.getJavaFileObjects(thisFile));
            Iterable<? extends CompilationUnitTree> trees = task.parse();
            CompilationUnitTree thisTree = trees.iterator().next();

            {   // test default
                String thisSrc = Pretty.toSimpleString((JCTree) thisTree);
                System.err.println(thisSrc);
                String expect = "import jav[...]} } }";
                if (!thisSrc.equals(expect)) {
                    throw new Exception("unexpected result");
                }
            }

            {   // test explicit length
                String thisSrc = Pretty.toSimpleString((JCTree) thisTree, 32);
                System.err.println(thisSrc);
                String expect = "import java.io.Fil[...]} } } } }";
                if (!thisSrc.equals(expect)) {
                    throw new Exception("unexpected result");
                }
            }
        }
    }
}

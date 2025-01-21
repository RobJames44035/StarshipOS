/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6345974
 * @summary JCPrimitiveTypeTree.getPrimitiveTypeKind() inappropriately throws an
 *              AssertionError for "void"
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.file
 */

import com.sun.tools.javac.api.JavacTool;
import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.PrimitiveTypeTree;
import com.sun.source.util.JavacTask;
import com.sun.source.util.TreeScanner;
import javax.tools.StandardJavaFileManager;


public class T6345974 {
    public static void main(String[] args) throws Exception {
        PrintWriter out = new PrintWriter(System.out, true);
        JavacTool tool = JavacTool.create();
        try (StandardJavaFileManager fm = tool.getStandardFileManager(null, null, null)) {
            File testSrc = new File(System.getProperty("test.src"));
            Iterable<? extends JavaFileObject> f =
                fm.getJavaFileObjectsFromFiles(Arrays.asList(new File(testSrc, "T6345974.java")));
            JavacTask task = tool.getTask(out, fm, null, null, null, f);
            Iterable<? extends CompilationUnitTree> trees = task.parse();
            out.flush();

            Scanner s = new Scanner();
            for (CompilationUnitTree t: trees)
                s.scan(t, null);
        }
    }

    private static class Scanner extends TreeScanner<Void,Void> {
        public Void visitPrimitiveType(PrimitiveTypeTree node, Void ignore) {
            // The following call of getPrimitiveTypeKind should not throw an AssertionError
            System.out.println(node + " " + node.getPrimitiveTypeKind());
            return null;
        }
    }

    boolean b;
    byte by;
    char c;
    double d;
    float f;
    int i;
    long l;
    short s;
    void f() { }
}

/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8206986
 * @summary Ensure SimpleTreeVisitor.visitSwitchExpression behaves as it should
 * @modules jdk.compiler
 */

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import javax.tools.*;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.SwitchExpressionTree;
import com.sun.source.tree.YieldTree;
import com.sun.source.tree.Tree;
import com.sun.source.util.JavacTask;
import com.sun.source.util.SimpleTreeVisitor;
import com.sun.source.util.TreePathScanner;

public class SwitchExpressionSimpleVisitorTest {

    public static void main(String[] args) throws Exception {
        new SwitchExpressionSimpleVisitorTest().run();
    }

    void run() throws Exception {
        String code = "class Test {\n" +
                      "    int t(int i) {\n" +
                      "         return switch(i) {\n" +
                      "              default: yield -1;\n" +
                      "         }\n" +
                      "    }\n" +
                      "}\n";
        int[] callCount = new int[1];
        int[] switchExprNodeCount = new int[1];
        int[] yieldNodeCount = new int[1];
        new TreePathScanner<Void, Void>() {
            @Override
            public Void visitSwitchExpression(SwitchExpressionTree node, Void p) {
                node.accept(new SimpleTreeVisitor<Void, Void>() {
                    @Override
                    protected Void defaultAction(Tree defaultActionNode, Void p) {
                        callCount[0]++;
                        if (node == defaultActionNode) {
                            switchExprNodeCount[0]++;
                        }
                        return null;
                    }
                }, null);
                return super.visitSwitchExpression(node, p);
            }
            @Override
            public Void visitYield(YieldTree node, Void p) {
                node.accept(new SimpleTreeVisitor<Void, Void>() {
                    @Override
                    protected Void defaultAction(Tree defaultActionNode, Void p) {
                        callCount[0]++;
                        if (node == defaultActionNode) {
                            yieldNodeCount[0]++;
                        }
                        return null;
                    }
                }, null);
                return super.visitYield(node, p);
            }
        }.scan(parse(code), null);

        if (callCount[0] != 2 || switchExprNodeCount[0] != 1 ||
            yieldNodeCount[0] != 1) {
            throw new AssertionError("Unexpected counts; callCount=" + callCount[0] +
                                     ", switchExprNodeCount=" + switchExprNodeCount[0] +
                                     ", yieldNodeCount=" + yieldNodeCount[0]);
        }
    }

    private CompilationUnitTree parse(String code) throws IOException {
        final JavaCompiler tool = ToolProvider.getSystemJavaCompiler();
        assert tool != null;
        DiagnosticListener<JavaFileObject> noErrors = d -> {};

        StringWriter out = new StringWriter();
        JavacTask ct = (JavacTask) tool.getTask(out, null, noErrors,
            List.of(), null,
            Arrays.asList(new MyFileObject(code)));
        return ct.parse().iterator().next();
    }

    static class MyFileObject extends SimpleJavaFileObject {
        private String text;

        public MyFileObject(String text) {
            super(URI.create("myfo:/Test.java"), JavaFileObject.Kind.SOURCE);
            this.text = text;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return text;
        }
    }
}

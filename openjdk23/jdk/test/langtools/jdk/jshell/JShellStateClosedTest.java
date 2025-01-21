/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test 8164277
 * @summary Testing IllegalStateException.
 * @build KullaTesting TestingInputStream JShellStateClosedTest
 * @run testng JShellStateClosedTest
 */

import java.util.function.Consumer;

import jdk.jshell.DeclarationSnippet;
import jdk.jshell.ImportSnippet;
import jdk.jshell.MethodSnippet;
import jdk.jshell.Snippet;
import jdk.jshell.TypeDeclSnippet;
import jdk.jshell.VarSnippet;
import org.testng.annotations.Test;

import static org.testng.Assert.fail;

@Test
public class JShellStateClosedTest extends KullaTesting {

    private void testStateClosedException(Runnable action) {
        getState().close();
        try {
            action.run();
            fail("Exception expected");
        } catch (IllegalStateException e) {
            // Expected
        }
    }

    public void testClasses() {
        TypeDeclSnippet sc = classKey(assertEval("class C { }"));
        TypeDeclSnippet si = classKey(assertEval("interface I { }"));
        getState().close();
        assertStreamMatch(getState().types(), sc, si);
    }

    public void testVariables() {
        VarSnippet sx = varKey(assertEval("int x = 5;"));
        VarSnippet sfoo = varKey(assertEval("String foo;"));
        getState().close();
        assertStreamMatch(getState().variables(), sx, sfoo);
    }

    public void testMethods() {
        MethodSnippet smm = methodKey(assertEval("int mm() { return 6; }"));
        MethodSnippet svv = methodKey(assertEval("void vv() { }"));
        getState().close();
        assertStreamMatch(getState().methods(), smm, svv);
    }

    public void testImports() {
        ImportSnippet simp = importKey(assertEval("import java.lang.reflect.*;"));
        getState().close();
        assertStreamMatch(getState().imports(), simp);
    }

    public void testSnippets() {
        VarSnippet sx = varKey(assertEval("int x = 5;"));
        VarSnippet sfoo = varKey(assertEval("String foo;"));
        MethodSnippet smm = methodKey(assertEval("int mm() { return 6; }"));
        MethodSnippet svv = methodKey(assertEval("void vv() { }"));
        TypeDeclSnippet sc = classKey(assertEval("class C { }"));
        TypeDeclSnippet si = classKey(assertEval("interface I { }"));
        ImportSnippet simp = importKey(assertEval("import java.lang.reflect.*;"));
        getState().close();
        assertStreamMatch(getState().snippets(), sx, sfoo, smm, svv, sc, si, simp);
    }

    public void testEval() {
        testStateClosedException(() -> getState().eval("int a;"));
    }

    private void testStateClosedException(Consumer<Snippet> action) {
        Snippet k = varKey(assertEval("int a;"));
        getState().close();
        try {
            action.accept(k);
            fail("IllegalStateException expected since closed");
        } catch (IllegalStateException e) {
            // Expected
        }
    }

    private void testStateClosedWithoutException(Consumer<Snippet> action) {
        Snippet k = varKey(assertEval("int a;"));
        getState().close();
        try {
            action.accept(k);
        } catch (IllegalStateException e) {
            fail("Expected no IllegalStateException even though closed");
        }
    }

    public void testStatus() {
        testStateClosedWithoutException((key) -> getState().status(key));
    }

    public void testVarValue() {
        testStateClosedException((key) -> getState().varValue((VarSnippet) key));
    }

    public void testDrop() {
        testStateClosedException((key) -> getState().drop(key));
    }

    public void testUnresolved() {
        testStateClosedWithoutException((key) -> getState().unresolvedDependencies((DeclarationSnippet) key));
    }

    public void testDiagnostics() {
        testStateClosedWithoutException((key) -> getState().diagnostics(key));
    }
}

/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @summary Testing IllegalArgumentException.
 * @build KullaTesting TestingInputStream IllegalArgumentExceptionTest
 * @run testng IllegalArgumentExceptionTest
 */

import java.util.function.Consumer;

import jdk.jshell.DeclarationSnippet;
import jdk.jshell.Snippet;
import jdk.jshell.VarSnippet;
import org.testng.annotations.Test;

import static org.testng.Assert.fail;
import static jdk.jshell.Snippet.Status.VALID;

@Test
public class IllegalArgumentExceptionTest extends KullaTesting {

    private void testIllegalArgumentException(Consumer<Snippet> action) {
        Snippet key = varKey(assertEval("int value;", added(VALID)));
        tearDown();
        setUp();
        assertEval("double value;");
        try {
            action.accept(key);
            fail("Exception expected.");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    public void testVarValue() {
        testIllegalArgumentException((key) -> getState().varValue((VarSnippet) key));
    }

    public void testStatus() {
        testIllegalArgumentException((key) -> getState().status(key));
    }

    public void testDrop() {
        testIllegalArgumentException((key) -> getState().drop(key));
    }

    public void testUnresolved() {
        testIllegalArgumentException((key) -> getState().unresolvedDependencies((DeclarationSnippet) key));
    }

    public void testDiagnostics() {
        testIllegalArgumentException((key) -> getState().diagnostics(key));
    }
}

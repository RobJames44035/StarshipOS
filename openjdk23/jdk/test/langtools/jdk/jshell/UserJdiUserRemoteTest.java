/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

 /*
 * @test
 * @bug 8160128 8159935 8168615
 * @summary Tests for Aux channel, custom remote agents, custom JDI implementations.
 * @build KullaTesting ExecutionControlTestBase MyExecutionControl MyRemoteExecutionControl MyExecutionControlProvider
 * @run testng UserJdiUserRemoteTest
 * @key intermittent
 */
import java.io.ByteArrayOutputStream;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import jdk.jshell.Snippet;
import static jdk.jshell.Snippet.Status.OVERWRITTEN;
import static jdk.jshell.Snippet.Status.VALID;
import jdk.jshell.VarSnippet;
import jdk.jshell.spi.ExecutionControl;
import jdk.jshell.spi.ExecutionControl.ExecutionControlException;
import static org.testng.Assert.assertEquals;

@Test
public class UserJdiUserRemoteTest extends ExecutionControlTestBase {

    ExecutionControl currentEC;
    ByteArrayOutputStream auxStream;

    @BeforeMethod
    @Override
    public void setUp() {
        auxStream = new ByteArrayOutputStream();
        setUp(builder -> builder.executionEngine(new MyExecutionControlProvider(this), null));
    }

    public void testVarValue() {
        VarSnippet dv = varKey(assertEval("double aDouble = 1.5;"));
        String vd = getState().varValue(dv);
        assertEquals(vd, "1.5");
        assertEquals(auxStream.toString(), "aDouble");
    }

    public void testExtension() throws ExecutionControlException {
        assertEval("42;");
        Object res = currentEC.extensionCommand("FROG", "test");
        assertEquals(res, "ribbit");
    }

    public void testRedefine() {
        Snippet vx = varKey(assertEval("int x;"));
        Snippet mu = methodKey(assertEval("int mu() { return x * 4; }"));
        Snippet c = classKey(assertEval("class C { String v() { return \"#\" + mu(); } }"));
        assertEval("C c0  = new C();");
        assertEval("c0.v();", "\"#0\"");
        assertEval("int x = 10;", "10",
                ste(MAIN_SNIPPET, VALID, VALID, false, null),
                ste(vx, VALID, OVERWRITTEN, false, MAIN_SNIPPET));
        assertEval("c0.v();", "\"#40\"");
        assertEval("C c = new C();");
        assertEval("c.v();", "\"#40\"");
        assertEval("int mu() { return x * 3; }",
                ste(MAIN_SNIPPET, VALID, VALID, false, null),
                ste(mu, VALID, OVERWRITTEN, false, MAIN_SNIPPET));
        assertEval("c.v();", "\"#30\"");
        assertEval("class C { String v() { return \"@\" + mu(); } }",
                ste(MAIN_SNIPPET, VALID, VALID, false, null),
                ste(c, VALID, OVERWRITTEN, false, MAIN_SNIPPET));
        assertEval("c0.v();", "\"@30\"");
        assertEval("c = new C();");
        assertEval("c.v();", "\"@30\"");
        assertActiveKeys();
    }
}

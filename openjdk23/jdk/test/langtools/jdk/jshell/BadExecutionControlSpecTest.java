/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8168615
 * @summary Test bad input to ExecutionControl.generate
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 *          jdk.jdeps/com.sun.tools.javap
 *          jdk.jshell/jdk.internal.jshell.tool
 * @run testng BadExecutionControlSpecTest
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.List;
import org.testng.annotations.Test;
import jdk.jshell.spi.ExecutionControl;
import jdk.jshell.spi.ExecutionEnv;
import static org.testng.Assert.fail;

@Test
public class BadExecutionControlSpecTest {
    private static void assertIllegal(String spec) throws Throwable {
        try {
            ExecutionEnv env = new ExecutionEnv() {
                @Override
                public InputStream userIn() {
                    return new ByteArrayInputStream(new byte[0]);
                }

                @Override
                public PrintStream userOut() {
                    return new PrintStream(new ByteArrayOutputStream());
                }

                @Override
                public PrintStream userErr() {
                    return new PrintStream(new ByteArrayOutputStream());
                }

                @Override
                public List<String> extraRemoteVMOptions() {
                    return Collections.emptyList();
                }

                @Override
                public void closeDown() {
                }

            };
            ExecutionControl.generate(env, spec);
            fail("Expected exception -- " + spec);
        } catch (IllegalArgumentException ex) {
            // The expected happened
        }
    }

    public void syntaxTest() throws Throwable {
        assertIllegal(":launch(true)");
        assertIllegal("jdi:launch(true");
        assertIllegal("jdi:launch(true)$");
        assertIllegal("jdi:,");
    }

    public void notFoundTest() throws Throwable {
        assertIllegal("fruitbats");
        assertIllegal("jdi:baz(true)");
        assertIllegal("random:launch(true)");
        assertIllegal("jdi:,");
    }
}

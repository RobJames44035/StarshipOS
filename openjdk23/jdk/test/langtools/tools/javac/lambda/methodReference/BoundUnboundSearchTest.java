/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8231461
 * @summary static/instance overload leads to 'unexpected static method found in unbound lookup' when resolving method reference
 * @library /lib/combo /tools/lib /tools/javac/lib
 * @modules
 *      jdk.compiler/com.sun.tools.javac.api
 *      jdk.compiler/com.sun.tools.javac.util
 * @run junit BoundUnboundSearchTest
 */

import java.util.function.*;

import javax.tools.Diagnostic;

import com.sun.tools.javac.api.ClientCodeWrapper.DiagnosticSourceUnwrapper;
import com.sun.tools.javac.util.Assert;
import com.sun.tools.javac.util.JCDiagnostic;

import org.junit.jupiter.api.Test;
import tools.javac.combo.CompilationTestCase;

class BoundUnboundSearchTest extends CompilationTestCase {
    static final String TEMPLATE =
            """
            import java.util.function.*;
            class Test {
                #CANDIDATES
                void m() {
                    Function<String, String> f = Test::foo;
                }
            }
            """;

    BoundUnboundSearchTest() {
        setDefaultFilename("Test.java");
        setCompileOptions(new String[]{"--debug=dumpMethodReferenceSearchResults"});
    }

    private Consumer<Diagnostic<?>> getDiagConsumer(final int boundCandidate, final int unboundCandidate) {
        return diagWrapper -> {
            JCDiagnostic diagnostic = ((DiagnosticSourceUnwrapper)diagWrapper).d;
            Object[] args = diagnostic.getArgs();
            if (args[0].toString().equals("bound")) {
                Assert.check(args[2].equals(boundCandidate));
            } else if (args[0].toString().equals("unbound")) {
                Assert.check(args[2].equals(unboundCandidate));
            }
        };
    }

    @Test
    void test() {
        assertOK(
            getDiagConsumer(0, -1),
                TEMPLATE.replaceFirst("#CANDIDATES",
                    """
                    public String foo(Object o) { return "foo"; }           // candidate 0
                    public static String foo(String o) { return "bar"; }    // candidate 1
                    """
            )
        );

        assertOK(
                getDiagConsumer(0, -1),
                TEMPLATE.replaceFirst("#CANDIDATES",
                    """
                    public static String foo(Object o) { return "foo"; }    // candidate 0
                    public static String foo(String o) { return "bar"; }    // candidate 0
                    """
                )
        );

        assertFail("compiler.err.prob.found.req",
                getDiagConsumer(0, -1),
                TEMPLATE.replaceFirst("#CANDIDATES",
                    """
                    public static String foo(Object o) { return "foo"; }    // candidate 0
                    public String foo(String o) { return "bar"; }           // candidate 1
                    """
                )
        );

        assertFail("compiler.err.prob.found.req",
                getDiagConsumer(0, -1),
                TEMPLATE.replaceFirst("#CANDIDATES",
                    """
                    public String foo(Object o) { return "foo"; }           // candidate 0
                    public String foo(String o) { return "bar"; }           // candidate 1
                    """
                )
        );

        assertFail("compiler.err.invalid.mref",
                getDiagConsumer(-1, -1),
                """
                import java.util.function.*;

                public class Test {
                    public String foo(Object o) { return "foo"; }
                    public static String foo(String o) { return "bar"; }

                    public void test() {
                        // method bar doesn't exist
                        Function<String, String> f = Test::bar;
                    }
                }
                """
        );

        assertOK(
                getDiagConsumer(0, -1),
                """
                import java.util.function.*;

                interface Intf {
                    Object apply(String... args);
                }

                public class Test {
                    public static Object foo(Object o) { return "bar"; }
                    public final Object foo(Object... o) { return "foo"; }

                    public void test() {
                        Intf f = this::foo;
                    }
                }
                """
        );
    }
}

/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8243047
 * @summary javac should not crash while processing exits in class initializers in Flow
 * @library /tools/lib /tools/javac/lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.file
 *          jdk.compiler/com.sun.tools.javac.main
 *          jdk.compiler/com.sun.tools.javac.util
 * @build toolbox.ToolBox toolbox.JavacTask
 * @build combo.ComboTestHelper
 * @compile ClassBlockExits.java
 * @run main ClassBlockExits
 */

import combo.ComboInstance;
import combo.ComboParameter;
import combo.ComboTask;
import combo.ComboTestHelper;
import java.io.StringWriter;
import toolbox.ToolBox;

public class ClassBlockExits extends ComboInstance<ClassBlockExits> {
    protected ToolBox tb;

    ClassBlockExits() {
        super();
        tb = new ToolBox();
    }

    public static void main(String... args) throws Exception {
        new ComboTestHelper<ClassBlockExits>()
                .withDimension("BLOCK", (x, block) -> x.block = block, Block.values())
                .withDimension("EXIT", (x, exit) -> x.exit = exit, Exit.values())
                .run(ClassBlockExits::new);
    }

    private Block block;
    private Exit exit;

    private static final String MAIN_TEMPLATE =
            """
            public class Test {
                #{BLOCK}
                void t() {}
            }
            """;

    @Override
    protected void doWork() throws Throwable {
        StringWriter out = new StringWriter();

        ComboTask task = newCompilationTask()
                .withSourceFromTemplate(MAIN_TEMPLATE, pname -> switch (pname) {
                        case "BLOCK" -> block;
                        case "BODY" -> exit;
                        default -> throw new UnsupportedOperationException(pname);
                    })
                .withOption("-XDshould-stop.at=FLOW")
                .withOption("-XDdev")
                .withWriter(out);

        task.analyze(result -> {
            if (out.toString().length() > 0) {
                throw new AssertionError("No output expected, but got" + out + "\n\n" + result.compilationInfo());
            }
        });
    }

    public enum Block implements ComboParameter {
        STATIC("""
               static {
                   #{BODY}
               }
               """),
        INSTANCE("""
                 {
                     #{BODY}
                 }
                 """),
        STATIC_INITIALIZER("""
                    private static int i = switch (0) { default: #{BODY} case 0: yield 0; };
                    """),
        INITIALIZER("""
                    private int i = switch (0) { default: #{BODY} case 0: yield 0; };
                    """);
        private final String block;

        private Block(String block) {
            this.block = block;
        }

        @Override
        public String expand(String optParameter) {
            return block;
        }
    }

    public enum Exit implements ComboParameter {
        RETURN("return;"),
        RETURN_VALUE("return null;"),
        BREAK("break;"),
        BREAK_LABEL("break LABEL;"),
        CONTINUE("continue;"),
        CONTINUE_LABEL("continue LABEL;"),
        YIELD("yield 0;");
        private final String code;

        private Exit(String code) {
            this.code = code;
        }

        @Override
        public String expand(String optParameter) {
            return code;
        }
    }
}
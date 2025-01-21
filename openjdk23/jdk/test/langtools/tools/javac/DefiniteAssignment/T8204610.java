/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8204610
 * @summary Compiler confused by parenthesized "this" in final fields assignments
 * @library /tools/javac/lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.file
 *          jdk.compiler/com.sun.tools.javac.util
 * @build combo.ComboTestHelper

 * @run main T8204610
 */

import combo.ComboInstance;
import combo.ComboParameter;
import combo.ComboTask.Result;
import combo.ComboTestHelper;

public class T8204610 extends ComboInstance<T8204610> {

    enum ParenKind implements ComboParameter {
        NONE(""),
        ONE("#P"),
        TWO("#P#P"),
        THREE("#P#P#P");

        String parensTemplate;

        ParenKind(String parensTemplate) {
            this.parensTemplate = parensTemplate;
        }

        @Override
        public String expand(String optParameter) {
            return parensTemplate.replaceAll("#P", optParameter.equals("OPEN") ? "(" : ")");
        }
    }

    public static void main(String... args) {
        new ComboTestHelper<T8204610>()
                .withArrayDimension("PAREN", (x, pk, idx) -> x.parenKinds[idx] = pk, 3, ParenKind.values())
                .run(T8204610::new);
    }

    ParenKind[] parenKinds = new ParenKind[3];

    @Override
    public void doWork() {
        newCompilationTask()
                .withSourceFromTemplate(bodyTemplate)
                .analyze(this::check);
    }

    String bodyTemplate = "class Test {\n" +
                          "   final int x;\n" +
                          "   Test() {\n" +
                          "      #{PAREN[0].OPEN} #{PAREN[1].OPEN} this #{PAREN[1].CLOSE} . #{PAREN[2].OPEN} x #{PAREN[2].CLOSE} #{PAREN[0].CLOSE} = 1;\n" +
                          "   } }";

    void check(Result<?> res) {
        boolean expectedFail = parenKinds[2] != ParenKind.NONE;
        if (expectedFail != res.hasErrors()) {
            fail("unexpected compilation result for source:\n" +
                res.compilationInfo());
        }
    }
}

/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @key randomness
 * @bug 8137167
 * @summary Stress directive json parser
 * @modules java.base/jdk.internal.misc
 * @library /test/lib /
 *
 * @run driver compiler.compilercontrol.parser.DirectiveStressTest
 */

package compiler.compilercontrol.parser;

import compiler.compilercontrol.share.AbstractTestBase;
import compiler.compilercontrol.share.JSONFile;
import compiler.compilercontrol.share.method.MethodDescriptor;
import compiler.compilercontrol.share.pool.PoolHelper;
import compiler.compilercontrol.share.scenario.DirectiveWriter;
import jdk.test.lib.process.OutputAnalyzer;

import java.util.List;
import java.util.stream.Collectors;

public class DirectiveStressTest {
    private static final int AMOUNT = Integer.getInteger(
            "compiler.compilercontrol.parser.DirectiveStressTest.amount",
            999);
    private static final List<MethodDescriptor> DESCRIPTORS
            = new PoolHelper().getAllMethods().stream()
                    .map(pair -> AbstractTestBase.getValidMethodDescriptor(
                            pair.first))
                    .collect(Collectors.toList());
    private static final String EXPECTED_MESSAGE = " compiler directives added";

    public static void main(String[] args) {
        hugeFileTest();
        hugeObjectTest();
    }

    /*
     * Creates file with AMOUNT of options in match block
     */
    private static void hugeObjectTest() {
        String fileName = "hugeObject.json";
        try (DirectiveWriter file = new DirectiveWriter(fileName)) {
            file.write(JSONFile.Element.ARRAY);
            HugeDirectiveUtil.createMatchObject(DESCRIPTORS, file, AMOUNT);
            file.end(); // end array block
        }
        OutputAnalyzer output = HugeDirectiveUtil.execute(fileName);
        output.shouldHaveExitValue(0);
        output.shouldContain(1 + EXPECTED_MESSAGE);
        output.shouldNotContain(HugeDirectiveUtil.EXPECTED_ERROR_STRING);
    }

    /*
     * Creates huge valid file with AMOUNT of match directives
     */
    private static void hugeFileTest() {
        String fileName = "hugeFile.json";
        HugeDirectiveUtil.createHugeFile(DESCRIPTORS, fileName, AMOUNT);
        OutputAnalyzer output = HugeDirectiveUtil.execute(fileName);
        output.shouldHaveExitValue(0);
        output.shouldContain(AMOUNT + EXPECTED_MESSAGE);
        output.shouldNotContain(HugeDirectiveUtil.EXPECTED_ERROR_STRING);
    }
}

/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8339694
 * @summary Test compilation of unresolved constant dynamics.
 * @library /test/lib
 * @compile TestUnresolvedConstantDynamicHelper.jasm
 * @run driver TestUnresolvedConstantDynamic
 * @run main/othervm -Xcomp -XX:-TieredCompilation -XX:CompileCommand=compileonly,TestUnresolvedConstantDynamicHelper::test* TestUnresolvedConstantDynamic
 */

import jdk.test.lib.Asserts;

public class TestUnresolvedConstantDynamic {

    public static void main(String[] args) {
        Asserts.assertEquals(TestUnresolvedConstantDynamicHelper.testBooleanArray(true)[0], true);
        Asserts.assertEquals(TestUnresolvedConstantDynamicHelper.testStringArray("42")[0], "42");
    }
}

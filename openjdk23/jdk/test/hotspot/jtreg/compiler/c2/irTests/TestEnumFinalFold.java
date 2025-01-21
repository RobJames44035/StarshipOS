/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.c2.irTests;

import jdk.test.lib.Asserts;
import compiler.lib.ir_framework.*;

/*
 * @test
 * @bug 8286190
 * @summary Verify constant folding for Enum fields
 * @library /test/lib /
 * @requires vm.compiler2.enabled
 * @run driver compiler.c2.irTests.TestEnumFinalFold
 */
public class TestEnumFinalFold {

    public static void main(String[] args) {
        TestFramework.runWithFlags(
            "-XX:+UnlockExperimentalVMOptions",
            "-XX:CompileCommand=inline,java.lang.Enum::name",
            "-XX:CompileCommand=inline,java.lang.Enum::ordinal",
            "-XX:CompileCommand=inline,java.lang.String::length"
        );
    }

    private enum MyEnum {
        VALUE1,
        VALUE2;
    }

    @Test
    @IR(failOn = {IRNode.ADD_I, IRNode.LOAD_I})
    public int testOrdinalSum() {
        return MyEnum.VALUE1.ordinal() + MyEnum.VALUE2.ordinal();
    }

    @Run(test = "testOrdinalSum")
    public void runOrdinalSum() {
        testOrdinalSum();
    }

    @Test
    @IR(failOn = {IRNode.ADD_I})
    public int testNameLengthSum() {
        return MyEnum.VALUE1.name().length() + MyEnum.VALUE2.name().length();
    }

    @Run(test = "testNameLengthSum")
    public void runNameLengthSum() {
        testNameLengthSum();
    }

}

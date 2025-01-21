/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8031321
 * @requires vm.flavor == "server" & !vm.emulatedClient & !vm.graal.enabled
 * @library /test/lib /
 * @modules java.base/jdk.internal.misc
 *          java.management
 *
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -Xbatch -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI
 *      -XX:+IgnoreUnrecognizedVMOptions -XX:+UseCountLeadingZerosInstruction
 *      compiler.intrinsics.bmi.verifycode.LZcntTestI
 */

package compiler.intrinsics.bmi.verifycode;

import compiler.intrinsics.bmi.TestLzcntI;

import java.lang.reflect.Method;

public class LZcntTestI extends BmiIntrinsicBase.BmiTestCase_x64 {

    protected LZcntTestI(Method method) {
        super(method);
        instrMask = new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
        instrPattern = new byte[]{(byte) 0xF3, (byte) 0x0F, (byte) 0xBD};

        instrMask_x64 = new byte[]{(byte) 0xFF, (byte) 0x00, (byte) 0xFF, (byte) 0xFF};
        instrPattern_x64 = new byte[]{(byte) 0xF3, (byte) 0x00, (byte) 0x0F, (byte) 0xBD};
    }

    public static void main(String[] args) throws Exception {
        // j.l.Integer and Long should be loaded to allow a compilation of the methods that use their methods
        System.out.println("class java.lang.Integer should be loaded. Proof: " + Integer.class);
        // Avoid uncommon traps.
        System.out.println("Num leading zeroes: " + new TestLzcntI.LzcntIExpr().intExpr(12341341));
        BmiIntrinsicBase.verifyTestCase(LZcntTestI::new, TestLzcntI.LzcntIExpr.class.getDeclaredMethods());
    }

    @Override
    protected String getVMFlag() {
        return "UseCountLeadingZerosInstruction";
    }

    @Override
    protected String getCpuFlag() {
        return "lzcnt";
    }
}

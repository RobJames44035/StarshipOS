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
 *      -XX:+IgnoreUnrecognizedVMOptions -XX:+UseBMI1Instructions
 *      compiler.intrinsics.bmi.verifycode.BlsiTestL
 */

package compiler.intrinsics.bmi.verifycode;

import compiler.intrinsics.bmi.TestBlsiL;

import java.lang.reflect.Method;

public class BlsiTestL extends BlsiTestI {

    protected BlsiTestL(Method method) {
        super(method);
        isLongOperation = true;
    }

    public static void main(String[] args) throws Exception {
        BmiIntrinsicBase.verifyTestCase(BlsiTestL::new, TestBlsiL.BlsiLExpr.class.getDeclaredMethods());
        BmiIntrinsicBase.verifyTestCase(BlsiTestL::new, TestBlsiL.BlsiLCommutativeExpr.class.getDeclaredMethods());
    }
}

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
 *      compiler.intrinsics.bmi.verifycode.BlsmskTestL
 */

package compiler.intrinsics.bmi.verifycode;

import compiler.intrinsics.bmi.TestBlsmskL;

import java.lang.reflect.Method;

public class BlsmskTestL extends BlsmskTestI {

    protected BlsmskTestL(Method method) {
        super(method);
        isLongOperation = true;
    }

    public static void main(String[] args) throws Exception {
        BmiIntrinsicBase.verifyTestCase(BlsmskTestL::new, TestBlsmskL.BlsmskLExpr.class.getDeclaredMethods());
        BmiIntrinsicBase.verifyTestCase(BlsmskTestL::new, TestBlsmskL.BlsmskLCommutativeExpr.class.getDeclaredMethods());
    }
}

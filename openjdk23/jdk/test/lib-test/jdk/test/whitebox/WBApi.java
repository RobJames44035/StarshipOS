/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test WBApi
 * @summary verify that whitebox functions can be linked and executed
 * @modules java.base/jdk.internal.misc
 * @library /test/lib
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI WBApi
 */

import jdk.test.whitebox.WhiteBox;
public class WBApi {
    public static void main(String... args) {
        System.out.printf("args at: %x\n",WhiteBox.getWhiteBox().getObjectAddress(args));
    }
}

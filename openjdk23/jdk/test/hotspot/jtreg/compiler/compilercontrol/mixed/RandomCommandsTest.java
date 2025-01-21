/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @key randomness
 * @bug 8137167
 * @summary Randomly generates commands with random types
 * @modules java.base/jdk.internal.misc
 * @library /test/lib /
 *
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run driver/timeout=1200 compiler.compilercontrol.mixed.RandomCommandsTest
 */

package compiler.compilercontrol.mixed;

import compiler.compilercontrol.share.MultiCommand;

public class RandomCommandsTest {
    public static void main(String[] args) {
        MultiCommand.generateRandomTest(false).test();
    }
}

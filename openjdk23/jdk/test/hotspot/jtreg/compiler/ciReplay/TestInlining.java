/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * @test
 * @bug 8270925
 * @library / /test/lib
 * @summary testing of ciReplay with inlining
 * @requires vm.flightRecorder != true & vm.compMode != "Xint" & vm.debug == true & vm.compiler2.enabled
 * @modules java.base/jdk.internal.misc
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI
 *      compiler.ciReplay.TestInlining
 */

package compiler.ciReplay;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.List;
import java.util.StringTokenizer;
import jdk.test.lib.Asserts;

public class TestInlining extends CiReplayBase {
    public static void main(String args[]) {
        new TestInlining().runTest(false, TIERED_DISABLED_VM_OPTION);
    }

    @Override
    public void testAction() {
        positiveTest(TIERED_DISABLED_VM_OPTION);
        try {
            Path replayFilePath = Paths.get(REPLAY_FILE_NAME);
            List<String> replayContent = Files.readAllLines(replayFilePath);
            boolean found = false;
            for (int i = 0; i < replayContent.size(); i++) {
                String line = replayContent.get(i);
                if (line.startsWith("compile ")) {
                    StringTokenizer tokenizer = new StringTokenizer(line, " ");
                    Asserts.assertEQ(tokenizer.nextToken(), "compile");
                    tokenizer.nextToken(); // class
                    tokenizer.nextToken(); // method
                    tokenizer.nextToken(); // signature
                    tokenizer.nextToken(); // bci
                    Asserts.assertEQ(tokenizer.nextToken(), "4"); // level
                    Asserts.assertEQ(tokenizer.nextToken(), "inline");
                    found = true;
                }
            }
            Asserts.assertEQ(found, true);
        } catch (IOException ioe) {
            throw new Error("Failed to read/write replay data: " + ioe, ioe);
        }
    }
}


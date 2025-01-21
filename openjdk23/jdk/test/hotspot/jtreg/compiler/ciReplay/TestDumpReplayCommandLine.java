/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8276044
 * @library / /test/lib
 * @summary Testing that a replay file is dumped for C1 and C2 when using the DumpReplay compile command option.
 * @requires vm.flightRecorder != true & vm.compMode != "Xint" & vm.compMode != "Xcomp" & vm.debug == true
 *           & vm.compiler1.enabled & vm.compiler2.enabled
 * @modules java.base/jdk.internal.misc
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run main/othervm -Xbootclasspath/a:. -XX:+UnlockDiagnosticVMOptions -XX:+WhiteBoxAPI -XX:+TieredCompilation
 *      compiler.ciReplay.TestDumpReplayCommandLine
 */

package compiler.ciReplay;

import jdk.test.lib.Asserts;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestDumpReplayCommandLine extends DumpReplayBase {

    public static void main(String[] args) {
        new TestDumpReplayCommandLine().runTest(TIERED_ENABLED_VM_OPTION);
    }

    @Override
    public void testAction() {
        List<File> replayFiles = getReplayFiles();
        Asserts.assertEQ(2, replayFiles.size(), "should find a C1 and a C2 replay file");
        String replayFile1 = replayFiles.get(0).getName();
        String replayFile2 = replayFiles.get(1).getName();
        int compileId1 = getCompileIdFromFile(replayFile1);
        int compileId2 = getCompileIdFromFile(replayFile2);
        int compLevel1 = getCompLevelFromReplay(replayFile1);
        int compLevel2 = getCompLevelFromReplay(replayFile2);
        Asserts.assertEQ(compileId1 < compileId2 ? compLevel1 : compLevel2, 3, "Must be C1 replay file");
        Asserts.assertEQ(compileId1 < compileId2 ? compLevel2 : compLevel1, 4, "Must be C2 replay file");
    }

    @Override
    public String getTestClass() {
        return TestDumpReplayCommandFoo.class.getName();
    }
}

class TestDumpReplayCommandFoo {
    public static int iFld;

    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            test();
        }
    }

    public static void test() {
        for (int i = 0; i < 1; i++) {
            iFld = 3;
        }
    }
}

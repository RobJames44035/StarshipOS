/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 7170638
 * @summary Test SDT probes available on GNU/Linux when DTRACE_ENABLED
 * @requires os.family == "linux"
 * @requires vm.flagless
 * @requires vm.hasDTrace
 *
 * @library /test/lib
 * @run driver SDTProbesGNULinuxTest
 */

import jdk.test.lib.Utils;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SDTProbesGNULinuxTest {
    public static void main(String[] args) throws Throwable {
        // This test only matters when build with DTRACE_ENABLED.
        try (var libjvms = Files.walk(Paths.get(Utils.TEST_JDK))) {
            libjvms.filter(p -> "libjvm.so".equals(p.getFileName().toString()))
                   .map(Path::toAbsolutePath)
                   .forEach(SDTProbesGNULinuxTest::testLibJvm);
        }
    }

    private static void testLibJvm(Path libjvm) {
        System.out.println("Testing " + libjvm);
        // We could iterate over all SDT probes and test them individually
        // with readelf -n, but older readelf versions don't understand them.
        try {
            ProcessTools.executeCommand("readelf", "-S", libjvm.toString())
                        .shouldHaveExitValue(0)
                        .stdoutShouldContain(".note.stapsd");
        } catch (Throwable t) {
            throw new Error(t);
        }
    }
}

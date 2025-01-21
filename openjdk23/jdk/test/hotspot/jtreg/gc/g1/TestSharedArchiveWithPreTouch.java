/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package gc.g1;

/**
 * @test
 * @bug 8169703
 * @summary Verifies that dumping and loading a CDS archive succeeds with AlwaysPreTouch
 * @requires vm.gc.G1
 * @requires vm.cds
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.management
 * @run driver gc.g1.TestSharedArchiveWithPreTouch
 */

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import jdk.test.lib.Platform;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;

public class TestSharedArchiveWithPreTouch {
    public static void main(String[] args) throws Exception {
        final String ArchiveFileName = "./SharedArchiveWithPreTouch.jsa";

        final List<String> BaseOptions = Arrays.asList(new String[] {"-XX:+UseG1GC", "-XX:+AlwaysPreTouch",
            "-XX:+UnlockDiagnosticVMOptions", "-XX:SharedArchiveFile=" + ArchiveFileName });

        List<String> dump_args = new ArrayList<String>(BaseOptions);

        if (Platform.is64bit()) {
          dump_args.addAll(0, Arrays.asList(new String[] { "-XX:+UseCompressedClassPointers", "-XX:+UseCompressedOops" }));
        }
        dump_args.addAll(Arrays.asList(new String[] { "-Xshare:dump", "-Xlog:cds" }));

        OutputAnalyzer output = ProcessTools.executeLimitedTestJava(dump_args);

        try {
            output.shouldContain("Loading classes to share");
            output.shouldHaveExitValue(0);

            List<String> load_args = new ArrayList<String>(BaseOptions);

            if (Platform.is64bit()) {
                load_args.addAll(0, Arrays.asList(new String[] { "-XX:+UseCompressedClassPointers", "-XX:+UseCompressedOops" }));
            }
            load_args.addAll(Arrays.asList(new String[] { "-Xshare:on", "-version" }));

            output = ProcessTools.executeLimitedTestJava(load_args.toArray(new String[0]));
            output.shouldContain("sharing");
            output.shouldHaveExitValue(0);
        } catch (RuntimeException e) {
            // Report 'passed' if CDS was turned off.
            output.shouldContain("Unable to use shared archive");
            output.shouldHaveExitValue(1);
        }
    }
}

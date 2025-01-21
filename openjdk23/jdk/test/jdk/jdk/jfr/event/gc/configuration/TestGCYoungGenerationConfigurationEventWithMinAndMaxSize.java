/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.event.gc.configuration;

import jdk.jfr.consumer.RecordedEvent;
import jdk.test.lib.jfr.EventVerifier;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @run driver jdk.jfr.event.gc.configuration.TestGCYoungGenerationConfigurationEventWithMinAndMaxSize
 */
public class TestGCYoungGenerationConfigurationEventWithMinAndMaxSize {
    public static void main(String[] args) throws Exception {
        String[] jvm_args = {"-XX:+UnlockExperimentalVMOptions",
                             "-XX:-UseFastUnorderedTimeStamps",
                             "-XX:NewSize=12m",
                             "-cp",
                             System.getProperty("java.class.path"),
                             "-XX:MaxNewSize=16m",
                             "-Xms32m",
                             "-Xmx64m",
                             Tester.class.getName()};
        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(jvm_args);
        OutputAnalyzer analyzer = ProcessTools.executeProcess(pb);
        analyzer.shouldHaveExitValue(0);
    }
}

class Tester extends GCYoungGenerationConfigurationEventTester {
    public static void main(String[] args) throws Exception {
        new Tester().run();
    }

    @Override protected EventVerifier createVerifier(RecordedEvent e) {
        return new MinAndMaxSizeVerifier(e);
    }
}

class MinAndMaxSizeVerifier extends EventVerifier {
    public MinAndMaxSizeVerifier(RecordedEvent e) {
        super(e);
    }

    @Override public void verify() throws Exception {
        verifyMinSizeIs(megabytes(12));
        verifyMaxSizeIs(megabytes(16));

        // Can't test newRatio at the same time as minSize and maxSize,
        // because the NewRatio flag can't be set when the flags NewSize and
        // MaxNewSize are set.
    }

    private void verifyMinSizeIs(long expected) throws Exception {
        verifyEquals("minSize", expected);
    }

    private void verifyMaxSizeIs(long expected) throws Exception {
        verifyEquals("maxSize", expected);
    }
}

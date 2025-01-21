/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.event.gc.configuration;

import jdk.jfr.consumer.RecordedEvent;
import jdk.test.lib.jfr.EventVerifier;

/**
 * @test
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib /test/jdk
 * @run main/othervm -XX:+UnlockExperimentalVMOptions -XX:-UseFastUnorderedTimeStamps -XX:NewRatio=4 jdk.jfr.event.gc.configuration.TestGCYoungGenerationConfigurationEventWithNewRatio
 */
public class TestGCYoungGenerationConfigurationEventWithNewRatio
    extends GCYoungGenerationConfigurationEventTester {
    public static void main(String[] args) throws Exception {
        GCYoungGenerationConfigurationEventTester t = new TestGCYoungGenerationConfigurationEventWithNewRatio();
        t.run();
    }

    @Override protected EventVerifier createVerifier(RecordedEvent e) {
        return new NewRatioVerifier(e);
    }
}

class NewRatioVerifier extends EventVerifier {
    public NewRatioVerifier(RecordedEvent event) {
        super(event);
    }

    @Override public void verify() throws Exception {
        verifyNewRatioIs(4);

        // Can't test minSize and maxSize at the same time as newRatio,
        // because the NewSize and MaxNewSize flags can't be set when the flag
        // MaxNewSize is set.
    }

    private void verifyNewRatioIs(int expected) throws Exception {
        verifyEquals("newRatio", expected);
    }
}

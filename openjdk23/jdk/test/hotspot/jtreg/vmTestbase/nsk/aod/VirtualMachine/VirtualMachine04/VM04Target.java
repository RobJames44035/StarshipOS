/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

package nsk.aod.VirtualMachine.VirtualMachine04;

import nsk.share.TestBug;
import nsk.share.aod.DummyTargetApplication;

/*
 * This target application during initialization sets special system property,
 * main test application tries to get this property using VirtualMachine.getSystemProperties()
 */
public class VM04Target extends DummyTargetApplication {
    static final String TEST_PROPERTY_KEY = "VirtualMachine04_testPropertyKey";
    static final String TEST_PROPERTY_VALUE = "VirtualMachine04_testPropertyValue";
    static final String CHANGED_TEST_PROPERTY_VALUE = "VirtualMachine04_testPropertyValue_changed";

    VM04Target(String[] args) {
        super(args);

        log.display("Setting property " + TEST_PROPERTY_KEY + " = " + TEST_PROPERTY_VALUE);
        System.setProperty(TEST_PROPERTY_KEY, TEST_PROPERTY_VALUE);
    }

    protected void targetApplicationActions() {
        String signal = pipe.readln();
        log.display("Received signal: " + signal);
        if (!signal.equals(VirtualMachine04.SIGNAL_CHANGE_PROPERTY)) {
            throw new TestBug("Received unexpected signal: " + signal);
        }

        log.display("Setting property " + TEST_PROPERTY_KEY + " = " + CHANGED_TEST_PROPERTY_VALUE);
        System.setProperty(TEST_PROPERTY_KEY, CHANGED_TEST_PROPERTY_VALUE);

        log.display("Sending signal " + VirtualMachine04.SIGNAL_PROPERTY_CHANGED);
        pipe.println(VirtualMachine04.SIGNAL_PROPERTY_CHANGED);
    }

    public static void main(String[] args) {
        new VM04Target(args).runTargetApplication();
    }
}

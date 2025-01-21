/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.jdwp.ObjectReference.ReferringObjects.referringObjects001;

import java.util.*;
import nsk.share.ReferringObjectSet;
import nsk.share.jdi.HeapwalkingDebuggee;
import nsk.share.jdwp.*;

public class referringObjects001a extends AbstractJDWPDebuggee {
    public static Object testInstance;

    public static final String COMMAND_CREATE_TEST_INSTANCES = "COMMAND_CREATE_TEST_INSTANCES";

    public static final int expectedCount = HeapwalkingDebuggee.includedIntoReferrersCountTypes.size() + 1;

    private ArrayList<ReferringObjectSet> referrers = new ArrayList<ReferringObjectSet>();

    public boolean parseCommand(String command) {
        if (super.parseCommand(command))
            return true;

        if (command.equals(COMMAND_CREATE_TEST_INSTANCES)) {
            testInstance = new Object();

            // create objects refering to 'testInstance' via references with types which should be supported by command ObjectReference.ReferringObjects
            for (String referenceType : HeapwalkingDebuggee.includedIntoReferrersCountTypes) {
                referrers.add(new ReferringObjectSet(testInstance, 1, referenceType));
            }

            return true;
        }

        return false;
    }

    public static void main(String args[]) {
        new referringObjects001a().doTest(args);
    }
}

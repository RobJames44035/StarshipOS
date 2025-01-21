/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jvmti.SetSystemProperty;

import java.io.PrintStream;
import java.util.*;

import nsk.share.*;
import nsk.share.jvmti.*;

public class setsysprop003 extends DebugeeClass {

    /** Load native library if required. */
    static {
        loadLibrary("setsysprop003");
    }

    /** Run test from command line. */
    public static void main(String argv[]) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        // JCK-compatible exit
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    /** Run test from JCK-compatible environment. */
    public static int run(String argv[], PrintStream out) {
        return new setsysprop003().runIt(argv, out);
    }

    /* =================================================================== */

    /* scaffold objects */
    ArgumentHandler argHandler = null;
    Log log = null;
    int status = Consts.TEST_PASSED;

    /* tested properties */
    public static final int PROPERTIES_COUNT = 3;
    public static final String propDescList[][] = {
        {"nsk.jvmti.test.property", "new value of nsk.jvmti.test.property"},
        {"nsk.jvmti.test.property.empty.old", "new value of nsk.jvmti.test.property.emply.old"},
        {"nsk.jvmti.test.property.empty.new", ""}
    };

    /** Check new values of defined system properties. */
    boolean checkProperties() {
        boolean success = true;

        log.display("Checking if System.getProperties() returns changed system properies:");
        Properties props = System.getProperties();
        Enumeration names = props.propertyNames();
        int found = 0;
        while (names.hasMoreElements()) {
            String name = (String)names.nextElement();
            log.display("  property: [" + name + "]");
            for (int j = 0; j < PROPERTIES_COUNT; j++) {
                if (name.equals(propDescList[j][0])) {
                    log.display("SUCCESS: found tested system property: " + propDescList[j][0]);
                    found++;
                    break;
                }
            }
        }
        if (found < PROPERTIES_COUNT) {
            log.complain("System,getProperties() returned not all changed system properties: \n"
                        + "    found properties: " + found + "\n"
                        + "    expected:         " + PROPERTIES_COUNT);
            success = false;
        }

        log.display("Checking if System.getProperty() returns changed system properies:");
        for (int j = 0; j < PROPERTIES_COUNT; j++) {
            String name = propDescList[j][0];
            String expectedValue = propDescList[j][1];

            log.display("  property: [" + name + "]");
            String value = System.getProperty(name);
            log.display("     value: \"" + value + "\"");
            if (value == null || !value.equals(expectedValue)) {
                log.complain("System.getProperty() returned unexpected value of changed system property:\n"
                            + "    property name: " + name + "\n"
                            + "    got value:     \"" + value + "\"\n"
                            + "    changed value: \"" + expectedValue + "\"");
                success = false;
            }
        }

        return success;
    }

    /** Run debuggee code. */
    public int runIt(String argv[], PrintStream out) {
        argHandler = new ArgumentHandler(argv);
        log = new Log(out, argHandler);

        if (!checkProperties()) {
            status = Consts.TEST_FAILED;
        }

        return status;
    }
}

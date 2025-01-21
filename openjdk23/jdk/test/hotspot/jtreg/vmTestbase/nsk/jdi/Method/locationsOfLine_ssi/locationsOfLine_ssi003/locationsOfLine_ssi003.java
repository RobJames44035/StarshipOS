/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 *
 * @summary converted from VM Testbase nsk/jdi/Method/locationsOfLine_ssi/locationsOfLine_ssi003.
 * VM Testbase keywords: [quick, jpda, jdi, feature_sde, vm6]
 * VM Testbase readme:
 * DESCRIPTION
 *     The test checks up that method 'com.sun.jdi.Method.locationsOfLine(String stratum, String sourceName, int lineNumber)' returns
 *     correct values for all stratums available for class and if sourceName == null locaitions for all sources are returned.
 *     Debugger creates copy of class file for class 'nsk.share.jdi.TestClass1' with SourceDebugExtension attribute
 *     which contains informations for 3 stratums('TestStratum1'-'TestStratum3') and for each of this stratums following line mapping
 *     is defined (each method has locations in 3 different sources):
 *         "Java"          "TestStratum"
 *         <init>
 *         9       -->     1000, source1, path1
 *         10      -->     1000, source2, path2
 *         11      -->     1000, source3, path3
 *         ...             ...
 *         sde_testMethod1
 *         20      -->     1100, source1, path1
 *         21      -->     1100, source2, path2
 *         22      -->     1100, source3, path3
 *         ...             ...
 *         sde_testMethod2
 *         31      -->     1200, source1, path1
 *         32      -->     1200, source2, path2
 *         33      -->     1200, source3, path3
 *         ...             ...
 *     Then debugger forces debuggee to load 'TestClass1' from updated class file, obtains ReferenceType for this class
 *     and checks up that for all methods defined in this class method 'com.sun.jdi.Method.locationsOfLine(String stratum, String sourceName, int lineNumber)'
 *     for all test stratums('TestStratum1'-'TestStratum3') returns only expected locations depending on given line number and source name,
 *     and if source name is null locations for all sources are returned.
 *
 * @library /vmTestbase
 *          /test/lib
 * @build nsk.jdi.Method.locationsOfLine_ssi.locationsOfLine_ssi003.locationsOfLine_ssi003
 * @run driver
 *      nsk.jdi.Method.locationsOfLine_ssi.locationsOfLine_ssi003.locationsOfLine_ssi003
 *      -verbose
 *      -arch=${os.family}-${os.simpleArch}
 *      -waittime=5
 *      -debugee.vmkind=java
 *      -transport.address=dynamic
 *      -debugee.vmkeys="${test.vm.opts} ${test.java.opts}"
 *      -testClassPath ${test.class.path}
 *      -testWorkDir .
 *      -testStratumCount 3
 */

package nsk.jdi.Method.locationsOfLine_ssi.locationsOfLine_ssi003;

import java.io.*;
import java.util.*;
import com.sun.jdi.*;
import nsk.share.Consts;
import nsk.share.jdi.sde.*;

public class locationsOfLine_ssi003 extends SDEDebugger {
    public static void main(String argv[]) {
        int result = run(argv,System.out);
        if (result != 0) {
            throw new RuntimeException("TEST FAILED with result " + result);
        }
    }

    public static int run(String argv[], PrintStream out) {
        return new locationsOfLine_ssi003().runIt(argv, out);
    }

    protected String[] doInit(String args[], PrintStream out) {
        args = super.doInit(args, out);

        ArrayList<String> standardArgs = new ArrayList<String>();

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-testStratumCount") && (i < args.length - 1)) {
                testStratumCount = Integer.parseInt(args[i + 1]);
                i++;
            } else
                standardArgs.add(args[i]);
        }

        return standardArgs.toArray(new String[] {});
    }

    private int testStratumCount = 1;

    public void doTest() {
        String className = TestClass1.class.getName();

        Map<String, LocationsData> testStratumData = prepareDefaultPatchedClassFile_Type5(className, testStratumCount);
        /*
         * Method 'prepareDefaultPatchedClassFile_Type5' creates class file with
         * following line mapping for each TestStratum: "Java" "TestStratum"
         *
         * <init>
         * 9 --> 1000, source1, path1
         * 10 --> 1000, source2, path2
         * 11 --> 1000, source3, path3 ... ...
         *
         * sde_testMethod1
         * 20 --> 1100, source1, path1
         * 21 --> 1100, source2, path2
         * 22 --> 1100, source3, path3 ... ...
         *
         * sde_testMethod2
         * 31 --> 1200, source1, path1
         * 32 --> 1200, source2, path2
         * 33 --> 1200, source3, path3 ... ...
         */

        // debuggee loads TestClass1 from patched class file
        pipe.println(SDEDebuggee.COMMAND_LOAD_CLASS + ":" + className);

        if (!isDebuggeeReady())
            return;

        // obtain ReferenceType for loaded class
        ReferenceType referenceType = debuggee.classByName(className);

        for (String stratumName : testStratumData.keySet()) {
            log.display("Check stratum: " + stratumName);

            for (Method method : referenceType.methods()) {
                log.display("Check method '" + method.name() + "'");
                List<DebugLocation> locationsOfMethod = locationsOfMethod(
                        testStratumData.get(stratumName).allLocations,
                        method.name());

                log.display("Check locations with specified source name");
                check_Method_locationsOfLine(method, stratumName, false, locationsOfMethod);

                log.display("Check locations for all sources");
                check_Method_locationsOfLine(method, stratumName, true, locationsOfMethod);
            }
        }

    }
}

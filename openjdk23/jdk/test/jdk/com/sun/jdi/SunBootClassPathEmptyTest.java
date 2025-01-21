/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

import com.sun.jdi.connect.*;
import com.sun.jdi.*;
import java.util.Map;
import java.util.List;
import jdk.test.lib.Asserts;

/*
 * @test
 * @summary Verifies that PathSearchingVirtualMachine.bootClassPath()
 *          returns an empty list in case no bootclass path specified
 *          regardless of sun.boot.class.path option, which is now obsolete
 * @library /test/lib
 * @compile TestClass.java
 * @compile SunBootClassPathEmptyTest.java
 * @run main/othervm SunBootClassPathEmptyTest
 */
public class SunBootClassPathEmptyTest {

    /**
     * Helper class to facilitate the debuggee VM launching
     */
    private static class VmConnector {

        LaunchingConnector lc;
        VirtualMachine vm;

        VmConnector() {
            for (LaunchingConnector c : Bootstrap.virtualMachineManager().launchingConnectors()) {
                System.out.println("name: " + c.name());
                if (c.name().equals("com.sun.jdi.CommandLineLaunch")) {
                    lc = c;
                    break;
                }
            }
            if (lc == null) {
                throw new RuntimeException("Connector not found");
            }
        }

        PathSearchingVirtualMachine launchVm(String cmdLine, String options) throws Exception {
            Map<String, Connector.Argument> vmArgs = lc.defaultArguments();
            vmArgs.get("main").setValue(cmdLine);
            if (options != null) {
                vmArgs.get("options").setValue(options);
            }
            System.out.println("Debugger is launching vm ...");
            vm = lc.launch(vmArgs);
            if (!(vm instanceof PathSearchingVirtualMachine)) {
                throw new RuntimeException("VM is not a PathSearchingVirtualMachine");
            }
            return (PathSearchingVirtualMachine) vm;
        }

    }

    private static VmConnector connector = new VmConnector();

    public static void main(String[] args) throws Exception {
        testWithObsoleteClassPathOption(null);
        testWithObsoleteClassPathOption("someclasspath");
    }

    private static void testWithObsoleteClassPathOption(String obsoleteClassPath) throws Exception {
        PathSearchingVirtualMachine vm = connector.launchVm("TestClass", makeClassPathOptions(obsoleteClassPath));
        List<String> bootClassPath = vm.bootClassPath();
        Asserts.assertNotNull(bootClassPath, "Expected bootClassPath to be empty but was null");
        Asserts.assertEquals(0, bootClassPath.size(), "Expected bootClassPath.size() 0 but was: " + bootClassPath.size());
    }

    private static String makeClassPathOptions(String obsoleteClassPath) {
        return obsoleteClassPath == null ? null : "-Dsun.boot.class.path=" + obsoleteClassPath;
    }

}

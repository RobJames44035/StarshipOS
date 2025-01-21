/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/* @test
 * @bug 4287596
 * @summary Unit test for "Pluggable Connectors and Transports" feature.
 *
 * This tests launches a debuggee using a custom LaunchingConnector.
 *
 * @modules jdk.jdi/com.sun.tools.jdi
 * @build DebugUsingCustomConnector SimpleLaunchingConnector Foo NullTransportService
 * @run main/othervm DebugUsingCustomConnector
 */
import com.sun.jdi.*;
import com.sun.jdi.connect.*;
import java.util.*;

public class DebugUsingCustomConnector {

    static Connector find(List l, String name) {
        Iterator i = l.iterator();
        while (i.hasNext()) {
            Connector c = (Connector)i.next();
            if (c.name().equals(name)) {
                return c;
            }
        }
        return null;
    }

    public static void main(String main_args[]) throws Exception {
        /*
         * In development builds the JDI classes are on the boot class
         * path so defining class loader for the JDI classes will
         * not find classes on the system class path.
         */
        VirtualMachineManager vmm = Bootstrap.virtualMachineManager();
        if (vmm.getClass().getClassLoader() == null) {
            System.out.println("JDI on bootclasspath - test skipped");
            return;
        }

        List launchers = vmm.launchingConnectors();

        LaunchingConnector connector =
            (LaunchingConnector)find(launchers, "SimpleLaunchingConnector");

        Map args = connector.defaultArguments();

        Connector.StringArgument arg =
            (Connector.StringArgument)args.get("class");
        arg.setValue("Foo");

        VirtualMachine vm = connector.launch(args);
        vm.resume();
    }

}

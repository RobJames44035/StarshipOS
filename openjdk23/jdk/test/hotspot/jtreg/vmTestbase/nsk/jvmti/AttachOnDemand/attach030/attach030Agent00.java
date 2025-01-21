/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.jvmti.AttachOnDemand.attach030;

import nsk.share.TestBug;
import nsk.share.aod.AbstractJarAgent;
import java.lang.instrument.Instrumentation;

/*
 * First agent tries to redefine class loaded by the target application, then agent itself loads
 * class and redefines it
 */
public class attach030Agent00 extends AbstractJarAgent {

    private static final String classToRedefine1 = "nsk.jvmti.AttachOnDemand.attach030.ClassToRedefine01";

    private static final String classToRedefine2 = "nsk.jvmti.AttachOnDemand.attach030.ClassToRedefine02";

    protected void init(String[] args) {
        if (pathToNewByteCode() == null)
            throw new TestBug("Path to new byte code wasn't specified (" + pathToNewByteCodeOption + "=...)");
    }

    protected void agentActions() throws Throwable {
        boolean classWasFound = false;

        for (Class<?> klass : inst.getAllLoadedClasses()) {
            /*
             * When agent is running class 'classToRedefine1' should be
             * already loaded by the target application
             */
            if (klass.getName().equals(classToRedefine1)) {
                classWasFound = true;
                display("Trying to redefine class '" + classToRedefine1 + "'");
                redefineClass(klass);
                display("Class was redefined");
                break;
            }
        }

        if (!classWasFound) {
            setStatusFailed("Instrumentation.getAllLoadedClasses() didn't return class '" + classToRedefine1 + "'");
        }

        /*
         * Try to load and redefine 'classToRedefine2'
         */

        Class<?> klass = Class.forName(classToRedefine2);
        display("Trying to redefine class '" + classToRedefine2 + "'");
        redefineClass(klass);
        display("Class was redefined");

        final String expectedString = "ClassToRedefine02: Class is redefined";
        ClassToRedefine02 instance = new ClassToRedefine02();
        String string = instance.getString();
        display("ClassToRedefine02.getString(): " + string);
        if (!string.equals(expectedString)) {
            setStatusFailed("ClassToRedefine02.getString() returned unexpected value , expected is '" + expectedString + "'");
        }
    }

    public static void agentmain(String options, Instrumentation inst) {
        new attach030Agent00().runJarAgent(options, inst);
    }
}

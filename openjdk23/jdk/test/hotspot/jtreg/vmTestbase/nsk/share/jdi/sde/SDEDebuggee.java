/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.share.jdi.sde;

import java.lang.reflect.Method;
import nsk.share.TestBug;
import nsk.share.jdi.*;

public class SDEDebuggee extends AbstractJDIDebuggee {
    public static String mainThreadName = "SDEDebuggee_mainThread";

    // command:class_name
    public static String COMMAND_EXECUTE_TEST_METHODS = "executeTestMethods";

    public static void main(String args[]) {
        new SDEDebuggee().doTest(args);
    }

    protected String[] doInit(String[] args) {
        args = super.doInit(args);

        if (classpath == null)
            throw new TestBug("Debuggee requires '-testClassPath' parameter");

        Thread.currentThread().setName(mainThreadName);

        return args;
    }

    public boolean parseCommand(String command) {
        if (super.parseCommand(command))
            return true;

        if (command.startsWith(COMMAND_EXECUTE_TEST_METHODS)) {
            // extract class name
            String split[] = command.split(":");

            if ((split.length != 2) || (split[1].length() == 0))
                throw new TestBug("");

            executeTestMethods(split[1]);
            breakpointMethod();

            return true;
        }

        return false;
    }

    // Keep class loader alive to avoid ObjectCollectedException
    // on the debugger side, in case the GC unloads the class and
    // invalidates code locations.
    private TestClassLoader classLoader;

    // create instance of given class and execute all methods which names start
    // with 'sde_testMethod'
    private void executeTestMethods(String className) {
        classLoader = new TestClassLoader();
        classLoader.setClassPath(classpath);

        try {
            Class<?> klass = classLoader.loadClass(className);
            Object testObject = klass.newInstance();

            for (Method method : klass.getDeclaredMethods()) {
                if (method.getName().startsWith("sde_testMethod"))
                    method.invoke(testObject, new Object[] {});
            }
        } catch (Exception e) {
            setSuccess(false);
            log.complain("Unexpected exception: " + e);
            e.printStackTrace(log.getOutStream());

            throw new TestBug("Unexpected exception: " + e);
        }
    }

}

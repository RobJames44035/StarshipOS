/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

import java.lang.instrument.Instrumentation;
import java.lang.instrument.ClassDefinition;
import jdk.test.lib.compiler.InMemoryJavaCompiler;
import jdk.test.lib.helpers.ClassFileInstaller;

/*
 * Helper class to write tests that redefine classes.
 * When main method is run, it will create a redefineagent.jar that can be used
 * with the -javaagent option to support redefining classes in jtreg tests.
 *
 * See sample test in test/testlibrary_tests/RedefineClassTest.java
 */
public class RedefineClassHelper {

    public static Instrumentation instrumentation;
    public static void premain(String agentArgs, Instrumentation inst) {
        instrumentation = inst;
    }

    /**
     * Redefine a class
     *
     * @param clazz Class to redefine
     * @param javacode String with the new java code for the class to be redefined
     */
    public static void redefineClass(Class<?> clazz, String javacode) throws Exception {
        byte[] bytecode = InMemoryJavaCompiler.compile(clazz.getName(), javacode);
        redefineClass(clazz, bytecode);
    }

    /**
     * Redefine a class
     *
     * @param clazz Class to redefine
     * @param bytecode byte[] with the new class
     */
    public static void redefineClass(Class<?> clazz, byte[] bytecode) throws Exception {
        instrumentation.redefineClasses(new ClassDefinition(clazz, bytecode));
    }

    /**
     * Main method to be invoked before test to create the redefineagent.jar
     */
    public static void main(String[] args) throws Exception {
        String manifest = "Premain-Class: RedefineClassHelper\nCan-Redefine-Classes: true\n";
        ClassFileInstaller.writeJar("redefineagent.jar", ClassFileInstaller.Manifest.fromString(manifest), "RedefineClassHelper");
    }
}

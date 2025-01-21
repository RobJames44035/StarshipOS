/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8027572
 * @summary class unloading resets profile, method compiled after the profile is
 * first set and before class loading sets unknown bit with not recorded class
 * @library /
 * @requires vm.compMode != "Xcomp"
 * @build compiler.profiling.unloadingconflict.B
 * @run main/othervm -XX:TypeProfileLevel=222 -XX:-BackgroundCompilation
 *                   compiler.profiling.unloadingconflict.TestProfileConflictClassUnloading
 *
 */

package compiler.profiling.unloadingconflict;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;

public class TestProfileConflictClassUnloading {
    static class A {
    }


    static void m1(Object o) {
    }

    static void m2(Object o) {
        m1(o);
    }

    static void m3(A a, boolean do_call) {
        if (!do_call) {
            return;
        }
        m2(a);
    }

    public static ClassLoader newClassLoader() {
        try {
            return new URLClassLoader(new URL[] {
                    Paths.get(System.getProperty("test.classes",".")).toUri().toURL(),
            }, null);
        } catch (MalformedURLException e){
            throw new RuntimeException("Unexpected URL conversion failure", e);
        }
    }

    public static void main(String[] args) throws Exception {
        ClassLoader loader = newClassLoader();
        Object o = loader.loadClass("compiler.profiling.unloadingconflict.B").newInstance();
        // collect conflicting profiles
        for (int i = 0; i < 5000; i++) {
            m2(o);
        }
        // prepare for conflict
        A a = new A();
        for (int i = 0; i < 5000; i++) {
            m3(a, false);
        }
        // unload class in profile
        o = null;
        loader = null;
        System.gc();
        // record the conflict
        m3(a, true);
        // trigger another GC
        System.gc();
    }
}

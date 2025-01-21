/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package p;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.Arrays;

import static java.lang.invoke.MethodHandles.Lookup.*;

public class Main {
    /*
     * Each Test object loads q.EndPoint and q.T with a custom loader.
     * These types are used to look up a method handle "EndPoint::test(T)".
     */
    static class Test {
        final ClassLoader loader;
        final Class<?> target;
        final Class<?> param;
        Test(String name) throws Exception {
            URL url = Paths.get(System.getProperty("test.classes"), "modules", "m2")
                           .toUri().toURL();
            this.loader = new URLClassLoader(name, new URL[]{url}, null);
            this.target = Class.forName("q.EndPoint", true, loader);
            this.param = Class.forName("q.T", true, loader);
            assertTrue(target != q.EndPoint.class);
            assertTrue(param != q.T.class);
        }

        /*
         *
         */
        public void verifyAccess(Lookup... publicLookups) throws Throwable {
            System.err.println(loader.getName() + ": verify access for " + Arrays.toString(publicLookups));
            for (Lookup lookup : publicLookups) {
                assertTrue((lookup.lookupModes() & UNCONDITIONAL) == UNCONDITIONAL);
                assertTrue((lookup.lookupModes() & PUBLIC) == 0);
                MethodHandle mh = lookup.findVirtual(target, "test", MethodType.methodType(void.class, param));
                mh.invoke(target.newInstance(), param.newInstance());
                checkTypeConsistency(mh);
            }
        }
    }

    /*
     * Verify that publicLookup can teleport to:
     * 1) q.EndPoint defined in m2
     * 2) q.EndPoint defined by a custom loader CL1 in one unnamed module
     * 3) q.EndPoint defined by a custom loader CL2 in another unnamed module
     *
     * All the resulting Lookup objects are public lookups and can find
     * any public accessible member.
     */
    public static void main(String... args) throws Throwable {
        Test test1 = new Test("CL1");
        Test test2 = new Test("CL2");

        Lookup lookup1 = MethodHandles.publicLookup();
        Lookup lookup2 = MethodHandles.publicLookup().in(test1.target);
        Lookup lookup3 = MethodHandles.publicLookup().in(test2.target);
        assertTrue(lookup2.lookupClass().getClassLoader() != lookup3.lookupClass().getClassLoader());

        test1.verifyAccess(lookup1, lookup2, lookup3);
        test2.verifyAccess(lookup1, lookup2, lookup3);
    }

    static void checkTypeConsistency(MethodHandle mh) throws Throwable {
        try {
            mh.invoke(new q.EndPoint(), new q.T());
            throw new Error("expect fail to invoke due to type inconsistency");
        } catch (ClassCastException e) {
            assertTrue(e.getMessage().startsWith("Cannot cast q."));
        }
    }

    static void assertTrue(boolean v) {
        if (!v) {
            throw new AssertionError("unexpected result");
        }
    }
}

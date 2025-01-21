/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/**
* @test
* @summary Tests class unloading on virtual threads
*
* @requires vm.continuations
* @compile ClassUnloading.java
* @run main/othervm -XX:-UseCompressedOops ClassUnloading
* @run main/othervm -XX:+UseCompressedOops ClassUnloading
* @run main/othervm -Xcomp -XX:-TieredCompilation -XX:CompileOnly=jdk.internal.vm.Continuation::*,ClassUnloading::* ClassUnloading
*/

// @run testng/othervm -Xcomp -XX:-TieredCompilation -XX:CompileOnly=jdk.internal.vm.Continuation::*,Basic::* Basic

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Executors;
import java.util.function.BooleanSupplier;

public class ClassUnloading {
    public static void main(String[] args) throws Throwable {
        System.out.println(Thread.currentThread());
        test();
        System.out.println();
        // repeat test in virtual thread
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            executor.submit(() -> {
                System.out.println(Thread.currentThread());
                test();
                return null;
            }).get();
        }
    }

    static void test() throws Exception {
        // class bytes for Dummy class
        URI uri = ClassUnloading.class.getProtectionDomain().getCodeSource().getLocation().toURI();
        Path file = Path.of(uri).resolve("Dummy.class");
        byte[] classBytes = Files.readAllBytes(file);
        // define hidden class in same run-time package as this class
        Lookup lookup = MethodHandles.lookup();
        Class<?> clazz = lookup.defineHiddenClass(classBytes, false).lookupClass();
        String cn = clazz.getName();
        var ref = new WeakReference<Class<?>>(clazz);
        // try to cause hidden class to be unloaded, should fail due to strong ref
        System.out.println("try unload " + cn);
        boolean unloaded = tryUnload(ref);
        if (unloaded)
            throw new RuntimeException(cn + " unloaded!!!");
        Reference.reachabilityFence(clazz);
        clazz = null;
        // try to cause hidden class to be unloaded, should succeed
        System.out.println("unload " + cn);
        unload(ref);
    }

    static boolean tryUnload(WeakReference<Class<?>> ref) {
        return gc(() -> ref.get() == null);
    }

    static void unload(WeakReference<Class<?>> ref) {
        boolean cleared = gc(() -> ref.get() == null);
        if (!cleared)
            throw new RuntimeException("weak reference not cleared!!");
        Class<?> clazz = ref.get();
        if (clazz != null)
            throw new RuntimeException(clazz + " not unloaded");
    }

    static boolean gc(BooleanSupplier s) {
        try {
            for (int i = 0; i < 10; i++) {
                if (s.getAsBoolean())
                    return true;
                System.out.print(".");
                System.gc();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return false;
        } finally {
            System.out.println();
        }
    }
}

class Dummy { }

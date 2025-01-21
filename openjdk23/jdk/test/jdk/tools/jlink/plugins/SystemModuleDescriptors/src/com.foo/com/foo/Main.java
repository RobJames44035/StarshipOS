/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package com.foo;

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.util.stream.Stream;

/**
 * Sanity test if SystemModules pre-resolved at link-time for com.foo
 * with main class is loaded properly.
 */
public class Main {
    public static void main(String... args) throws Exception {
        ModuleDescriptor md = Main.class.getModule().getDescriptor();
        System.out.println(md);

        checkMainClass("com.foo", "com.foo.Main");
        checkMainClass("net.foo", "net.foo.Main");
        Stream.of("jdk.httpserver", "jdk.jfr").forEach(mn ->
                ModuleFinder.ofSystem().find(mn).get().descriptor().mainClass()
                            .orElseThrow(() -> new RuntimeException(mn + " no main class"))
        );
    }

    static void checkMainClass(String mn, String mainClass) {
        String cn = ModuleFinder.ofSystem().find(mn).get().descriptor().mainClass().get();
        if (!cn.equals(mainClass)) {
            throw new RuntimeException("Mismatched main class of module " + mn + ": " + cn + " expected: " + mainClass);
        }
    }
}

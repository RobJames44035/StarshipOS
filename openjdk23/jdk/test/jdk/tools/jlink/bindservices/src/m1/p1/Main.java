/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */
package p1;

import java.lang.module.ModuleFinder;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This tests if JAVA_HOME is linked only with the specified modules.
 */
public class Main {
    public static void main(String... args) {
        Set<String> modules = ModuleFinder.ofSystem().findAll().stream()
            .map(mref -> mref.descriptor().name())
            .filter(mn -> !mn.equals("java.base"))
            .collect(Collectors.toSet());

        Set<String> notLinked = Stream.of(args).filter(mn -> !modules.contains(mn))
                                      .collect(Collectors.toSet());
        if (!notLinked.isEmpty()) {
            throw new RuntimeException("Expected modules not linked in the image: "
                + notLinked);
        }
        Stream.of(args).forEach(modules::remove);

        if (!modules.isEmpty()) {
            throw new RuntimeException("Unexpected modules linked in the image: "
                + modules);
        }
    }
}

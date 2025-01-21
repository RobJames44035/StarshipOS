/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package p;

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ListModules {
    public static void main(String... args) {
        Set<String> modules = ModuleFinder.ofSystem().findAll()
            .stream()
            .map(ModuleReference::descriptor)
            .map(ModuleDescriptor::name)
            .collect(Collectors.toSet());

        Set<String> expected = Arrays.stream(args).collect(Collectors.toSet());

        if (!modules.equals(expected)) {
            throw new RuntimeException(modules + " != " + expected);
        }

    }
}

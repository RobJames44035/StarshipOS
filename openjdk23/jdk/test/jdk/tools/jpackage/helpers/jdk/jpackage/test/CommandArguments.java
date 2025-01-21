/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */
package jdk.jpackage.test;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandArguments<T> {

    CommandArguments() {
        args = new ArrayList<>();
    }

    public final T clearArguments() {
        args.clear();
        return (T) this;
    }

    public final T addArgument(String v) {
        args.add(v);
        return (T) this;
    }

    public final T addArguments(List<String> v) {
        args.addAll(v);
        return (T) this;
    }

    public final T addArgument(Path v) {
        return addArgument(v.toString());
    }

    public final T addArguments(String... v) {
        return addArguments(Arrays.asList(v));
    }

    public final T addPathArguments(List<Path> v) {
        return addArguments(v.stream().map((p) -> p.toString()).collect(
                Collectors.toList()));
    }

    public final List<String> getAllArguments() {
        return List.copyOf(args);
    }

    protected void verifyMutable() {
        if (!isMutable()) {
            throw new UnsupportedOperationException(
                    "Attempt to modify immutable object");
        }
    }

    protected boolean isMutable() {
        return true;
    }

    protected List<String> args;
}

/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package jdk.internal.classfile.impl;

import java.lang.classfile.Interfaces;
import java.lang.classfile.constantpool.ClassEntry;
import java.util.List;
import java.util.stream.Collectors;

public final class InterfacesImpl
        extends AbstractElement
        implements Interfaces {
    private final List<ClassEntry> interfaces;

    public InterfacesImpl(List<ClassEntry> interfaces) {
        this.interfaces = List.copyOf(interfaces);
    }

    @Override
    public List<ClassEntry> interfaces() {
        return interfaces;
    }

    @Override
    public void writeTo(DirectClassBuilder builder) {
        builder.setInterfaces(interfaces);
    }

    @Override
    public String toString() {
        return String.format("Interfaces[interfaces=%s]", interfaces.stream()
                .map(iface -> iface.name().stringValue())
                .collect(Collectors.joining(", ")));
    }
}

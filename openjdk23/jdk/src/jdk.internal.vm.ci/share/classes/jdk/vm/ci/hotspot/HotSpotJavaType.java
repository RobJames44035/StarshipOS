/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */
package jdk.vm.ci.hotspot;

import jdk.vm.ci.meta.JavaType;

/**
 * Common base class for all HotSpot {@link JavaType} implementations.
 */
public abstract class HotSpotJavaType implements JavaType {

    private final String name;

    public HotSpotJavaType(String name) {
        this.name = name;
    }

    @Override
    public final String getName() {
        return name;
    }
}

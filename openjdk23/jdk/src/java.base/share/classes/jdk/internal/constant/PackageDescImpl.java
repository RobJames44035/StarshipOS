/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
package jdk.internal.constant;

import java.lang.constant.PackageDesc;

/*
 * Implementation of {@code PackageDesc}
 * @param internalName must have been validated
 */
public record PackageDescImpl(String internalName) implements PackageDesc {

    @Override
    public String toString() {
        return String.format("PackageDesc[%s]", name());
    }
}

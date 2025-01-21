/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
package jdk.internal.constant;

import java.lang.constant.ModuleDesc;

/*
 * Implementation of {@code ModuleDesc}
 * @param name must have been validated
 */
public record ModuleDescImpl(String name) implements ModuleDesc {

    @Override
    public String toString() {
        return String.format("ModuleDesc[%s]", name());
    }
}

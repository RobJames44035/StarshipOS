/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */
package jdk.tools.jlink.internal;

import jdk.internal.util.Architecture;
import jdk.internal.util.OperatingSystem;

import java.util.Locale;

/**
 * Supported OperatingSystem and Architecture.
 */
public record Platform(OperatingSystem os, Architecture arch) {

    /*
     * Returns the {@code Platform} based on the platformString of the form <operating system>-<arch>.
     * @throws IllegalArgumentException if the delimiter is missing or either OS or
     * architecture is not known
     */
    public static Platform parsePlatform(String platformString) {
        String osName;
        String archName;
        int index = platformString.indexOf("-");
        if (index < 0) {
            throw new IllegalArgumentException("platformString missing delimiter: " + platformString);
        }
        osName = platformString.substring(0, index);
        OperatingSystem os = OperatingSystem.valueOf(osName.toUpperCase(Locale.ROOT));

        archName = platformString.substring(index + 1);
        Architecture arch = Architecture.lookupByName(archName);

        return new Platform(os, arch);
    }

    /**
     * {@return the runtime {@code Platform}}
     */
    public static Platform runtime() {
        return new Platform(OperatingSystem.current(), Architecture.current());
    }

    /**
     * Returns a {@code String} representation of a {@code Platform} in the format of <os>-<arch>
     */
    @Override
    public String toString() {
        return os.toString().toLowerCase(Locale.ROOT) + "-" + arch.toString().toLowerCase(Locale.ROOT);
    }
}

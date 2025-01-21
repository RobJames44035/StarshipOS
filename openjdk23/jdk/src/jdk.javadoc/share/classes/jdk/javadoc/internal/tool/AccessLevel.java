/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package jdk.javadoc.internal.tool;

import java.util.Set;
import javax.lang.model.element.Modifier;

/**
 * The access levels.
 *
 * These constants are ordered by their access limiting power. The bigger the
 * {@link #ordinal() ordinal} of a constant, the more limiting power that
 * constant has.
 *
 * That has a few useful implications. For example, the levels can be compared
 * by {@link #compareTo}. It also means that {@code AccessLevel.values()[0]} and
 * {@code AccessLevel.values()[values.length() - 1] and the constants with the
 * smallest and the biggest limiting powers respectively.
 */
public enum AccessLevel implements Comparable<AccessLevel> {

    /** Does not limit access */
    PRIVATE,
    /** Limits access to entities that are public, protected, or declared with package access */
    PACKAGE,
    /** Limits access to public and protected entities */
    PROTECTED,
    /** Limits access to public entities */
    PUBLIC;

    public static AccessLevel of(Set<Modifier> mods) {
        if (mods.contains(Modifier.PUBLIC))
            return AccessLevel.PUBLIC;
        else if (mods.contains(Modifier.PROTECTED))
            return AccessLevel.PROTECTED;
        else if (mods.contains(Modifier.PRIVATE))
            return AccessLevel.PRIVATE;
        else
            return AccessLevel.PACKAGE;
    }
}

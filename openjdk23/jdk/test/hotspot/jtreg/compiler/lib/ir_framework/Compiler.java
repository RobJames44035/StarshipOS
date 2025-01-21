/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package compiler.lib.ir_framework;

/**
 * Compilers to select for {@link DontCompile}. HotSpot does not handle the exclusion of a C1 method at a specific level.
 * It can only exclude a method for the entire C1 compilation. Thus, this annotation is provided for {@link DontCompile}
 * instead of {@link CompLevel}.
 *
 * @see DontCompile
 */
public enum Compiler {
    /**
     * Selecting both the C1 and C2 compiler. This must be in sync with hotspot/share/compiler/compilerDefinitions.hpp.
     */
    ANY(-1),
    /**
     * The C1 compiler.
     */
    C1(1),
    /**
     * The C2 compiler.
     */
    C2(4),

    ;

    private final int value;

    Compiler(int level) {
        this.value = level;
    }

    /**
     * Get the compilation level as integer value. These will match the levels specified in HotSpot (if available).
     *
     * @return the compilation level as integer.
     */
    public int getValue() {
        return value;
    }
}

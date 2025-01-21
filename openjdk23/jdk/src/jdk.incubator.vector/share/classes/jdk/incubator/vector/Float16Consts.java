/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package jdk.incubator.vector;

import static jdk.incubator.vector.Float16.MIN_EXPONENT;
import static jdk.incubator.vector.Float16.PRECISION;
import static jdk.incubator.vector.Float16.SIZE;

/**
 * This class contains additional constants documenting limits of the
 * {@code Float16} type.
 */

class Float16Consts {
    /**
     * Don't let anyone instantiate this class.
     */
    private Float16Consts() {}

    /**
     * The number of logical bits in the significand of a
     * {@code Float16} number, including the implicit bit.
     */
    public static final int SIGNIFICAND_WIDTH = PRECISION;

    /**
     * The exponent the smallest positive {@code Float16}
     * subnormal value would have if it could be normalized.
     */
    public static final int MIN_SUB_EXPONENT =
            MIN_EXPONENT - (SIGNIFICAND_WIDTH - 1); // -24

    /**
     * Bias used in representing a {@code Float16} exponent.
     */
    public static final int EXP_BIAS =
            (1 << (SIZE - SIGNIFICAND_WIDTH - 1)) - 1; // 15

    /**
     * Bit mask to isolate the sign bit of a {@code Float16}.
     */
    public static final int SIGN_BIT_MASK = 1 << (SIZE - 1);

    /**
     * Bit mask to isolate the exponent field of a {@code Float16}.
     */
    public static final int EXP_BIT_MASK =
            ((1 << (SIZE - SIGNIFICAND_WIDTH)) - 1) << (SIGNIFICAND_WIDTH - 1);

    /**
     * Bit mask to isolate the significand field of a {@code Float16}.
     */
    public static final int SIGNIF_BIT_MASK = (1 << (SIGNIFICAND_WIDTH - 1)) - 1;

    /**
     * Bit mask to isolate the magnitude bits (combined exponent and
     * significand fields) of a {@code Float16}.
     */
    public static final int MAG_BIT_MASK = EXP_BIT_MASK | SIGNIF_BIT_MASK;

    static {
        // verify bit masks cover all bit positions and that the bit
        // masks are non-overlapping
        assert(((SIGN_BIT_MASK | EXP_BIT_MASK | SIGNIF_BIT_MASK) == 0xFFFF) &&
               (((SIGN_BIT_MASK & EXP_BIT_MASK) == 0) &&
                ((SIGN_BIT_MASK & SIGNIF_BIT_MASK) == 0) &&
                ((EXP_BIT_MASK & SIGNIF_BIT_MASK) == 0)) &&
                ((SIGN_BIT_MASK | MAG_BIT_MASK) == 0xFFFF));
    }
}

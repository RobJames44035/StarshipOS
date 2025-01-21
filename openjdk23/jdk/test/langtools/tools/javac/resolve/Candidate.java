/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
@interface Candidate {
    /**
     * the candidate position (line/col of the method call for which this candidate
     * is a potential overload candidate)
     */
    Pos pos() default @Pos(userDefined=false);
    /**
     * resolution phases for which this candidate is applicable
     */
    Phase[] applicable() default { };
    /**
     * is this candidate the most specific (in the resolution phases for which it
     * is also applicable)
     */
    boolean mostSpecific() default false;
    /**
     * this candidate inferred signature (in the resolution phases for which it
     * is also applicable, in case it corresponds to a generic method)
     */
    String sig() default "";
}

enum Phase {
    BASIC("BASIC"),
    BOX("BOX"),
    VARARGS("VARARITY");

    final String javacString;

    private Phase(String javacString) {
        this.javacString = javacString;
    }

    static Phase fromString(String s) {
        for (Phase phase : Phase.values()) {
            if (phase.javacString.equals(s)) {
                return phase;
            }
        }
        throw new AssertionError("Invalid resolution phase string " + s);
    }
}

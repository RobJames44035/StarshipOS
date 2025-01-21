/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

// key: compiler.err.type.annotation.inadmissible
// key: compiler.misc.type.annotation

import java.lang.annotation.*;

class CantAnnotateScoping {
    @Target(ElementType.TYPE_USE)
    @interface TA {}
    @Target(ElementType.TYPE_USE)
    @interface TB {}

    interface Outer {
        interface Inner {}
    }

    // Error:
    @TA @TB Outer.Inner f;

    // OK:
    @TA @TB Outer g;
}

/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

// key: compiler.err.invalid.repeatable.annotation.elem.nondefault

import java.lang.annotation.*;

@Repeatable(Annos.class)
@interface Anno { }

@interface Annos { Anno[] value(); String foo(); }

class RepeatableNonDefault { }

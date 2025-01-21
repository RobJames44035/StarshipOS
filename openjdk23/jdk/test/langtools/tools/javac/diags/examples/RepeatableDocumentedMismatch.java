/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

// key: compiler.err.invalid.repeatable.annotation.not.documented

import java.lang.annotation.*;

@Documented
@Repeatable(Annos.class)
@interface Anno { }

@interface Annos { Anno[] value(); }

@Anno
@Anno
class RepeatableDocumentedMismatch { }

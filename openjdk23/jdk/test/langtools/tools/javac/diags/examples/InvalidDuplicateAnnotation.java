/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

// key: compiler.err.duplicate.annotation.invalid.repeated
// key: compiler.err.invalid.repeatable.annotation.elem.nondefault

// We need an almost valid containing annotation. The easiest way to get
// one close enough to valid is by forgetting a default.

import java.lang.annotation.*;

@Repeatable(Annos.class)
@interface Anno { }

@interface Annos { Anno[] value(); String foo(); }

@Anno
@Anno
class InvalidDuplicateAnnotation { }

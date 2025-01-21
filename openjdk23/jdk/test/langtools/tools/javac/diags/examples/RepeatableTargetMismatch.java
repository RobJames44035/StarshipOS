/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

// key: compiler.err.invalid.repeatable.annotation.incompatible.target

import java.lang.annotation.*;

@Repeatable(Annos.class)
@Target(ElementType.METHOD)
@interface Anno { }

@interface Annos { Anno[] value(); }

class RepeatableTargetMismatch { }

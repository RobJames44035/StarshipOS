/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;


/**
 * At the JVM level, the ACC_STRICT bit is only defined for
 * methods/constructors, unlike where strictfp can be applied in
 * sources.
 */
@Retention(RUNTIME)
@Target({METHOD, CONSTRUCTOR})
public @interface StrictfpInSource {}

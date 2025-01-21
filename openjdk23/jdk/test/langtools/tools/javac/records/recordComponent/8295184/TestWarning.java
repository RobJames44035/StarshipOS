/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Direct {@link ReproducingAP} to emit a warning.
 */
@Target({ElementType.TYPE, ElementType.RECORD_COMPONENT})
public @interface TestWarning {
    /**
     * {@return {@code true} to include the relevant mirror in the warning message}
     */
    boolean includeAnnotation() default false;
}

/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package com.sun.swingset3;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation type for specifying meta-data about Demo
 *
 * @author aim
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DemoProperties {

    String value(); // Name

    String category();

    String description();

    String iconFile() default "";

    String[] sourceFiles() default "";
}

/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package jdk.internal.javac;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates, when declared on a module declaration, that the module participates
 * in preview features and therefore does not need to be compiled with "--enable-preview".
 */
@Target(ElementType.MODULE)
@Retention(RetentionPolicy.CLASS)
public @interface ParticipatesInPreview {
}

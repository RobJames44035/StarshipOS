/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */
package jdk.vm.ci.services;

/**
 * Marker interface for methods which are called from the JVM.
 */
@interface VMEntryPoint {
    /**
     * An optional comment describing the caller.
     */
    String value() default "";
}

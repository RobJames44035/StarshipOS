/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package jdk.internal.vm.annotation;

import java.lang.annotation.*;

/**
 * A method or constructor may be annotated as "hidden" to hint it is desirable
 * to omit it from stack traces.
 *
 * @implNote
 * This annotation only takes effect for methods or constructors of classes
 * loaded by the boot loader.  Annotations on methods or constructors of classes
 * loaded outside of the boot loader are ignored.
 *
 * <p>HotSpot JVM provides diagnostic option {@code -XX:+ShowHiddenFrames} to
 * always show "hidden" frames.
 */
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface Hidden {
}

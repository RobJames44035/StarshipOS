/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package jdk.internal.joptsimple.internal;

/**
 * This unchecked exception wraps reflection-oriented exceptions.
 *
 * @author <a href="mailto:pholser@alumni.rice.edu">Paul Holser</a>
 */
public class ReflectionException extends RuntimeException {
    private static final long serialVersionUID = -2L;

    ReflectionException( Throwable cause ) {
        super( cause );
    }
}

/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
package java.util.concurrent;

import jdk.internal.javac.PreviewFeature;

/**
 * Thrown when a structure violation is detected.
 *
 * @see StructuredTaskScope#close()
 *
 * @since 21
 */
@PreviewFeature(feature = PreviewFeature.Feature.STRUCTURED_CONCURRENCY)
public final class StructureViolationException extends RuntimeException {
    @java.io.Serial
    private static final long serialVersionUID = -7705327650798235468L;

    /**
     * Constructs a {@code StructureViolationException} with no detail message.
     */
    public StructureViolationException() {
        super();
    }

    /**
     * Constructs a {@code StructureViolationException} with the specified
     * detail message.
     *
     * @param  message the detail message, can be null
     */
    public StructureViolationException(String message) {
        super(message);
    }
}

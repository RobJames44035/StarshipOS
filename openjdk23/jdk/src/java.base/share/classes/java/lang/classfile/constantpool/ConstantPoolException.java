/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
package java.lang.classfile.constantpool;


/**
 * Thrown to indicate that requested entry cannot be obtained from the constant
 * pool or the bootstrap method table.  This is also thrown when the lazy
 * evaluation of constant pool or bootstrap method table entries encounter
 * format errors.
 *
 * @since 24
 */
public class ConstantPoolException extends IllegalArgumentException {

    @java.io.Serial
    private static final long serialVersionUID = 7245472922409094120L;

    /**
     * Constructs a {@code ConstantPoolException} with no detail message.
     */
    public ConstantPoolException() {
        super();
    }

    /**
     * Constructs a {@code ConstantPoolException} with the specified detail
     * message.
     *
     * @param message the detail message, may be {@code null} for no detail
     *                message
     */
    public ConstantPoolException(String message) {
        super(message);
    }

    /**
     * Constructs a {@code ConstantPoolException} with the specified cause and
     * a detail message of {@code cause == null ? null : cause.toString()}.
     *
     * @param cause the cause, may be {@code null} for nonexistent or unknown
     *              cause
     */
    public ConstantPoolException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a {@code ConstantPoolException} with the specified detail
     * message and cause.
     *
     * @param message the detail message, may be {@code null} for no detail
     *                message
     * @param cause the cause, may be {@code null} for nonexistent or unknown
     *              cause
     */
    public ConstantPoolException(String message, Throwable cause) {
        super(message, cause);
    }
}

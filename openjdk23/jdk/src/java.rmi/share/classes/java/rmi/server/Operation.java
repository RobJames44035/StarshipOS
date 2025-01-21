/*
 * StarshipOS Copyright (c) 1996-2025. R.A. James
 */

package java.rmi.server;

/**
 * An <code>Operation</code> contains a description of a Java method.
 * <code>Operation</code> objects were used in JDK1.1 version stubs and
 * skeletons. The <code>Operation</code> class is not needed for 1.2 style
 * stubs; hence, this class is deprecated.
 *
 * @since 1.1
 * @deprecated no replacement
 */
@Deprecated
public class Operation {
    private String operation;

    /**
     * Creates a new Operation object.
     * @param op method name
     * @deprecated no replacement
     * @since 1.1
     */
    @Deprecated
    public Operation(String op) {
        operation = op;
    }

    /**
     * Returns the name of the method.
     * @return method name
     * @deprecated no replacement
     * @since 1.1
     */
    @Deprecated
    public String getOperation() {
        return operation;
    }

    /**
     * Returns the string representation of the operation.
     * @deprecated no replacement
     * @since 1.1
     */
    @Deprecated
    public String toString() {
        return operation;
    }
}

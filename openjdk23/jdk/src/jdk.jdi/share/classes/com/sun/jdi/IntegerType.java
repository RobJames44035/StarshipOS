/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

package com.sun.jdi;

/**
 * The type of all primitive <code>int</code> values
 * accessed in the target VM. Calls to {@link Value#type} will return an
 * implementor of this interface.
 *
 * @see IntegerValue
 *
 * @author James McIlree
 * @since  1.3
 */
public interface IntegerType extends PrimitiveType {
}

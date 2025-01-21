/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

package com.sun.jdi;

/**
 * The type of all primitive <code>void</code> values
 * accessed in the target VM. Calls to {@link Value#type} will return an
 * implementor of this interface.
 *
 * @see VoidValue
 *
 * @author Robert Field
 * @since  1.3
 */
public interface VoidType extends Type {
}

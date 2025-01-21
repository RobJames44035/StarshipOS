/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * This file is available under and governed by the GNU General Public
 * License version 2 only, as published by the Free Software Foundation.
 * However, the following notice accompanied the original version of this
 * file, and Oracle licenses the original version of this file under the BSD
 * license:
 */
package jdk.dynalink;

/**
 * Defines the standard dynamic operations. The operations {@link #GET} and {@link #SET} must
 * be used as part of a {@link NamespaceOperation}. {@link NamedOperation} can then be further used on these
 * {@link NamespaceOperation}s to bind the name parameter of {@link #GET} and {@link #SET} operations, in which case it
 * disappears from their type signature.
 * {@link NamedOperation} can also be used to decorate {@link #CALL} and {@link #NEW} operations with a
 * diagnostic name, and as such it does not affect their type signature.
 * @since 9
 */
public enum StandardOperation implements Operation {
    /**
     * Get the value from a namespace defined on an object. Call sites with this
     * operation should have a signature of
     * <code>(receiver,&nbsp;name)&rarr;value</code> or
     * <code>(receiver)&rarr;value</code> when used with {@link NamedOperation}, with
     * all parameters and return type being of any type (either primitive or
     * reference). This operation must always be used as part of a {@link NamespaceOperation}.
     */
    GET,
    /**
     * Set the value in a namespace defined on an object. Call sites with this
     * operation should have a signature of
     * <code>(receiver,&nbsp;name,&nbsp;value)&rarr;void</code> or
     * <code>(receiver,&nbsp;value)&rarr;void</code> when used with {@link NamedOperation},
     * with all parameters and return type being of any type (either primitive
     * or reference). This operation must always be used as part of a {@link NamespaceOperation}.
     */
    SET,
    /**
     * Removes the value from a namespace defined on an object. Call sites with this
     * operation should have a signature of
     * <code>(receiver,&nbsp;name)&rarr;void</code> or
     * <code>(receiver)&rarr;void</code> when used with {@link NamedOperation},
     * with all parameters being of any type (either primitive
     * or reference). This operation must always be used as part of a {@link NamespaceOperation}.
     *
     * @since 10
     */
    REMOVE,
    /**
     * Call a callable object. Call sites with this operation should have a
     * signature of <code>(callable,&nbsp;receiver,&nbsp;arguments...)&rarr;value</code>,
     * with all parameters and return type being of any type (either primitive or
     * reference). Typically, the callables are presumed to be methods of an object, so
     * an explicit receiver value is always passed to the callable before the arguments.
     * If a callable has no concept of a receiver, it is free to ignore the value of the
     * receiver argument.
     * The {@code CALL} operation is allowed to be used with a
     * {@link NamedOperation} even though it does not take a name. Using it with
     * a named operation won't affect its signature; the name is solely meant to
     * be used as a diagnostic description for error messages.
     */
    CALL,
    /**
     * Call a constructor object. Call sites with this operation should have a
     * signature of <code>(constructor,&nbsp;arguments...)&rarr;value</code>, with all
     * parameters and return type being of any type (either primitive or
     * reference). The {@code NEW} operation is allowed to be used with a
     * {@link NamedOperation} even though it does not take a name. Using it with
     * a named operation won't affect its signature; the name is solely meant to
     * be used as a diagnostic description for error messages.
     */
    NEW
}

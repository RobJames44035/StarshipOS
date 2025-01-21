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
package jdk.dynalink.linker;

import jdk.dynalink.DynamicLinkerFactory;

/**
 * Interface for objects that are used to transform one guarded invocation into
 * another one. Typical usage is for implementing
 * {@link DynamicLinkerFactory#setPrelinkTransformer(GuardedInvocationTransformer)
 * pre-link transformers}.
 * @since 9
 */
@FunctionalInterface
public interface GuardedInvocationTransformer {
    /**
     * Given a guarded invocation, return either the same or potentially
     * different guarded invocation.
     * @param inv the original guarded invocation.
     * @param linkRequest the link request for which the invocation was
     * generated (usually by some linker).
     * @param linkerServices the linker services that can be used during
     * creation of a new invocation.
     * @return either the passed guarded invocation or a different one, with
     * the difference usually determined based on information in the link
     * request and the differing invocation created with the assistance of the
     * linker services. Whether or not {@code null} is an accepted return value
     * is dependent on the user of the filter.
     * @throws NullPointerException is allowed to be thrown by implementations
     * if any of the passed arguments is null.
     */
    public GuardedInvocation filter(GuardedInvocation inv, LinkRequest linkRequest, LinkerServices linkerServices);
}

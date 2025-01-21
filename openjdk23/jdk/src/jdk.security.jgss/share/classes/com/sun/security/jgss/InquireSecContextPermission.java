/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

package com.sun.security.jgss;

import java.security.BasicPermission;

/**
 * This class is for GSS security context permissions.
 *
 * <p>The target name is the {@link InquireType} allowed.
 *
 * @apiNote
 * This permission cannot be used for controlling access to resources
 * as the Security Manager is no longer supported.
 *
 * @since 1.7
 */
public final class InquireSecContextPermission extends BasicPermission {
    private static final long serialVersionUID = -7131173349668647297L;

    /**
     * Constructs a new {@code InquireSecContextPermission} object with
     * the specified name. The name is the symbolic name of the
     * {@link InquireType} allowed.
     *
     * @param name the {@link InquireType} allowed by this
     * permission. "*" means all {@link InquireType}s are allowed.
     *
     * @throws NullPointerException if <code>name</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>name</code> is empty.
     */
    public InquireSecContextPermission(String name) {
        super(name);
    }
}

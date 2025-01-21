/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package com.sun.jdi;

/**
 * A {@code JDIPermission} object contains a name (also referred
 * to as a "target name") but no actions list; you either have the
 * named permission or you don't.
 *
 * @apiNote
 * This permission cannot be used for controlling access to resources
 * as the Security Manager is no longer supported.
 *
 * @author  Tim Bell
 * @since   1.5
 *
 * @see Bootstrap
 * @see java.security.BasicPermission
 * @see java.security.Permission
 * @see java.security.Permissions
 * @see java.security.PermissionCollection
 * @see java.lang.SecurityManager
 *
 */

public final class JDIPermission extends java.security.BasicPermission {

    private static final long serialVersionUID = -6988461416938786271L;

    /**
     * The {@code JDIPermission} class represents access rights to the
     * {@code VirtualMachineManager}
     * @param name Permission name. Must be "virtualMachineManager".
     * @throws IllegalArgumentException if the name argument is invalid.
     */
    public JDIPermission(String name) {
        super(name);
        if (!name.equals("virtualMachineManager")) {
            throw new IllegalArgumentException("name: " + name);
        }
    }

    /**
     * Constructs a new JDIPermission object.
     *
     * @param name Permission name. Must be "virtualMachineManager".
     * @param actions Must be either null or the empty string.
     * @throws IllegalArgumentException if arguments are invalid.
     */
    public JDIPermission(String name, String actions)
        throws IllegalArgumentException {
        super(name);
        if (!name.equals("virtualMachineManager")) {
            throw new IllegalArgumentException("name: " + name);
        }
        if (actions != null && actions.length() > 0) {
            throw new IllegalArgumentException("actions: " + actions);
        }
    }
}

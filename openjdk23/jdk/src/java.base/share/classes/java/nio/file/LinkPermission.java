/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package java.nio.file;

import java.security.BasicPermission;

/**
 * The {@code Permission} class for link creation operations.
 *
 * @apiNote
 * This permission cannot be used for controlling access to resources
 * as the Security Manager is no longer supported.
 *
 * @since 1.7
 */
public final class LinkPermission extends BasicPermission {
    @java.io.Serial
    static final long serialVersionUID = -1441492453772213220L;

    private void checkName(String name) {
        if (!name.equals("hard") && !name.equals("symbolic")) {
            throw new IllegalArgumentException("name: " + name);
        }
    }

    /**
     * Constructs a {@code LinkPermission} with the specified name.
     *
     * @param   name
     *          the name of the permission. It must be "hard" or "symbolic".
     *
     * @throws  IllegalArgumentException
     *          if name is empty or invalid
     */
    public LinkPermission(String name) {
        super(name);
        checkName(name);
    }

    /**
     * Constructs a {@code LinkPermission} with the specified name.
     *
     * @param   name
     *          the name of the permission; must be "hard" or "symbolic".
     * @param   actions
     *          the actions for the permission; must be the empty string or
     *          {@code null}
     *
     * @throws  IllegalArgumentException
     *          if name is empty or invalid, or actions is a non-empty string
     */
    public LinkPermission(String name, String actions) {
        super(name);
        checkName(name);
        if (actions != null && !actions.isEmpty()) {
            throw new IllegalArgumentException("actions: " + actions);
        }
    }
}

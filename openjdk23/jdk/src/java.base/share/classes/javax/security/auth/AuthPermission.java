/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

package javax.security.auth;

/**
 * This class is for authentication permissions. An {@code AuthPermission}
 * contains a name (also referred to as a "target name") but no actions
 * list; you either have the named permission or you don't.
 *
 * @apiNote
 * This permission cannot be used for controlling access to resources
 * as the Security Manager is no longer supported.
 *
 * @since 1.4
 */
public final class AuthPermission extends
java.security.BasicPermission {

    @java.io.Serial
    private static final long serialVersionUID = 5806031445061587174L;

    /**
     * Creates a new AuthPermission with the specified name.
     * The name is the symbolic name of the AuthPermission.
     *
     * @param name the name of the AuthPermission
     *
     * @throws NullPointerException if {@code name} is {@code null}.
     * @throws IllegalArgumentException if {@code name} is empty.
     */
    public AuthPermission(String name) {
        // for backwards compatibility --
        // createLoginContext is deprecated in favor of createLoginContext.*
        super("createLoginContext".equals(name) ?
                "createLoginContext.*" : name);
    }

    /**
     * Creates a new AuthPermission object with the specified name.
     * The name is the symbolic name of the AuthPermission, and the
     * actions String is currently unused and should be null.
     *
     * @param name the name of the AuthPermission
     *
     * @param actions should be null.
     *
     * @throws NullPointerException if {@code name} is {@code null}.
     * @throws IllegalArgumentException if {@code name} is empty.
     */
    public AuthPermission(String name, String actions) {
        // for backwards compatibility --
        // createLoginContext is deprecated in favor of createLoginContext.*
        super("createLoginContext".equals(name) ?
                "createLoginContext.*" : name, actions);
    }
}

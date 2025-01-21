/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package java.nio.file.attribute;

import java.io.IOException;

/**
 * Checked exception thrown when a lookup of {@link UserPrincipal} fails because
 * the principal does not exist.
 *
 * @since 1.7
 */

public class UserPrincipalNotFoundException
    extends IOException
{
    @java.io.Serial
    static final long serialVersionUID = -5369283889045833024L;

    /**
     * @serial The user principal name.
     */
    private final String name;

    /**
     * Constructs an instance of this class.
     *
     * @param   name
     *          the principal name; may be {@code null}
     */
    public UserPrincipalNotFoundException(String name) {
        super();
        this.name = name;
    }

    /**
     * Returns the user principal name if this exception was created with the
     * user principal name that was not found, otherwise {@code null}.
     *
     * @return  the user principal name or {@code null}
     */
    public String getName() {
        return name;
    }
}

/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

package javax.security.auth;

/**
 * Objects such as credentials may optionally implement this
 * interface to provide the capability to refresh itself.
 * For example, a credential with a particular time-restricted lifespan
 * may implement this interface to allow callers to refresh the time period
 * for which it is valid.
 *
 * @since 1.4
 * @see javax.security.auth.Subject
 */
public interface Refreshable {

    /**
     * Determine if this {@code Object} is current.
     *
     * @return true if this {@code Object} is currently current,
     *          false otherwise.
     */
    boolean isCurrent();

    /**
     * Update or extend the validity period for this
     * {@code Object}.
     *
     * @exception RefreshFailedException if the refresh attempt failed.
     */
    void refresh() throws RefreshFailedException;
}

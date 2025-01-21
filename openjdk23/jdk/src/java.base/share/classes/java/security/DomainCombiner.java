/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

package java.security;

/**
 * {@code DomainCombiner} was used to dynamically update
 * {@code ProtectionDomain}s for access control operations and decisions. This
 * feature no longer exists.
 *
 * @since 1.3
 * @deprecated This class was only useful in conjunction with {@linkplain
 *       SecurityManager the Security Manager}, which is no longer supported.
 *       There is no replacement for the Security Manager or this class.
 */
@Deprecated(since="17", forRemoval=true)
public interface DomainCombiner {

    /**
     * Modify or update the provided ProtectionDomains.
     * ProtectionDomains may be added to or removed from the given
     * ProtectionDomains.  The ProtectionDomains may be re-ordered.
     * Individual ProtectionDomains may be modified (with a new
     * set of Permissions, for example).
     *
     * @param currentDomains the ProtectionDomains associated with the
     *          current execution thread.
     *          The ProtectionDomains are listed in order of execution,
     *          with the most recently executing {@code ProtectionDomain}
     *          residing at the beginning of the array. This parameter may
     *          be {@code null} if the current execution thread
     *          has no associated ProtectionDomains.
     *
     * @param assignedDomains an array of inherited ProtectionDomains.
     *          This parameter may be {@code null}
     *          if there are no inherited ProtectionDomains.
     *
     * @return a new array consisting of the updated ProtectionDomains,
     *          or {@code null}.
     */
    ProtectionDomain[] combine(ProtectionDomain[] currentDomains,
                                ProtectionDomain[] assignedDomains);
}

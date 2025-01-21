/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */
package com.sun.nio.sctp;

/**
 * A notification from the SCTP stack.
 *
 * <P> Objects of this type are passed to the {@link NotificationHandler} when
 * a notification is received.
 *
 * <P> An SCTP channel supports the following notifications: {@link
 * AssociationChangeNotification}, {@link PeerAddressChangeNotification},
 * {@link SendFailedNotification}, {@link ShutdownNotification}, and may support
 * additional implementation specific notifications.
 *
 * @since 1.7
 */
public interface Notification {
    /**
     * Returns the association that this notification is applicable to.
     *
     * @return  The association
     */
    public Association association();
}

/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */
package com.sun.nio.sctp;

/**
 * Notification emitted when a peer shuts down an association.
 *
 * <P> When a peer sends a <i>SHUTDOWN</i>, the SCTP stack delivers this
 * notification to inform the application that it should cease sending data.
 *
 * @since 1.7
 */
public abstract class ShutdownNotification implements Notification {
    /**
     * Initializes a new instance of this class.
     */
    protected ShutdownNotification() {}

    /**
     * Returns the association that this notification is applicable to.
     *
     * @return  The association that received the shutdown
     */
    public abstract Association association();
}

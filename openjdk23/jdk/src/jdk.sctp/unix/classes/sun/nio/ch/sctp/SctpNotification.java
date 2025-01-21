/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */
package sun.nio.ch.sctp;

import com.sun.nio.sctp.Association;
import com.sun.nio.sctp.Notification;

/**
 * All Notification implementations MUST implement this interface to provide
 * access to the native association identifier.
 */
interface SctpNotification extends Notification {
    int assocId();
    void setAssociation(Association association);
}

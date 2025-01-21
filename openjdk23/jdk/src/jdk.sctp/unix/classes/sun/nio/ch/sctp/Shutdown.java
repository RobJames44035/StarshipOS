/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */
package sun.nio.ch.sctp;

import com.sun.nio.sctp.Association;
import com.sun.nio.sctp.ShutdownNotification;

/**
 * An implementation of ShutdownNotification
 */
public class Shutdown extends ShutdownNotification
    implements SctpNotification
{
    private Association association;
    /* assocId is used to look up the association before the notification is
     * returned to user code */
    private final int assocId;

    /* Invoked from native */
    private Shutdown(int assocId) {
        this.assocId = assocId;
    }

    @Override
    public int assocId() {
        return assocId;
    }

    @Override
    public void setAssociation(Association association) {
        this.association = association;
    }

    @Override
    public Association association() {
        assert association != null;
        return association;
    }

    @Override
    public String toString() {
        return super.toString() + " [" +
                "Association:" + association + "]";
    }
}

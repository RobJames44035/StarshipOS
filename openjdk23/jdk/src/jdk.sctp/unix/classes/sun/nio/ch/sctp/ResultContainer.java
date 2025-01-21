/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */
package sun.nio.ch.sctp;

import java.lang.annotation.Native;

/**
 * Wraps the actual message or notification so that it can be
 * set and returned from the native receive implementation.
 */
public class ResultContainer {
    /* static final ints so that they can be referenced from native */
    @Native static final int NOTHING = 0;
    @Native static final int MESSAGE = 1;
    @Native static final int SEND_FAILED = 2;
    @Native static final int ASSOCIATION_CHANGED = 3;
    @Native static final int PEER_ADDRESS_CHANGED = 4;
    @Native static final int SHUTDOWN = 5;

    private Object value;
    private int type;

    int type() {
        return type;
    }

    boolean hasSomething() {
        return type() != NOTHING;
    }

    boolean isNotification() {
        return type() != MESSAGE && type() != NOTHING;
    }

    void clear() {
        type = NOTHING;
        value = null;
    }

    SctpNotification notification() {
        assert type() != MESSAGE && type() != NOTHING;

        return (SctpNotification) value;
    }

    MessageInfoImpl getMessageInfo() {
        assert type() == MESSAGE;

        if (value instanceof MessageInfoImpl messageInfo)
            return messageInfo;

        return null;
    }

    SendFailed getSendFailed() {
        assert type() == SEND_FAILED;

        if (value instanceof SendFailed sendFailed)
            return sendFailed;

        return null;
    }

    AssociationChange getAssociationChanged() {
        assert type() == ASSOCIATION_CHANGED;

        if (value instanceof AssociationChange associationChanged)
            return associationChanged;

        return null;
    }

    PeerAddrChange getPeerAddressChanged() {
        assert type() == PEER_ADDRESS_CHANGED;

        if (value instanceof PeerAddrChange peerAddressChanged)
            return peerAddressChanged;

        return null;
    }

    Shutdown getShutdown() {
        assert type() == SHUTDOWN;

        if (value instanceof Shutdown shutdown)
            return shutdown;

        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Type: ");
        switch (type) {
            case NOTHING              -> sb.append("NOTHING");
            case MESSAGE              -> sb.append("MESSAGE");
            case SEND_FAILED          -> sb.append("SEND FAILED");
            case ASSOCIATION_CHANGED  -> sb.append("ASSOCIATION CHANGE");
            case PEER_ADDRESS_CHANGED -> sb.append("PEER ADDRESS CHANGE");
            case SHUTDOWN             -> sb.append("SHUTDOWN");
            default                   -> sb.append("Unknown result type");
        }
        sb.append(", Value: ");
        sb.append((value == null) ? "null" : value.toString());
        return sb.toString();
    }
}

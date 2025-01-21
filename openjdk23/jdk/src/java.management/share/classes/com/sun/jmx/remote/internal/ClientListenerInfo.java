/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package com.sun.jmx.remote.internal;

import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectName;

/**
 * <p>An identified listener.  A listener has an Integer id that is
 * unique per connector server.  It selects notifications based on the
 * ObjectName of the originator and an optional
 * NotificationFilter.</p>
 */
public class ClientListenerInfo {
    public ClientListenerInfo(Integer listenerID,
                              ObjectName name,
                              NotificationListener listener,
                              NotificationFilter filter,
                              Object handback) {
        this.listenerID = listenerID;
        this.name = name;
        this.listener = listener;
        this.filter = filter;
        this.handback = handback;
    }

    public ObjectName getObjectName() {
        return name;
    }

    public Integer getListenerID() {
        return listenerID;
    }

    public NotificationFilter getNotificationFilter() {
        return filter;
    }

    public NotificationListener getListener() {
        return listener;
    }

    public Object getHandback() {
        return handback;
    }

    public boolean sameAs(ObjectName name) {
        return (getObjectName().equals(name));
    }


    public boolean sameAs(ObjectName name, NotificationListener listener) {
        return ( getObjectName().equals(name) &&
                 getListener() == listener);
    }


    public boolean sameAs(ObjectName name, NotificationListener listener, NotificationFilter filter, Object handback) {
        return ( getObjectName().equals(name) &&
                 getListener() == listener &&
                 getNotificationFilter() == filter &&
                 getHandback() == handback);
    }

    private final ObjectName name;
    private final Integer listenerID;
    private final NotificationFilter filter;

    private final NotificationListener listener;
    private final Object handback;
}

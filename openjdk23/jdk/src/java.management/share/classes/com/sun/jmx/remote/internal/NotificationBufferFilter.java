/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

package com.sun.jmx.remote.internal;

import java.util.List;
import javax.management.Notification;
import javax.management.ObjectName;
import javax.management.remote.TargetedNotification;

public interface NotificationBufferFilter {
    /**
     * Add the given notification coming from the given MBean to the list
     * iff it matches this filter's rules.
     */
    public void apply(List<TargetedNotification> targetedNotifs,
            ObjectName source, Notification notif);
}

/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/**
 * <p>Provides the definition of the Timer MBean.  A Timer MBean
 * maintains a list of scheduled notifications and, because it is a
 * {@link javax.management.NotificationBroadcaster
 * NotificationBroadcaster}, a list of listeners for those
 * notifications.  Whenever the time for one of the scheduled
 * notifications is reached, each listener receives the
 * notification.  Notifications can be repeated at a fixed
 * interval, and the number of repetitions can be bounded.</p>
 *
 * <p>A listener for a Timer MBean can itself be an MBean, using
 * the method {@link
 * javax.management.MBeanServer#addNotificationListener(ObjectName,
 * ObjectName, NotificationFilter, Object)}.  In this way, a
 * management application can create an MBean representing a task,
 * then schedule that task using a Timer MBean.</p>
 *
 * @since 1.5
 */
package javax.management.timer;

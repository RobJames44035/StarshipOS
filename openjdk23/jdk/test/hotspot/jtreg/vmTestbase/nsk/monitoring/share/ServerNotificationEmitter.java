/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package nsk.monitoring.share;

import nsk.share.*;
import nsk.monitoring.share.*;
import javax.management.*;

/**
 * NotificationEmitter that delegates functionality to MBeanServer.
 */
public class ServerNotificationEmitter implements NotificationEmitter {
        private MBeanServer mbeanServer;
        private ObjectName name;

        public ServerNotificationEmitter(MBeanServer mbeanServer, ObjectName name) {
                this.mbeanServer = mbeanServer;
                this.name = name;
        }

        public ServerNotificationEmitter(MBeanServer mbeanServer, String name) {
                try {
                        this.mbeanServer = mbeanServer;
                        this.name = new ObjectName(name);
                } catch (MalformedObjectNameException e) {
                        throw Monitoring.convertException(e);
                }
        }

        public void addNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback)  {
                try {
                        mbeanServer.addNotificationListener(name, listener, filter, handback);
                } catch (InstanceNotFoundException e) {
                        throw Monitoring.convertException(e);
                }
        }

        public MBeanNotificationInfo[] getNotificationInfo() {
                throw new TestBug("ServerNotificationEmitter.getNotificationInfo() not implemented.");
        }

        public void removeNotificationListener(NotificationListener listener)
                throws ListenerNotFoundException {
                try {
                        mbeanServer.removeNotificationListener(name, listener);
                } catch (InstanceNotFoundException e) {
                        throw Monitoring.convertException(e);
                }
        }

        public void removeNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback)
                throws ListenerNotFoundException {
                try {
                        mbeanServer.removeNotificationListener(name, listener, filter, handback);
                } catch (InstanceNotFoundException e) {
                        throw Monitoring.convertException(e);
                }
        }
}

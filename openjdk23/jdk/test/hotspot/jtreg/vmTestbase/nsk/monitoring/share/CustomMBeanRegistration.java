/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.monitoring.share;

import javax.management.*;

/**
 * <code>CustomMBeanRegistration</code> class is a dummy implementation of
 * <code>MBeanRegistration</code> interface.
 *
 * @see javax.management.MBeanRegistration
 */
class CustomMBeanRegistration implements MBeanRegistration {
    public void postDeregister() {}

    public void postRegister(Boolean registrationDone) {}

    public void preDeregister() {}

    public ObjectName preRegister(MBeanServer server, ObjectName name) {
        return name;
    }
}

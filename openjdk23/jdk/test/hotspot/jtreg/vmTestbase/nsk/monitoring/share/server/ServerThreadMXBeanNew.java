/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package nsk.monitoring.share.server;

import javax.management.*;
import com.sun.management.*;

/**
 * com.sun.management.ThreadMXBean implementation that delegates functionality to MBeanServer.
 */
public class ServerThreadMXBeanNew extends ServerThreadMXBean implements ThreadMXBean{

    public ServerThreadMXBeanNew(MBeanServer mbeanServer) {
        super(mbeanServer);
    }

    public long[] getThreadUserTime(long[] ids) {
        return (long[]) invokeMethod("getThreadUserTime",
                new Object[] { ids },
                new String[] { long[].class.getName() });
    }

    public long[] getThreadCpuTime(long[] ids) {
        return (long[]) invokeMethod("getThreadCpuTime",
                new Object[] { ids },
                new String[] { long[].class.getName() });
    }

    public long[] getThreadAllocatedBytes(long[] ids) {
        return (long[]) invokeMethod("getThreadAllocatedBytes",
                new Object[] { ids },
                new String[] { long[].class.getName() });
    }

    public long getThreadAllocatedBytes(long id) {
        return (Long) invokeMethod("getThreadAllocatedBytes",
            new Object[] { id },
            new String[] { long.class.getName() });
    }

    public long getCurrentThreadAllocatedBytes() {
        return getLongAttribute("CurrentThreadAllocatedBytes");
    }

    public void setThreadAllocatedMemoryEnabled(boolean enabled) {
        setBooleanAttribute("ThreadAllocatedMemoryEnabled", enabled);
    }

    public boolean isThreadAllocatedMemorySupported() {
        return getBooleanAttribute("ThreadAllocatedMemorySupported");
    }

    public boolean isThreadAllocatedMemoryEnabled() {
        return getBooleanAttribute("ThreadAllocatedMemoryEnabled");
    }
}

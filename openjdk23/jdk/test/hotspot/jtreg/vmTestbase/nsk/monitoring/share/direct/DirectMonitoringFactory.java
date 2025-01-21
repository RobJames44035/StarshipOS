/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package nsk.monitoring.share.direct;

import java.lang.management.*;
import javax.management.*;
import nsk.monitoring.share.*;
import java.util.List;
import java.lang.reflect.Method;

/**
 * This is MonitoringFactory implementation, which obtains
 * MXBeans directly from ManagementFactory.
 *
 * @see nsk.monitoring.share.MonitoringFactory
 */
public class DirectMonitoringFactory implements MonitoringFactory {
        public ClassLoadingMXBean getClassLoadingMXBean() {
                return ManagementFactory.getClassLoadingMXBean();
        }

        public boolean hasCompilationMXBean() {
                return ManagementFactory.getCompilationMXBean() != null;
        }

        public CompilationMXBean getCompilationMXBean() {
                return ManagementFactory.getCompilationMXBean();
        }

        public List<GarbageCollectorMXBean> getGarbageCollectorMXBeans() {
                return ManagementFactory.getGarbageCollectorMXBeans();
        }

        public RuntimeMXBean getRuntimeMXBean() {
                return ManagementFactory.getRuntimeMXBean();
        }

        public MemoryMXBean getMemoryMXBean() {
                return ManagementFactory.getMemoryMXBean();
        }

        public NotificationEmitter getMemoryMXBeanNotificationEmitter() {
                return (NotificationEmitter) ManagementFactory.getMemoryMXBean();
        }

        public List<MemoryPoolMXBean> getMemoryPoolMXBeans() {
                return ManagementFactory.getMemoryPoolMXBeans();
        }

        public ThreadMXBean getThreadMXBean() {
                return ManagementFactory.getThreadMXBean();
        }

        public boolean hasThreadMXBeanNew() {
            boolean supported = false;
            Class cl = ManagementFactory.getThreadMXBean().getClass();
            Method[] methods = cl.getDeclaredMethods();
            for (int i = 0; i < methods.length; i++ ) {
                if (methods[i].getName().equals("isThreadAllocatedMemorySupported")) {
                    supported = true;
                    break;
                }
            }
            return supported;
        }

        public ThreadMXBean getThreadMXBeanNew() {
            return getThreadMXBean();
        }
        /*
        public OperatingSystemMXBean getOperatingSystemMXBean() {
                return ManagementFactory.getOperatingSystemMXBean();
        }

        */
}

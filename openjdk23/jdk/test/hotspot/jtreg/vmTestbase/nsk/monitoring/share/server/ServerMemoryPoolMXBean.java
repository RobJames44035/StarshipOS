/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package nsk.monitoring.share.server;

import java.util.*;
import nsk.monitoring.share.*;
import javax.management.*;
import java.lang.management.*;

/**
 * MemoryPoolMXBean implementation that delegates functionality to MBeanServer.
 */
public class ServerMemoryPoolMXBean extends ServerMXBean implements MemoryPoolMXBean {
        public ServerMemoryPoolMXBean(MBeanServer mbeanServer, String name) {
                super(mbeanServer, name);
        }

        public ServerMemoryPoolMXBean(MBeanServer mbeanServer, ObjectName name) {
                super(mbeanServer, name);
        }

        public MemoryUsage getCollectionUsage() {
                return getMemoryUsageAttribute("CollectionUsage");
        }

        public long getCollectionUsageThreshold() {
                return getLongAttribute("CollectionUsageThreshold");
        }

        public long getCollectionUsageThresholdCount() {
                return getLongAttribute("CollectionUsageThresholdCount");
        }

        public String[] getMemoryManagerNames() {
                return getStringArrayAttribute("MemoryManagerNames");
        }

        public String getName() {
                return getStringAttribute("Name");
        }

        public MemoryUsage getPeakUsage() {
                return getMemoryUsageAttribute("PeakUsage");
        }

        public MemoryType getType() {
                return getMemoryTypeAttribute("MemoryType");
        }

        public MemoryUsage getUsage() {
                return getMemoryUsageAttribute("Usage");
        }

        public long getUsageThreshold() {
                return getLongAttribute("UsageThreshhold");
        }

        public long getUsageThresholdCount() {
                return getLongAttribute("UsageThreshholdCount");
        }

        public boolean isCollectionUsageThresholdExceeded() {
                return getBooleanAttribute("CollectionUsageThresholdExceeded");
        }

        public boolean isCollectionUsageThresholdSupported() {
                return getBooleanAttribute("CollectionUsageThresholdSupported");
        }

        public boolean isUsageThresholdExceeded() {
                return getBooleanAttribute("UsageThresholdExceeded");
        }

        public boolean isUsageThresholdSupported() {
                return getBooleanAttribute("UsageThresholdSupported");
        }

        public boolean isValid() {
                return getBooleanAttribute("Valid");
        }

        public void resetPeakUsage() {
                invokeVoidMethod("resetPeakUsage");
        }

        public void setCollectionUsageThreshold(long threshold) {
                setLongAttribute("CollectionUsageThreshold", threshold);
        }

        public void setUsageThreshold(long threshold) {
                setLongAttribute("UsageThreshold", threshold);
        }
}

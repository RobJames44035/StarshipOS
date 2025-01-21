/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package nsk.monitoring.share.server;

import javax.management.MBeanServer;
import java.lang.management.*;

public class ServerClassLoadingMXBean extends ServerMXBean implements ClassLoadingMXBean {
        private static final String LOADED_CLASSES = "LoadedClassCount";
        private static final String TOTAL_CLASSES = "TotalLoadedClassCount";
        private static final String UNLOADED_CLASSES = "UnloadedClassCount";
        private static final String VERBOSE = "Verbose";

        public ServerClassLoadingMXBean(MBeanServer mbeanServer) {
                super(mbeanServer, ManagementFactory.CLASS_LOADING_MXBEAN_NAME);
        }

        public int getLoadedClassCount() {
                return getIntAttribute(LOADED_CLASSES);
        }

        public long getTotalLoadedClassCount() {
                return getLongAttribute(TOTAL_CLASSES);
        }

        public long getUnloadedClassCount() {
                return getLongAttribute(UNLOADED_CLASSES);
        }

        public boolean isVerbose() {
                return getBooleanAttribute(VERBOSE);
        }

        public void setVerbose(boolean verbose) {
                setBooleanAttribute(VERBOSE, verbose);
        }
}

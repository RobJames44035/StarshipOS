/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package nsk.monitoring.share;

import nsk.share.test.*;
import nsk.share.runner.*;
import nsk.share.log.Log;
import java.lang.management.*;
import javax.management.*;
import nsk.monitoring.share.thread.RunningThread;
import nsk.monitoring.share.thread.SleepingThread;
import nsk.monitoring.share.thread.WaitingThread;
import nsk.monitoring.share.thread.BlockedThread;
import nsk.monitoring.share.thread.*;
import nsk.monitoring.share.direct.DirectMonitoringFactory;
import nsk.monitoring.share.server.ServerMonitoringFactory;
import nsk.monitoring.share.proxy.ProxyMonitoringFactory;
import nsk.share.TestBug;
import nsk.share.Failure;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

public class Monitoring extends nsk.share.test.Tests {
        private Monitoring() {
        }

        /**
         * Convert monitoring exception.
         *
         * @param e exception
         * @return converted exception
         */
        public static RuntimeException convertException(Throwable e) {
                //e.printStackTrace(logger.getOutStream());
                return new Failure(e);
        }

        protected static class MonitoringTestRunner extends TestRunner {
                private ArgumentHandler argHandler;

                public MonitoringTestRunner(Test test, String[] args) {
                        super(test, args);
                }

                public synchronized ArgumentHandler getArgumentHandler(String[] args) {
                        if (argHandler == null)
                                argHandler = new ArgumentHandler(args);
                        return argHandler;
                }

                public synchronized MonitoringFactory getMonitoringFactory(String testMode, String serverType) {
                        if (testMode.equals(ArgumentHandler.DIRECTLY_MODE))
                                return new DirectMonitoringFactory();
                        else if (testMode.equals(ArgumentHandler.SERVER_MODE))
                                return new ServerMonitoringFactory(getMBeanServer(serverType));
                        else if (testMode.equals(ArgumentHandler.PROXY_MODE))
                                return new ProxyMonitoringFactory(getMBeanServer(serverType));
                        else
                                throw new TestBug("Unknown test mode" + testMode);
                }

                public synchronized MBeanServer getMBeanServer(String serverType) {
                        if (serverType.equals(ArgumentHandler.DEFAULT_TYPE))
                                return ManagementFactory.getPlatformMBeanServer();
                        else {
                                System.setProperty(CustomMBeanServer.SERVER_BUILDER_PROPERTY, CustomMBeanServer.CUSTOM_SERVER_BUILDER);
                                return ManagementFactory.getPlatformMBeanServer();
                        }
                }


                public void configure(Object o) {
                        super.configure(o);
                        if (o instanceof ArgumentHandlerAware)
                                ((ArgumentHandlerAware) o).setArgumentHandler(getArgumentHandler(args));
                        if (o instanceof MonitoringFactoryAware) {
                                ArgumentHandler argHandler = getArgumentHandler(args);
                                MonitoringFactory mfactory = getMonitoringFactory(argHandler.getTestMode(), argHandler.getServerType());
                                ((MonitoringFactoryAware) o).setMonitoringFactory(mfactory);
                        }
                        if (o instanceof ScenarioTypeAware) {
                                ArgumentHandler argHandler = getArgumentHandler(args);
                                ((ScenarioTypeAware) o).setScenarioType(argHandler.getScenarioType());
                        }
                }
        }

        public static void runTest(Test test, String[] args) {
                new MonitoringTestRunner(test, args).run();
        }

        public static Collection<ObjectName> queryNamesByStart(MBeanServer mbeanServer, String name) {
                try {
                        ObjectName pattern = ObjectName.getInstance(name + "*");
                        Set<ObjectName> query = mbeanServer.queryNames(pattern, null);
                        List<ObjectName> list = new ArrayList<ObjectName>(query.size());
                        for (ObjectName oname : query) {
                            list.add(oname);
                        }
                        return list;
                } catch (Exception e) {
                        throw convertException(e);
                }
        }
}

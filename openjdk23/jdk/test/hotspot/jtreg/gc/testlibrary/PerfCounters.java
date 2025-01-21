/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package gc.testlibrary;

import sun.jvmstat.monitor.Monitor;
import sun.jvmstat.monitor.MonitorException;
import sun.jvmstat.monitor.MonitoredHost;
import sun.jvmstat.monitor.MonitoredVm;
import sun.jvmstat.monitor.VmIdentifier;
import jdk.test.lib.process.ProcessTools;

/**
 * PerfCounters can be used to get a performance counter from the currently
 * executing VM.
 *
 * Throws a runtime exception if an error occurs while communicating with the
 * currently executing VM.
 */
public class PerfCounters {
    private final static MonitoredVm vm;

    static {
        try {
            String pid = Long.toString(ProcessTools.getProcessId());
            VmIdentifier vmId = new VmIdentifier(pid);
            MonitoredHost host = MonitoredHost.getMonitoredHost(vmId);
            vm = host.getMonitoredVm(vmId);
        } catch (Exception e) {
            throw new RuntimeException("Could not connect to the VM");
        }
    }

    /**
     * Returns the performance counter with the given name.
     *
     * @param name The name of the performance counter.
     * @throws IllegalArgumentException If no counter with the given name exists.
     * @throws MonitorException If an error occurs while communicating with the VM.
     * @return The performance counter with the given name.
     */
    public static PerfCounter findByName(String name)
        throws MonitorException, IllegalArgumentException {
        Monitor m = vm.findByName(name);
        if (m == null) {
            throw new IllegalArgumentException("Did not find a performance counter with name " + name);
        }
        return new PerfCounter(m, name);
    }
}

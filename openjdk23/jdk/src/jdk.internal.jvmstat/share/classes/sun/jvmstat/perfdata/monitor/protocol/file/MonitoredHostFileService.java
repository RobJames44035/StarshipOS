/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package sun.jvmstat.perfdata.monitor.protocol.file;

import sun.jvmstat.monitor.HostIdentifier;
import sun.jvmstat.monitor.MonitorException;
import sun.jvmstat.monitor.MonitoredHost;
import sun.jvmstat.monitor.MonitoredHostService;

public final class MonitoredHostFileService implements MonitoredHostService {

    @Override
    public MonitoredHost getMonitoredHost(HostIdentifier hostId)
            throws MonitorException {
        return new MonitoredHostProvider(hostId);
    }

    @Override
    public String getScheme() {
        return "file";
    }

}

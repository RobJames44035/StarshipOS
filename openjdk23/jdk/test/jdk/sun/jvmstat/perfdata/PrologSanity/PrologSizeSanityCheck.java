/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 4990825
 * @summary prolog size and overflow sanity checks
 *
 * @run main/othervm -XX:+UsePerfData PrologSizeSanityCheck
 */

import sun.jvmstat.monitor.*;

public class PrologSizeSanityCheck {

    private static final String sizeName = "sun.perfdata.size";
    private static final String usedName = "sun.perfdata.used";
    private static final String overflowName = "sun.perfdata.overflow";
    private static final int K = 1024;

    public static void main(String args[]) throws Exception {

        VmIdentifier vmid = new VmIdentifier("0");
        MonitoredHost localhost = MonitoredHost.getMonitoredHost("localhost");
        MonitoredVm self = localhost.getMonitoredVm(vmid);

        IntegerMonitor prologSize = (IntegerMonitor)self.findByName(sizeName);
        IntegerMonitor prologUsed = (IntegerMonitor)self.findByName(usedName);
        IntegerMonitor prologOverflow =
                (IntegerMonitor)self.findByName(overflowName);

        if (prologOverflow.intValue() != 0) {
            throw new RuntimeException("jvmstat memory buffer overflow: "
                    + sizeName + "=" + prologSize.intValue()
                    + usedName + "=" + prologUsed.intValue()
                    + overflowName + "=" + prologOverflow.intValue()
                    + " : PerfDataMemorySize must be increased");
        }

        if (prologUsed.intValue() + 3*K >= prologSize.intValue()) {
          /*
           * we want to leave at least 3K of space to allow for long
           * string names in the various path oriented strings and for
           * the command line argument and vm argument strings. 3K is
           * somewhat of an arbitrary figure, but it is based on failure
           * scenarios observed in SQE when jvmstat was originally
           * introduced in 1.4.1.
           */
            throw new RuntimeException(
                   "jvmstat memory buffer usage approaching size: "
                    + sizeName + "=" + prologSize.intValue()
                    + usedName + "=" + prologUsed.intValue()
                    + " : consider increasing PerfDataMemorySize");
        }
    }
}

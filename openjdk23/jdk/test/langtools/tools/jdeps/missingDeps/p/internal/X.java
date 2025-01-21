/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package p.internal;

import java.lang.management.*;

class X implements q.T {
    public void upTime() {
        ManagementFactory.getRuntimeMXBean().getUptime();
    }
}

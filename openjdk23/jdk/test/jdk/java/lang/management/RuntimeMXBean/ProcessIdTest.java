/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8044122
 * @summary check the correctness of process ID returned by RuntimeMXBean.getPid()
 * @run main ProcessIdTest
 */

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.ProcessHandle;

public class ProcessIdTest {
    public static void main(String args[]) {
        RuntimeMXBean mbean = ManagementFactory.getRuntimeMXBean();
        long mbeanPid = mbean.getPid();
        long pid = ProcessHandle.current().pid();
        long pid1 = Long.parseLong(mbean.getName().split("@")[0]);
        if(mbeanPid != pid || mbeanPid != pid1) {
            throw new RuntimeException("Incorrect process ID returned");
        }

        System.out.println("Test Passed");
    }

}


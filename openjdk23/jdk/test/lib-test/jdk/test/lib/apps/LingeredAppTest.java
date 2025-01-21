/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @summary Unit test for LingeredApp
 * @library /test/lib
 * @build jdk.test.lib.apps.LingeredAppTest jdk.test.lib.apps.LingeredApp
 * @run main jdk.test.lib.apps.LingeredAppTest
 */

package jdk.test.lib.apps;

import java.io.IOException;
import java.util.ArrayList;

public class LingeredAppTest {

    public static void main(String[] args) {
        try {
            System.out.println("Starting LingeredApp with default parameters");

            ArrayList<String> cmd = new ArrayList<String>();

            // Propagate test.vm.options to LingeredApp, filter out possible empty options
            String testVmOpts[] = System.getProperty("test.vm.opts","").split("\\s+");
            for (String s : testVmOpts) {
                if (!s.equals("")) {
                    cmd.add(s);
                }
            }

            cmd.add("-XX:+PrintFlagsFinal");

            LingeredApp a = LingeredApp.startApp(cmd.toArray(new String[cmd.size()]));
            System.out.printf("App pid: %d\n", a.getPid());
            a.stopApp();

            System.out.println("App output:");
            int count = 0;
            for (String line : a.getOutput().getStdoutAsList()) {
                count += 1;
            }
            System.out.println("Found " + count + " lines in VM output");
            System.out.println("Test PASSED");
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Test ERROR");
            System.exit(3);
        }
    }
}

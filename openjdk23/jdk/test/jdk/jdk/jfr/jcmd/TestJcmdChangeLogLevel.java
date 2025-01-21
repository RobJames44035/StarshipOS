/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package jdk.jfr.jcmd;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import jdk.test.lib.dcmd.JcmdExecutor;
import jdk.test.lib.dcmd.PidJcmdExecutor;

/**
 * @test TestJcmdLogLevelChange
 * @key jfr
 * @summary Test changing log level
 * @requires vm.hasJFR
 *
 * @library /test/lib /test/jdk
 *
 * @run main/othervm -Xlog:jfr=info jdk.jfr.jcmd.TestJcmdChangeLogLevel
 */
public class TestJcmdChangeLogLevel {
    public static void main(String[] args) throws Exception {
        final String fileName = "jfr_trace.txt";
        final String findWhat = "[info][jfr] Flight Recorder initialized";
        boolean passed = false;

        JcmdExecutor je = new PidJcmdExecutor();
        je.execute("VM.log output='file=" + fileName + "' what='jfr=info'");
        je.execute("JFR.start duration=1s");
        List<String> lines;

        do {
            try {
                lines = Files.readAllLines(Paths.get(fileName));
            } catch (IOException e) {
                throw new Error(e);
            }
            for (String l : lines) {
                if (l.toString().contains(findWhat)) {
                    passed = true;
                    break;
                }
            }
            if (lines.size() > 100) {
                break; /* did not find it */
            }
        } while(!passed);

        if (!passed) {
            throw new Error("Not found " + findWhat  + " in stream" + lines);
        }

        System.out.println("PASSED");
    }
}

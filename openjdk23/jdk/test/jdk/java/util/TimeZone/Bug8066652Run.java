/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8066652
 * @requires os.family == "mac"
 * @summary tests thread safe native function localtime_r is accessed by multiple
 *          threads at same time and zone id should not be  "GMT+00:00"
 *          if default timezone is "GMT" and user specifies a fake timezone.
 * @library /test/lib
 * @build Bug8066652
 * @run main Bug8066652Run
 */

import java.util.Map;

import jdk.test.lib.JDKToolLauncher;
import jdk.test.lib.Utils;
import jdk.test.lib.process.ProcessTools;

public class Bug8066652Run {
    private static String cp = Utils.TEST_CLASSES;
    private static ProcessBuilder pb = new ProcessBuilder();

    public static void main(String[] args) throws Throwable {
        //set system TimeZone to GMT using environment variable TZ
        Map<String, String> env = pb.environment();
        env.put("TZ", "GMT");
        System.out.println("Current TimeZone:" + pb.environment().get("TZ"));
        JDKToolLauncher launcher = JDKToolLauncher.createUsingTestJDK("java");
        //Setting invalid TimeZone using VM option
        launcher.addToolArg("-Duser.timezone=Foo/Bar")
                .addToolArg("-ea")
                .addToolArg("-esa")
                .addToolArg("-cp")
                .addToolArg(cp)
                .addToolArg("Bug8066652");

        pb.command(launcher.getCommand());
        int exitCode = ProcessTools.executeCommand(pb)
                .getExitValue();
        if (exitCode != 0) {
            throw new RuntimeException("Unexpected exit code: " + exitCode);
        }
    }
}


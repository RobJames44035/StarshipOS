/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import jdk.jpackage.test.JPackageCommand;
import jdk.jpackage.test.Annotations.Test;
import jdk.jpackage.test.HelloApp;
import static jdk.jpackage.test.WindowsHelper.killAppLauncherProcess;

/**
 * Test that terminating of the parent app launcher process automatically
 * terminates child app launcher process.
 */

/*
 * @test
 * @summary Test case for JDK-8301247
 * @library /test/jdk/tools/jpackage/helpers
 * @build jdk.jpackage.test.*
 * @build Win8301247Test
 * @requires (os.family == "windows")
 * @run main/othervm/timeout=360 -Xmx512m  jdk.jpackage.test.Main
 *  --jpt-run=Win8301247Test
 */
public class Win8301247Test {

    @Test
    public void test() throws IOException, InterruptedException {
        var cmd = JPackageCommand.helloAppImage().ignoreFakeRuntime();

        // Launch the app in a way it doesn't exit to let us trap app laucnher
        // processes in the process list
        cmd.addArguments("--java-options", "-Djpackage.test.noexit=true");
        cmd.executeAndAssertImageCreated();

        try ( // Launch the app in a separate thread
                ExecutorService exec = Executors.newSingleThreadExecutor()) {
            exec.execute(() -> {
                HelloApp.executeLauncher(cmd);
            });

            // Wait a bit to let the app start
            Thread.sleep(Duration.ofSeconds(10));

            // Find the main app launcher process and kill it
            killAppLauncherProcess(cmd, null, 2);

            // Wait a bit and check if child app launcher process is still running (it must NOT)
            Thread.sleep(Duration.ofSeconds(5));

            killAppLauncherProcess(cmd, null, 0);
        }
    }
}

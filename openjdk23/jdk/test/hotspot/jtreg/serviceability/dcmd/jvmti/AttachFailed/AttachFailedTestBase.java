/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

import jdk.test.lib.Platform;
import jdk.test.lib.Utils;
import jdk.test.lib.dcmd.CommandExecutor;
import jdk.test.lib.dcmd.JMXExecutor;
import jdk.test.lib.dcmd.PidJcmdExecutor;

import java.nio.file.Paths;

import org.testng.annotations.Test;

public abstract class AttachFailedTestBase {

    public abstract void run(CommandExecutor executor);

    /**
     * Build path to shared object according to platform rules
     */
    public static String getSharedObjectPath(String name) {
        String libname = Platform.buildSharedLibraryName(name);

        return Paths.get(Utils.TEST_NATIVE_PATH, libname)
                    .toAbsolutePath()
                    .toString();
    }

    @Test
    public void jmx() {
        run(new JMXExecutor());
    }

    @Test
    public void cli() {
        run(new PidJcmdExecutor());
    }
}

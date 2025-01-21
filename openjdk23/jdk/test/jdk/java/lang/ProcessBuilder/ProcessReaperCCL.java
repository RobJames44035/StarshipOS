/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8269488
 * @summary verify that Process Reaper threads have a null CCL
 * @run testng ProcessReaperCCL
 */

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.testng.Assert;
import org.testng.annotations.Test;


public class ProcessReaperCCL {

    @Test
    static void test() throws Exception {
        // create a class loader
        File dir = new File(".");
        URL[] urls = new URL[] {dir.toURI().toURL()};
        ClassLoader cl = new URLClassLoader(urls);
        Thread.currentThread().setContextClassLoader(cl);

        // Invoke a subprocess with processBuilder
        ProcessBuilder pb = new ProcessBuilder(List.of("echo", "abc", "xyz"));
        Process p = pb.start();
        CompletableFuture<Process> cf = p.onExit();
        int exitValue = cf.get().exitValue();
        Assert.assertEquals(exitValue, 0, "error exit value");

        // Verify all "Process Reaper" threads have a null CCL
        for (Thread th : Thread.getAllStackTraces().keySet()) {
            if (th.getName().startsWith("process reaper")) {
                Assert.assertEquals(th.getContextClassLoader(), null, "CCL not null");
            }
        }
    }
}

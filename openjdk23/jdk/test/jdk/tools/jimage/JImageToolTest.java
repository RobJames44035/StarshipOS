/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
/*
 * @test
 * @library /test/lib
 * @requires vm.flagless
 * @build jdk.test.lib.process.ProcessTools
 * @summary Test to check if jimage tool exists and is working
 * @run main/timeout=360 JImageToolTest
 */

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import jdk.test.lib.process.ProcessTools;

/**
 * Basic test for jimage tool.
 */
public class JImageToolTest {
    private static void jimage(String... jimageArgs) throws Exception {
        ArrayList<String> args = new ArrayList<>();
        args.add("-Xms64m");
        args.add("jdk.tools.jimage.Main");
        args.addAll(Arrays.asList(jimageArgs));

        ProcessBuilder builder = ProcessTools.createLimitedTestJavaProcessBuilder(args.toArray(new String[args.size()]));
        int res = builder.inheritIO().start().waitFor();

        if (res != 0) {
            throw new RuntimeException("JImageToolTest FAILED");
        }
    }

    public static void main(String[] args) throws Exception {
        String home = System.getProperty("java.home");
        Path jimagePath = Paths.get(home, "bin", "jimage");
        Path modulesimagePath = Paths.get(home, "lib", "modules");

        if (Files.exists(jimagePath) && Files.exists(modulesimagePath)) {
            String jimage = jimagePath.toAbsolutePath().toString();
            jimage("--version");
            System.out.println("Test successful");
         } else {
            System.out.println("Test skipped, not an images build");
         }
    }
}

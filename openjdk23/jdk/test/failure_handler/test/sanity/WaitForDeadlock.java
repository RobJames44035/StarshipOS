/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.io.IOException;
import java.nio.file.Paths;

/*
 * @test
 * @build Deadlock
 * @run driver WaitForDeadlock
 */
public class WaitForDeadlock {
    public static void main(String[] args) throws Exception {
        System.out.println("START");
        ProcessBuilder pb = new ProcessBuilder(Paths.get(
                System.getProperty("test.jdk"), "bin", "java").toString(),
                "-cp", System.getProperty("java.class.path"),
                Deadlock.class.getName());
        pb.redirectError(ProcessBuilder.Redirect.to(Paths.get("out").toFile()));
        pb.redirectOutput(ProcessBuilder.Redirect.to(Paths.get("err").toFile()));
        int r = pb.start().waitFor();
        System.out.println("END. " + r);
    }
}

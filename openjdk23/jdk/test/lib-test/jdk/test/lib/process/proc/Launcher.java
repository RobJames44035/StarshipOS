/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
import jdk.test.lib.process.Proc;

/*
 * @test
 * @bug 8305846
 * @library /test/lib
 */
public class Launcher {
    public static void main(String[] args) throws Exception {
        Proc.create("A")
                .compile()
                .start()
                .output()
                .stdoutShouldContain("Hello")
                .stderrShouldContain("World");
    }
}

/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/*
 * @test
 * @bug 8261785
 * @summary Test static main methods in anonymous/local class won't cause launcher crash
 * @modules jdk.compiler jdk.zipfs
 * @compile ../TestHelper.java
 * @run testng Test8261785
 */
public class Test8261785 {
    private final Path inputDir;

    public Test8261785() {
        inputDir = Paths.get(System.getProperty("test.src", "."));
    }

    public void compile() {
        Path file = inputDir.resolve("CrashTheJVM.java");
        TestHelper.compile("-d", ".", file.toAbsolutePath().toString());
    }

    @Test
    public void run() throws IOException {
        System.out.println("Current folder: " + Paths.get(".").toAbsolutePath().toString());
        compile();
        String[] clz = Files.list(Paths.get("."))
            .peek(p -> System.out.println("Found " + p.toString()))
            .map(Path::getFileName)
            .map(Path::toString)
            .filter(f -> f.endsWith(".class"))
            .map(f -> f.substring(0, f.length() - 6))
            .toArray(String[]::new);
        assertEquals(clz.length, 8);
        for (String f: clz) {
            System.out.println("Running class " + f);
            var result = TestHelper.doExec(TestHelper.javaCmd, "-cp", ".", f);
            assertTrue(result.isOK());
        };
    }
}

/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/*
 * @test
 * @enablePreview
 * @bug 8304400
 * @summary Test basic features of javac's source-code launcher
 * @modules jdk.compiler/com.sun.tools.javac.launcher
 * @run junit BasicSourceLauncherTests
 */
class BasicSourceLauncherTests {
    @Test
    void launchHelloClassInHelloJavaUnit(@TempDir Path base) throws Exception {
        var hello = Files.writeString(base.resolve("Hello.java"),
                """
                public class Hello {
                  public static void main(String... args) {
                    System.out.println("Hi");
                  }
                }
                """);

        var run = Run.of(hello);
        var result = run.result();
        assertAll("# " + run,
                () -> assertLinesMatch(
                        """
                        Hi
                        """.lines(), run.stdOut().lines()),
                () -> assertTrue(run.stdErr().isEmpty()),
                () -> assertNull(run.exception()),
                () -> assertEquals(Set.of("Hello"), result.classNames()),
                () -> assertNotNull(result.programClass().getResource("Hello.java")),
                () -> assertNotNull(result.programClass().getResource("Hello.class")));
    }

    @Test
    void launchHelloClassInHalloJavaUnit(@TempDir Path base) throws Exception {
        var hallo = Files.writeString(base.resolve("Hallo.java"),
                """
                public class Hello {
                  public static void main(String... args) {
                    System.out.println("Hi!");
                  }
                }
                """);

        var run = Run.of(hallo);
        var result = run.result();
        assertAll("# " + run,
                () -> assertLinesMatch(
                        """
                        Hi!
                        """.lines(), run.stdOut().lines()),
                () -> assertTrue(run.stdErr().isEmpty()),
                () -> assertNull(run.exception()),
                () -> assertEquals(Set.of("Hello"), result.classNames()),
                () -> assertNotNull(result.programClass().getResource("Hallo.java")),
                () -> assertNotNull(result.programClass().getResource("Hello.class")));
    }

    @Test
    void launchMinifiedJavaProgram(@TempDir Path base) throws Exception {
        var hi = Files.writeString(base.resolve("Hi.java"),
                """
                void main() {
                  System.out.println("Hi!");
                }
                """);

        var run = Run.of(hi, List.of("--enable-preview"), List.of());
        assertAll("# " + run,
                () -> assertLinesMatch(
                        """
                        Hi!
                        """.lines(), run.stdOut().lines()),
                () -> assertTrue(run.stdErr().isEmpty()),
                () -> assertNull(run.exception()));
    }
}

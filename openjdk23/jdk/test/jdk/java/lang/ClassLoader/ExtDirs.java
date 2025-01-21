/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8060206 8067366
 * @library /test/lib
 * @summary Extension mechanism is removed
 */

import jdk.test.lib.process.ProcessTools;

import java.lang.Integer;
import java.util.stream.Stream;

public class ExtDirs {
    private static String TEST_CLASSES = System.getProperty("test.classes", ".");

    private static String[] VALUES = new String[] {
            null,
            "",
            "\"\""
    };

    public static void main(String... args) throws Exception {
        String value = System.getProperty("java.ext.dirs");
        System.out.format("java.ext.dirs = '%s'%n", value);
        if (args.length > 0) {
            int index = Integer.valueOf(args[0]);
            String expectedValue = VALUES[index];
            if (!(expectedValue == value ||
                    (value != null && value.isEmpty()) ||
                    (expectedValue != null & expectedValue.equals(value)))) {
                throw new RuntimeException("java.ext.dirs (" +
                        value + ") != " + expectedValue);
            }
            // launched by subprocess.
            return;
        }

        if (value != null) {
            throw new RuntimeException("java.ext.dirs not removed: " + value);
        }

        fatalError(0, "-Djava.ext.dirs=foo");
        start(0);
        start(1, "-Djava.ext.dirs=");
        start(2, "-Djava.ext.dirs=\"\"");
    }

    static String[] launchOptions(int testParam, String... args) {
        return Stream.concat(Stream.of(args),
                             Stream.of("-cp", TEST_CLASSES, "ExtDirs",
                                       String.valueOf(testParam)))
                     .toArray(String[]::new);
    }

    static void start(int testParam, String... args) throws Exception {
        ProcessTools.executeTestJava(launchOptions(testParam, args))
                    .shouldHaveExitValue(0);
    }

    static void fatalError(int testParam, String... args) throws Exception {
        ProcessTools.executeTestJava(launchOptions(testParam, args))
                    .stderrShouldContain("Could not create the Java Virtual Machine");
    }
}

/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8060206 8067366
 * @library /test/lib
 * @summary Endorsed standards and override mechanism is removed
 */

import jdk.test.lib.process.ProcessTools;

import java.util.stream.Stream;

public class EndorsedDirs {
    private static String TEST_CLASSES = System.getProperty("test.classes", ".");

    private static String[] VALUES = new String[] {
            null,
            "",
            "\"\""
    };

    public static void main(String... args) throws Exception {
        String value = System.getProperty("java.endorsed.dirs");
        System.out.format("java.endorsed.dirs = '%s'%n", value);
        if (args.length > 0) {
            int index = Integer.valueOf(args[0]);
            String expectedValue = VALUES[index];
            if (!(expectedValue == value ||
                    (value != null && value.isEmpty()) ||
                    (expectedValue != null & expectedValue.equals(value)))) {
                throw new RuntimeException("java.endorsed.dirs (" +
                        value + ") != " + expectedValue);
            }
            // launched by subprocess.
            return;
        }

        if (value != null) {
            throw new RuntimeException("java.endorsed.dirs not removed: " + value);
        }

        fatalError(0, "-Djava.endorsed.dirs=foo");
        start(0);
        start(1, "-Djava.endorsed.dirs=");
        start(2, "-Djava.endorsed.dirs=\"\"");
    }

    static String[] launchOptions(int testParam, String... args) {
        return Stream.concat(Stream.of(args),
                             Stream.of("-cp", TEST_CLASSES, "EndorsedDirs",
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

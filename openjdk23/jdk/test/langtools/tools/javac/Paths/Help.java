/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */


/*
 * @test
 * @bug 4940642 8293877
 * @summary Check for -help and -X flags
 */

/*
 * Converted from Help.sh, originally written by Martin Buchholz
 *
 * For the last version of the original, Help.sh, see
 * https://git.openjdk.org/jdk/blob/jdk-19%2B36/test/langtools/tools/javac/Paths/Help.sh
 *
 * This class provides rudimentary tests of the javac command-line help.
 */

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.spi.ToolProvider;

public class Help {
    public static void main(String... args) throws Exception {
        new Help().run(args);
    }

    void run(String... args) throws Exception {
        String helpText = javac("-help");
        check(helpText,
                "-X ", "-J", "-classpath ", "-cp ", "-bootclasspath ", "-sourcepath ");

        String xText = javac("-X");
        check(xText, "-Xbootclasspath/p:");
    }

    void check(String text, String... options) throws Exception {
        for (String opt : options) {
            System.err.println("Checking '" + opt + "'");
            if (!text.contains(opt)) {
                text.lines().forEach(System.err::println);
                throw new Exception("Bad help output");
            }
        }
    }

    String javac(String... args) throws Exception {
        var javac = ToolProvider.findFirst("javac")
                .orElseThrow(() -> new Exception("cannot find javac"));
        try (StringWriter sw = new StringWriter();
             PrintWriter pw = new PrintWriter(sw)) {
             int rc = javac.run(pw, pw, args);
             if (rc != 0) {
                 throw new Error("unexpected exit from javac: " + rc);
             }
             pw.flush();
             return sw.toString();
        }
    }
}


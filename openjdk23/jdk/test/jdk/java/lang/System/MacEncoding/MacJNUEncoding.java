/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8003228 8260265
 * @summary Test the value of sun.jnu.encoding on Mac
 * @requires (os.family == "mac")
 * @library /test/lib
 * @build jdk.test.lib.process.*
 *        ExpectedEncoding
 * @run main MacJNUEncoding UTF-8 UTF-8 C
 * @run main MacJNUEncoding US-ASCII UTF-8 C COMPAT
 * @run main MacJNUEncoding UTF-8    UTF-8 en_US.UTF-8
 * @run main MacJNUEncoding UTF-8    UTF-8 en_US.UTF-8 COMPAT
 */

import java.util.List;
import java.util.Map;

import jdk.test.lib.process.ProcessTools;

public class MacJNUEncoding {

    public static void main(String[] args) throws Exception {
        if (args.length != 3 && args.length != 4) {
            System.out.println("Usage:");
            System.out.println("  java MacJNUEncoding"
                    + " <expected file.encoding> <expected sun.jnu.encoding> <locale> [<user's file.encoding>]");
            throw new IllegalArgumentException("missing arguments");
        }

        final String locale = args[2];
        System.out.println("Running test for locale: " + locale);
        var cmds = (args.length == 4)
                ? List.of("-Dfile.encoding=" + args[3], ExpectedEncoding.class.getName(), args[0], args[1])
                : List.of(ExpectedEncoding.class.getName(), args[0], args[1]);
        ProcessBuilder pb = ProcessTools.createTestJavaProcessBuilder(cmds);
        Map<String, String> env = pb.environment();
        env.put("LANG", locale);
        env.put("LC_ALL", locale);
        ProcessTools.executeProcess(pb)
                    .outputTo(System.out)
                    .errorTo(System.err)
                    .shouldHaveExitValue(0);
    }
}

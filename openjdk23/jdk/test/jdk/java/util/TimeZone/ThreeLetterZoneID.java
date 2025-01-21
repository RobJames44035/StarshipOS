/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * @test
 * @bug 8342550
 * @summary Three-letter time zone IDs should output a deprecated warning
 *          message.
 * @library /test/lib
 * @build jdk.test.lib.process.ProcessTools
 * @run main ThreeLetterZoneID
 */
import java.util.TimeZone;
import jdk.test.lib.process.ProcessTools;

public class ThreeLetterZoneID {
    public static void main(String... args) throws Exception {
        if (args.length > 0) {
            TimeZone.getTimeZone("PST");
        } else {
            checkWarningMessage();
        }
    }

    public static void checkWarningMessage() throws Exception {
        ProcessTools.executeTestJava("ThreeLetterZoneID", "dummy")
            .shouldContain("Use of the three-letter time zone ID \"PST\" is deprecated and it will be removed in a future release");
    }
}

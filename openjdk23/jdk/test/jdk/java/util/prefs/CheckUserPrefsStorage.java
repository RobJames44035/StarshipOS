/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 7198073 7197662
 * @summary Tests that user preferences are stored in the permanent storage
 * @library /test/lib
 * @build jdk.test.lib.process.* CheckUserPrefFirst CheckUserPrefLater
 * @run main CheckUserPrefsStorage
 */

import jdk.test.lib.process.ProcessTools;

public class CheckUserPrefsStorage {

    public static void main(String[] args) throws Throwable {
        // First to create and store a user preference
        run("CheckUserPrefFirst");
        // Then check that preferences stored by CheckUserPrefFirst can be retrieved
        run("CheckUserPrefLater");
    }

    public static void run(String testName) throws Exception {
        ProcessTools.executeTestJava("-Djava.util.prefs.userRoot=.", testName)
                    .outputTo(System.out)
                    .errorTo(System.out)
                    .shouldHaveExitValue(0);
    }
}

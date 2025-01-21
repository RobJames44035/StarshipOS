/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/**
 * @test
 * @bug 8266851
 * @library /test/lib
 * @build IllegalAccessTest
 * @run testng IllegalAccessTest
 * @summary Make sure that --illegal-access=$VALUE is obsolete.
 */

import jdk.test.lib.process.*;
import org.testng.annotations.*;

/**
 * Make sure that --illegal-access=$VALUE is obsolete.
 */

@Test
public class IllegalAccessTest {

    void run(String text, String... vmopts)
        throws Exception
    {
        var outputAnalyzer = ProcessTools
            .executeTestJava(vmopts)
            .outputTo(System.out)
            .errorTo(System.out);
        outputAnalyzer.shouldContain(text);
    }

    public void testObsolete() throws Exception {
        run("Ignoring option --illegal-access",
            "--illegal-access=permit", "--version");
    }

}

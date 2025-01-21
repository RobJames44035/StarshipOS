/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import javax.security.auth.login.LoginContext;

/**
 * @test
 * @bug 8050461
 * @summary Test should throw Configuration error if configuration file contains
 * syntax error
 * @build SampleLoginModule JAASConfigSyntaxTest
 * @run main/othervm -Djava.security.auth.login.config=file:${test.src}/JAASSynWithOutApplication.config JAASConfigSyntaxTest
 * @run main/othervm -Djava.security.auth.login.config=file:${test.src}/JAASSynWithOutBraces.config JAASConfigSyntaxTest
 * @run main/othervm -Djava.security.auth.login.config=file:${test.src}/JAASSynWithOutFlag.config JAASConfigSyntaxTest
 * @run main/othervm -Djava.security.auth.login.config=file:${test.src}/JAASSynWithOutLoginModule.config JAASConfigSyntaxTest
 * @run main/othervm -Djava.security.auth.login.config=file:${test.src}/JAASSynWithOutSemiColen.config JAASConfigSyntaxTest
 */
public class JAASConfigSyntaxTest {

    private static final String TEST_NAME = "JAASConfigSyntaxTest";

    public static void main(String[] args) throws Exception {
        try {
            LoginContext lc = new LoginContext(TEST_NAME);
            lc.login();
            throw new RuntimeException("Test Case Failed, did not get "
                    + "expected exception");
        } catch (Exception ex) {
            if (ex.getMessage().contains("java.io.IOException: "
                    + "Configuration Error:")) {
                System.out.println("Test case passed");
            } else {
                throw new RuntimeException(ex);
            }
        }
    }
}

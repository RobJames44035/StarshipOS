/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import javax.security.auth.login.LoginContext;
import java.io.File;

/*
 * @test
 * @bug 8047789 8273026
 * @summary auth.login.LoginContext needs to be updated to work with modules
 * @comment shows that the SecondLoginModule is still needed even if it's not in the JAAS login config file
 * @build FirstLoginModule
 * @clean SecondLoginModule
 * @run main/othervm/fail Loader
 * @build SecondLoginModule
 * @run main/othervm Loader
 */
public class Loader {

    public static void main(String[] args) throws Exception {

        System.setProperty("java.security.auth.login.config",
                new File(System.getProperty("test.src"), "sl.conf").toString());
        LoginContext lc = new LoginContext("me");

        lc.login();

    }
}

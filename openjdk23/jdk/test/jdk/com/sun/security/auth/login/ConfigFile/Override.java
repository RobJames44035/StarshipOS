/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 6208996
 * @summary using -Djavax.security.auth.login.Configuration==foo doesn't ignore other configs
 *
 * @run main/othervm -Djava.security.properties=${test.src}/Override.props -Djava.security.auth.login.config==file:${test.src}/Override.good.config Override
 */

import javax.security.auth.login.*;
import com.sun.security.auth.login.*;

public class Override {
    public static void main(String[] args) throws Exception {
        ConfigFile c = new ConfigFile();
        AppConfigurationEntry[] good = c.getAppConfigurationEntry("good");
        AppConfigurationEntry[] bad = c.getAppConfigurationEntry("bad");

        if (good != null && bad == null) {
            System.out.println("test passed");
        } else {
            if (good == null) {
                throw new SecurityException("could not get good entries");
            } else {
                throw new SecurityException("incorrectly got bad entries");
            }
        }
    }
}

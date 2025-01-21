/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 4337769
 * @summary     ConfigFile should support system property expansion
 * @run main/othervm -Djava.security.auth.login.config==file:${test.src}/PropertyExpansion.config PropertyExpansion
 */

import javax.security.auth.login.*;

public class PropertyExpansion {

    public static void main(String[] args) {

        Configuration config = Configuration.getConfiguration();

        AppConfigurationEntry[] entries =
                config.getAppConfigurationEntry("PropertyExpansion");

        // there are 2 entries
        if (entries.length != 2) {
            throw new IllegalStateException("test 2 failed");
        }

        for (int i = 0; i < 2; i++) {
            System.out.println("module " + i + " = " +
                    entries[i].getLoginModuleName());
            System.out.println("control flag " + i + " = " +
                    entries[i].getControlFlag());
            java.util.Map map = entries[i].getOptions();
            System.out.println("option " + i + " = useFile, " +
                    "value = " + map.get("useFile"));
            System.out.println("option " + i + " = debug, " +
                    "value = " + map.get("debug"));

            if (i == 0 && map.get("useFile") == null ||
                    i == 0 && map.get("debug") != null) {
                throw new IllegalStateException("test 3 failed");
            }
            if (i == 1 && map.get("useFile") != null ||
                    i == 1 && map.get("debug") == null) {
                throw new IllegalStateException("test 4 failed");
            }
        }

        System.out.println("test succeeded");
    }
}

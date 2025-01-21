/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @test
 * @bug 4406033
 * @summary     ConfigFile throws an inconsistent error message
 *              when the configuration file is not found
 * @run main/othervm -Duser.language=en InconsistentError
 */

import com.sun.security.auth.login.*;
import javax.security.auth.login.*;

public class InconsistentError {

    public static void main(String[] args) {

        try {
            System.setProperty("java.security.auth.login.config",
                                "=nofile");
            ConfigFile config = new ConfigFile();
            throw new SecurityException("test 1 failed");
        } catch (SecurityException se) {
            if (se.getMessage().indexOf("No such file or directory") > 0) {
                System.out.println("test 1 succeeded");
            } else {
                System.out.println("test 1 failed");
                throw se;
            }
        }

        try {
            System.setProperty("java.security.auth.login.config",
                                "=file:/nofile");
            ConfigFile config = new ConfigFile();
            throw new SecurityException("test 2 failed");
        } catch (SecurityException se) {
            if (se.getMessage().indexOf("No such file or directory") > 0) {
                System.out.println("test 2 succeeded");
            }
        }

        System.setProperty("java.security.auth.login.config",
                                "=file:${test.src}/InconsistentError.config");
        ConfigFile config = new ConfigFile();

        System.out.println("test succeeded");

    }
}

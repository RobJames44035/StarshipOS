/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8039951
 * @summary com.sun.security.auth.module missing classes on some platforms
 * @run main/othervm AllPlatforms
 */
import javax.security.auth.login.Configuration;
import javax.security.auth.login.LoginContext;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.security.auth.login.FailedLoginException;

public class AllPlatforms {

    private static final String UNIX_MODULE = "UnixLoginModule";
    private static final String NT_MODULE = "NTLoginModule";

    public static void main(String[] args) throws Exception {
        login("cross-platform",
                UNIX_MODULE, "optional",
                NT_MODULE, "optional");
        login("windows", NT_MODULE, "required");
        login("unix", UNIX_MODULE, "required");
    }

    static void login(String test, String... conf) throws Exception {
        System.out.println("Testing " + test + "...");

        StringBuilder sb = new StringBuilder();
        sb.append("hello {\n");
        for (int i = 0; i < conf.length; i += 2) {
            sb.append("    com.sun.security.auth.module.")
                    .append(conf[i]).append(" ")
                    .append(conf[i + 1]).append(";\n");
        }
        sb.append("};\n");
        Files.write(Paths.get(test), sb.toString().getBytes());

        // Must be called. Configuration has an internal static field.
        Configuration.setConfiguration(null);
        System.setProperty("java.security.auth.login.config", test);

        try {
            LoginContext lc = new LoginContext("hello");
            lc.login();
            System.out.println(lc.getSubject());
            lc.logout();
        } catch (FailedLoginException e) {
            // This exception can occur in other platform module than the running one.
            if(e.getMessage().startsWith("Failed in attempt to import")) {
                System.out.println("Expected Exception found.");
                e.printStackTrace(System.out);
            }
        }
    }
}

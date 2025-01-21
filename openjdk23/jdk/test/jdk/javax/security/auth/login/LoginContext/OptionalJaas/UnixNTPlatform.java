/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8215916
 * @summary This test case attempts to verify whether call stack trace is
 * printed when JAAS optional login fails when debug is true.
 * @run main/othervm -Djava.security.debug=logincontext UnixNTPlatform
 */
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class UnixNTPlatform {

    public static void main(String[] args) throws Exception {
        System.out.println("Testing cross-platform");

        String config = """
                        hello {
                        com.sun.security.auth.module.UnixLoginModule optional debug=true;
                        com.sun.security.auth.module.NTLoginModule optional debug=true;
                        };
                        """;

        System.out.println("config is : \n"+config);
        Files.writeString(Path.of("cross-platform"), config.toString());

        System.setProperty("java.security.auth.login.config", "cross-platform");

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        PrintStream ps = System.err;
        System.setErr(new PrintStream(new PrintStream(stream)));

        try {
            LoginContext lc = new LoginContext("hello");
            lc.login();
            System.out.println(lc.getSubject());
            lc.logout();
        } catch (LoginException e) {
            System.out.println("Retrieving exception information");
        } finally {
            System.setErr(ps);
        }

        byte[] byes = stream.toByteArray();
        String s = new String(byes);
        System.out.printf("-- call stack is -- %n%s%n", s);
        if (!s.contains("Failed in attempt to import the underlying")) {
           throw new RuntimeException();
        }
    }
}

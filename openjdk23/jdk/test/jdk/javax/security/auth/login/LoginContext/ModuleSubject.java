/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 4378100
 * @modules jdk.security.auth
 * @summary LoginContext doesn't reinit modules with new Subject
 *      if authentication fails
 *
 * @build ModuleSubject ModuleSubjectModule
 * @run main/othervm -Djava.security.auth.login.config=file:${test.src}/ModuleSubject.config ModuleSubject
 */

import java.security.Principal;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

public class ModuleSubject {

    public static void main(String[] args) {

        LoginContext lc = null;
        try {
            lc = new LoginContext("SampleLogin");
        } catch (LoginException le) {
            System.out.println
                ("ModuleSubject test failed - login construction failed");
            throw new SecurityException(le.getMessage());
        }

        // first attempt must fail
        try {
            lc.login();
            throw new SecurityException
                ("ModuleSubject test failed: 1st login attempt did not fail!");
        } catch (LoginException le) {
            // good!
            System.out.println
                ("Good: first attempt failed");
            le.printStackTrace();
        }

        if (lc.getSubject() != null) {
            throw new SecurityException
                ("ModuleSubject test failed - " +
                "Subject after failed attempt not null: " +
                lc.getSubject().toString());
        }

        // second attempt succeeds, and the correct subject comes back
        try {
            lc.login();
            java.util.Set principals = lc.getSubject().getPrincipals();

            if (principals.size() != 1) {
                throw new SecurityException("ModuleSubject test failed: " +
                                        "corrupted subject");
            }
            java.util.Iterator i = principals.iterator();
            while (i.hasNext()) {
                Principal p = (Principal)i.next();
                System.out.println("principal after authentication = " +
                                p.toString());
            }
        } catch (LoginException le) {
            System.out.println
                ("ModuleSubject test failed - 2nd login attempt failed");
            throw new SecurityException(le.getMessage());
        }

        System.out.println("ModuleSubject test succeeded");
    }
}

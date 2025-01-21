/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 4377181
 * @summary Provide default configurable CallbackHandlers
 *
 * @build DefaultHandler DefaultHandlerImpl DefaultHandlerModule
 * @run main/othervm -Djava.security.auth.login.config=file:${test.src}/DefaultHandler.config DefaultHandler
 */

import javax.security.auth.*;
import javax.security.auth.login.*;

public class DefaultHandler {

    public static void main(String[] args) {

        // first test if a default is not provided.
        // we should get an exception

        LoginContext lc = null;
        try {
            lc = new LoginContext("SampleLogin");
        } catch (LoginException le) {
            System.out.println
                ("DefaultHandler test failed - login construction failed");
            throw new SecurityException(le.getMessage());
        }

        try {
            lc.login();
            throw new SecurityException
                ("DefaultHandler test failed: got a handler!");
        } catch (LoginException le) {
            // good!
            System.out.println
                ("Good: CallbackHandler implementation not found");
            le.printStackTrace();
        }

        // set the security property for the default handler
        java.security.Security.setProperty("auth.login.defaultCallbackHandler",
                "DefaultHandlerImpl");

        // now test to see if the default handler is picked up.
        // this should succeed.

        LoginContext lc2 = null;
        try {
            lc2 = new LoginContext("SampleLogin");
        } catch (LoginException le) {
            System.out.println
                ("DefaultHandler test failed - constructing LoginContext");
            throw new SecurityException(le.getMessage());
        }

        try {
            lc2.login();
        } catch (LoginException le) {
            System.out.println
                ("DefaultHandler test failed - login method");
            throw new SecurityException(le.getMessage());
        }

        System.out.println("DefaultHandler test succeeded");
    }
}

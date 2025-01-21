/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;


public final class TestSampleLoginModule implements LoginModule {

    private Subject subject;
    private CallbackHandler callbackHandler;
    private Map<String, ?> sharedState;
    private Map<String, ?> options;

    public TestSampleLoginModule() {
    }

    public void initialize(Subject subject,
            CallbackHandler callbackHandler,
            Map<String,?> sharedState,
            Map<String,?> options) {

        this.subject = subject;
        this.callbackHandler = callbackHandler;
        this.sharedState = sharedState;
        this.options = options;
    }

  /*
   * Authenticate the user by comparing the values of the java properties
   * (username and password) against the values of the credentials.
   * */
    public boolean login() throws LoginException {

        String credentials_username = null;
        String credentials_password = null;
        String authenticated_username = System.getProperty("susername");
        String authenticated_password = System.getProperty("spassword");

        System.out.println("TestSampleLoginModule::login: Start");

        // First retreive the credentials {username, password} from
        // the callback handler
        Callback[] callbacks = new Callback[2];
        callbacks[0] = new NameCallback("username");
        callbacks[1] = new PasswordCallback("password", false);
        try {
            callbackHandler.handle(callbacks);
            credentials_username = ((NameCallback)callbacks[0]).getName();
            credentials_password = new String(((PasswordCallback)callbacks[1]).
                    getPassword());
        } catch (Exception e) {
            throw new LoginException(e.toString());
        }

        System.out.println("TestSampleLoginModule::login: credentials username = " +
                credentials_username);
        System.out.println("TestSampleLoginModule::login: credentials password = " +
                credentials_password);
        System.out.println("TestSampleLoginModule::login: authenticated username = " +
                authenticated_username);
        System.out.println("TestSampleLoginModule::login: authenticated password = " +
                authenticated_password);

        if (credentials_username.equals(authenticated_username) &&
                credentials_password.equals(authenticated_password)) {
            System.out.println("TestSampleLoginModule::login: " +
                    "Authentication should succeed");
            return true;
        } else {
            System.out.println("TestSampleLoginModule::login: " +
                    "Authentication should reject");
            throw new LoginException("TestSampleLoginModule throws EXCEPTION");
        }
    }

    public boolean commit() throws LoginException {
        return true;
    }

    public boolean abort() throws LoginException {
        return true;
    }

    public boolean logout() throws LoginException {
        return true;
    }
}

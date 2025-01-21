/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

import javax.security.auth.login.LoginException;

/**
 * Login module which passes all the time
 */

public class DummyLoginModule extends SmartLoginModule {
    private final String header;

    public DummyLoginModule() {
        header = "DummyLoginModule: ";
    }

    @Override
    public boolean login() throws LoginException {
        System.out.println("\t\t" + header + " login method is called ");
        System.out.println("\t\t" + header + " login:PASS");
        return true;
    }

    @Override
    public boolean commit() throws LoginException {
        System.out.println("\t\t" + header + " commit method is called");
        System.out.println("\t\t" + header + " commit:PASS");
        return true;
    }

    @Override
    public boolean abort() throws LoginException {
        System.out.println("\t\t" + header + " abort method is called ");
        System.out.println("\t\t" + header + " abort:PASS");

        return true;
    }

    @Override
    public boolean logout() throws LoginException {
        System.out.println("\t\t" + header + " logout method is called");
        System.out.println("\t\t" + header + " logout:PASS");
        return true;
    }

}

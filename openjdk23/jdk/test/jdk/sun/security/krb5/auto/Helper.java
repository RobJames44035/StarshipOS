/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

public class Helper {

    static class UserPasswordHandler implements CallbackHandler {

        private final String name;
        private final String password;

        UserPasswordHandler(String name, String password) {
            this.name = name;
            this.password = password;
        }

        @Override
        public void handle(Callback[] callbacks)
                throws UnsupportedCallbackException {
            for (Callback callback : callbacks) {
                if (callback instanceof PasswordCallback) {
                    ((PasswordCallback) callback).setPassword(
                            password.toCharArray());
                } else if (callback instanceof NameCallback) {
                    ((NameCallback)callback).setName(name);
                } else {
                    throw new UnsupportedCallbackException(callback);
                }
            }
        }
    }
}

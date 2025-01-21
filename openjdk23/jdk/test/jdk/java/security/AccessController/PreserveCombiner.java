/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug 4819194
 * @summary doPrivileged should preserve DomainCombiner
 */

import java.security.*;
import java.util.concurrent.Callable;
import javax.security.auth.Subject;
import javax.security.auth.x500.X500Principal;

public class PreserveCombiner {

    public static void main(String[] args) throws Exception {

        Subject s = new Subject();
        s.getPrincipals().add(new X500Principal("cn=duke"));

        String result = Subject.callAs(s, new Callable<String>() {
            public String call() {

                // get subject from current ACC - this always worked
                Subject callAsSubject = Subject.current();
                if (callAsSubject == null) {
                    return "test 1 failed";
                } else {
                    System.out.println(callAsSubject);
                    System.out.println("test 1 passed");
                }

                // try doPriv (PrivilegedAction) test
                String result = AccessController.doPrivilegedWithCombiner
                    (new PrivilegedAction<String>() {
                    public String run() {
                        // get subject after doPriv
                        Subject doPrivSubject = Subject.current();
                        if (doPrivSubject == null) {
                            return "test 2 failed";
                        } else {
                            System.out.println(doPrivSubject);
                            return "test 2 passed";
                        }
                    }
                });

                if ("test 2 failed".equals(result)) {
                    return result;
                } else {
                    System.out.println(result);
                }

                // try doPriv (PrivilegedExceptionAction) test
                try {
                    result = AccessController.doPrivilegedWithCombiner
                        (new PrivilegedExceptionAction<String>() {
                        public String run() throws PrivilegedActionException {
                            // get subject after doPriv
                            Subject doPrivSubject = Subject.current();
                            if (doPrivSubject == null) {
                                return "test 3 failed";
                            } else {
                                System.out.println(doPrivSubject);
                                return "test 3 passed";
                            }
                        }
                    });
                } catch (PrivilegedActionException pae) {
                    result = "test 3 failed";
                }

                if ("test 3 failed".equals(result)) {
                    return result;
                } else {
                    System.out.println(result);
                }

                // tests passed
                return result;
            }
        });

        if (result.indexOf("passed") <= 0) {
            throw new SecurityException("overall test failed");
        }
    }
}

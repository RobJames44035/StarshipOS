/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8034820
 * @summary Check that adding Principal subclasses to Subject works
 */

import java.security.Principal;
import java.util.Collections;
import java.util.Set;
import javax.security.auth.Subject;

public class AddPrincipalSubclass {

    public static void main(String[] args) throws Exception {

        // create Subject with no principals and then add TestPrincipal
        Subject subject = new Subject();
        Set<Principal> principals = subject.getPrincipals(Principal.class);
        Principal principal = new TestPrincipal();
        if (!principals.add(principal)) {
            throw new Exception("add returned false instead of true");
        }
        if (!principals.contains(principal)) {
            throw new Exception("set does not contain principal");
        }

        // pre-populate Subject with TestPrincipal
        subject = new Subject(false,
                              Collections.singleton(principal),
                              Collections.emptySet(), Collections.emptySet());
        principals = subject.getPrincipals(Principal.class);
        if (!principals.contains(principal)) {
            throw new Exception("set does not contain principal");
        }
    }

    private static class TestPrincipal implements Principal {
        @Override
        public String getName() {
            return "TestPrincipal";
        }
    }
}

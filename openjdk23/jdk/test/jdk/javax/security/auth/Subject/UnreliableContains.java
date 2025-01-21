/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8243592
 * @summary Subject$SecureSet::addAll should not call contains(null)
 */

import javax.security.auth.Subject;
import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;

public class UnreliableContains {

    public static void main(String[] args) {
        MySet<Principal> set = new MySet<>();
        set.add(null);
        Subject s = null;
        try {
            s = new Subject(false, set, Collections.emptySet(),
                    Collections.emptySet());
        } catch (NullPointerException e) {
            // The correct exit
            return;
        }
        // Suppose NPE was not caught. At least null was not added?
        for (Principal p : s.getPrincipals()) {
            Objects.requireNonNull(p);
        }
        // Still must fail. We don't want this Subject created
        throw new RuntimeException("Fail");
    }

    // This is a Map that implements contains(null) differently
    static class MySet<E> extends HashSet<E> {
        @Override
        public boolean contains(Object o) {
            if (o == null) {
                return false;
            } else {
                return super.contains(o);
            }
        }
    }
}

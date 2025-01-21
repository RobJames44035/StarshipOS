/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8214583
 * @summary Check that getSubject works after JIT compiler escape analysis.
 */
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.security.auth.Subject;

public class DoAsTest {

    public static void main(String[] args) throws Exception {
        final Set<String> outer = new HashSet<>(Arrays.asList("Outer"));
        final Subject subject = new Subject(true, Collections.EMPTY_SET, outer, Collections.EMPTY_SET);

        for (int i = 0; i < 100_000; ++i) {
            final int index = i;
            Subject.callAs(subject, () -> {
                Subject s = Subject.current();
                if (s != subject) {
                    throw new AssertionError("outer Oops! " + "iteration " + index + " " + s + " != " + subject);
                }
                return 0;
            });
        }
    }
}

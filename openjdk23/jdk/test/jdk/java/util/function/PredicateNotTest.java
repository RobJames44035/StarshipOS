/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8050818
 * @run testng PredicateNotTest
 */

import java.util.List;
import java.util.function.Predicate;
import org.testng.annotations.Test;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.joining;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

@Test(groups = "unit")
public class PredicateNotTest {
    static class IsEmptyPredicate implements Predicate<String> {
        @Override
        public boolean test(String s) {
            return s.isEmpty();
        }
    }

    public void test() {
        List<String> test = List.of(
           "A non-empty line",
           "",
           "A non-empty line",
           "",
           "A non-empty line",
           "",
           "A non-empty line",
           ""
        );
        String expected = "A non-empty line\nA non-empty line\nA non-empty line\nA non-empty line";

        assertEquals(test.stream().filter(not(String::isEmpty)).collect(joining("\n")), expected);
        assertEquals(test.stream().filter(not(s -> s.isEmpty())).collect(joining("\n")), expected);
        assertEquals(test.stream().filter(not(new IsEmptyPredicate())).collect(joining("\n")), expected);
        assertEquals(test.stream().filter(not(not(not(String::isEmpty)))).collect(joining("\n")), expected);
        assertEquals(test.stream().filter(not(not(not(s -> s.isEmpty())))).collect(joining("\n")), expected);
        assertEquals(test.stream().filter(not(not(not(new IsEmptyPredicate())))).collect(joining("\n")), expected);
    }
}


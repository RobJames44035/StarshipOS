/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */


import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@Test(groups = "lib")
public class MethodReferenceTestInstanceMethod {
    public Stream<String> generate() {
        return Arrays.asList("one", "two", "three", "four", "five", "six")
            .stream()
            .filter(s->s.length() > 3)
            .map(s -> s.toUpperCase());
    }

    class Thingy<T,U> {
        U blah(Function<T, U> m, T val) {
            return m.apply(val);
        }
    }

    public void testStringBuffer() {
        String s = generate().collect(Collectors.joining());
        assertEquals(s, "THREEFOURFIVE");
    }

    public void testMRInstance() {
        Thingy<String,String> t = new Thingy<>();
        assertEquals(t.blah(String::toUpperCase, "frogs"), "FROGS");
    }

}

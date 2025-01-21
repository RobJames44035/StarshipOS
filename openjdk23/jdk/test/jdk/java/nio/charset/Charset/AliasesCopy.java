/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/* @test
 * @bug 8319817
 * @summary Check that aliases cannot be mutated
 * @run junit AliasesCopy
 */

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Set;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class AliasesCopy {
    private static final Set<String> ALIASES_SET = Set.of("foo-alias");
    private static final String[] ALIASES_ARRAY = ALIASES_SET.toArray(String[]::new);

    @Test
    public void aliasesCopy() {
        final FooCharset cs = new FooCharset(ALIASES_ARRAY);
        ALIASES_ARRAY[0] = "bar-alias";
        assertIterableEquals(ALIASES_SET, cs.aliases());
    }

    private static final class FooCharset extends Charset {
        private FooCharset(String[] aliases) {
            super("foo", aliases);
        }

        @Override
        public CharsetEncoder newEncoder() {
            throw new RuntimeException("not implemented");
        }

        @Override
        public CharsetDecoder newDecoder() {
            throw new RuntimeException("not implemented");
        }

        @Override
        public boolean contains(Charset cs) {
            throw new RuntimeException("not implemented");
        }
    }
}

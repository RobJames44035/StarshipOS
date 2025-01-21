/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */
package jdk.internal.org.jline.reader.impl.completer;

import java.util.Objects;

import jdk.internal.org.jline.reader.Candidate;
import jdk.internal.org.jline.reader.Completer;

/**
 * {@link Completer} for {@link Enum} names.
 *
 * @author <a href="mailto:jason@planet57.com">Jason Dillon</a>
 * @since 2.3
 */
public class EnumCompleter extends StringsCompleter {
    public EnumCompleter(Class<? extends Enum<?>> source) {
        Objects.requireNonNull(source);
        for (Enum<?> n : source.getEnumConstants()) {
            candidates.add(new Candidate(n.name().toLowerCase()));
        }
    }
}

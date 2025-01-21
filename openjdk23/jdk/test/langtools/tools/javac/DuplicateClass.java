/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/**
 * Compiling this translation unit should fail; there is, after all, a
 * duplicate class.  Nonetheless, the compiler should not crash while
 * processing it.
 */
public class DuplicateClass {
    protected Object clone() {
        super.clone();
    }
}

public class DuplicateClass {}

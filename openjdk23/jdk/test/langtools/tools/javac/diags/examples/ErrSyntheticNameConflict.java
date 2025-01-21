/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.err.cannot.generate.class
// key: compiler.misc.synthetic.name.conflict

import java.util.Objects;

class ErrSyntheticNameConflict {

    static class Outer {
        ErrSyntheticNameConflict this$0 = null;
    }

    public class Inner extends Outer {
        {
            // access enclosing instance so this$0 field is generated
            Objects.requireNonNull(ErrSyntheticNameConflict.this);
        }
    }
}

/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.misc.type.req.class.array
// key: compiler.err.type.found.req

import java.util.*;

class TypeReqClassArray {
    interface Sig {
        void m(int s);
    }

    Sig consume(Sig s) { return s; }

    public void meth() {
        Sig s = consume(int::new);
    }
}

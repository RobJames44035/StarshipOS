/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

// key: compiler.note.method.ref.search.results.multi
// key: compiler.misc.bound
// key: compiler.misc.applicable.method.found.3
// key: compiler.misc.static
// key: compiler.misc.partial.inst.sig
// key: compiler.misc.unbound
// options: --debug=dumpMethodReferenceSearchResults

import java.util.function.*;

class BoundUnboundMethRefSearch2 {
    interface SAM <T> {
        boolean test(T n, T m);
    }

    static <T> boolean foo(T x, T y) {
        return false;
    }

    void bar() {
        SAM <Integer> mRef = BoundUnboundMethRefSearch2::<Integer>foo;
    }

}
